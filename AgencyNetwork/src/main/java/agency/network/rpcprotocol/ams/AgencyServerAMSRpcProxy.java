package agency.network.rpcprotocol.ams;

import agency.model.entities.Agent;
import agency.model.entities.Trip;
import agency.network.dto.AgentDTO;
import agency.network.dto.DTOUtils;
import agency.network.dto.MakeABookingDTO;
import agency.network.dto.TripDTO;
import agency.network.rpcprotocol.Request;
import agency.network.rpcprotocol.RequestType;
import agency.network.rpcprotocol.Response;
import agency.network.rpcprotocol.ResponseType;
import agency.services.AgencyExceptions;
import agency.services.IAgencyClient;
import agency.services.IAgencyServicesAMS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Sergiu on 5/11/2017.
 */
public class AgencyServerAMSRpcProxy implements IAgencyServicesAMS {

    private String host;
    private Integer port;

    private IAgencyClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public AgencyServerAMSRpcProxy(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingDeque<>();
    }

    @Override
    public void login(Agent agent) throws AgencyExceptions {
        initializeConnection();
        AgentDTO agentDTO = DTOUtils.getDTO(agent);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(agentDTO).build();
        sendRequest(req);
        Response response = readResponse();
        if(response.type() == ResponseType.OK){
            this.client = client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new AgencyExceptions(err);
        }
    }

    @Override
    public Trip[] getAllTrips() throws AgencyExceptions {
        Request req = new Request.Builder().type(RequestType.GETALLTRIPS).data(null).build();
        sendRequest(req);
        Response response = readResponse();

        if (response.type() == ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new AgencyExceptions(err);
        }

        TripDTO[] trDTO = (TripDTO[])response.data();
        Trip[] trips = DTOUtils.getFromDTO(trDTO);
        return trips;
    }

    @Override
    public void sendTripToAll(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Integer id, String landmark, String transportCompanyName, String time, String price, String numberOfSeats) throws AgencyExceptions {
        MakeABookingDTO maBDTO = new MakeABookingDTO(ok, name, phoneNumber, tripTickets, ticketsPurchased, id, landmark, transportCompanyName, time, price, numberOfSeats);
        Request req = new Request.Builder().type(RequestType.BOOKING).data(maBDTO).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new AgencyExceptions(err);
        }

    }

    @Override
    public void logout(Agent agent) throws AgencyExceptions {

    }



    private void sendRequest(Request request)throws AgencyExceptions {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new AgencyExceptions("Error sending object "+e);
        }

    }
    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Response readResponse() throws AgencyExceptions {
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection()
    {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(Response response)
    {
        return response.type() == ResponseType.OK || response.type() == ResponseType.BOOKING || response.type() == ResponseType.ERROR || response.type() == ResponseType.GETALLTRIPS;
    }

    private class ReaderThread implements Runnable {

        @Override
        public void run() {
            while (!finished)
            {
                try{
                    Object response = input.readObject();
                    System.out.println("Response received " + response);
                    if(isUpdate((Response) response))
                    {
                        try {
                            qresponses.put((Response)response);

                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
