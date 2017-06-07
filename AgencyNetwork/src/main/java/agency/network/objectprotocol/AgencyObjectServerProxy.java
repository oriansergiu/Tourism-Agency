package agency.network.objectprotocol;

import agency.model.entities.Agent;
import agency.model.entities.Trip;
import agency.model.exceptions.AgentException;
import agency.network.dto.AgentDTO;
import agency.network.dto.DTOUtils;
import agency.network.dto.MakeABookingDTO;
import agency.network.dto.TripDTO;
import agency.services.AgencyExceptions;
import agency.services.IAgencyClient;
import agency.services.IAgencyServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Sergiu on 4/3/2017.
 */
public class AgencyObjectServerProxy implements IAgencyServer {

    private String host;
    private int port;

    private IAgencyClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponse;
    private volatile boolean finished;

    public AgencyObjectServerProxy(String host, int port) {
        this.host = host;
        this.port = port;

        this.qresponse = new LinkedBlockingDeque<Response>();
    }

    @Override
    public void login(Agent agent, IAgencyClient client) throws AgencyExceptions, AgentException {

        initializeConnection();
        AgentDTO agentDTO = DTOUtils.getDTO(agent);
        sendRequest(new LoginRequest(agentDTO));
        Response response=readResponse();
        if (response instanceof OkResponse){
            this.client=client;
            return;
        }
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new AgencyExceptions(err.getMessage());
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

    private Response readResponse() {
        Response response=null;
        try{
            response=qresponse.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendRequest(Request request) throws AgencyExceptions{
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new AgencyExceptions("Error sending object "+e);
        }
    }


    @Override
    public void logout(Agent agent, IAgencyClient client) throws AgencyExceptions {
        System.out.println("LOGOUT: PROXY");
        AgentDTO agdto=DTOUtils.getDTO(agent);
        sendRequest(new LogoutRequest(agdto));
        Response response=readResponse();
        closeConnection();
        System.out.println("CLOSED");
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new AgencyExceptions(err.getMessage());
        }
    }

    @Override
    public Trip[] makeABooking(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Trip newTrip) throws AgencyExceptions {

        //MakeABookingDTO maBDTO = new MakeABookingDTO(ok,name,phoneNumber,tripTickets,ticketsPurchased,newTrip);
        //sendRequest(new MakeABookingRequest(maBDTO));
        Response response = readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new AgencyExceptions(err.getMessage());
        }
        GetAllTripsResponse resp = (GetAllTripsResponse) response;
        TripDTO[] trDTO = resp.getTrips();
        Trip[] trips = DTOUtils.getFromDTO(trDTO);
        return trips;
    }

    @Override
    public Trip[] getAllTrips(IAgencyClient client) throws AgencyExceptions {

        sendRequest(new GetAllTripsRequest());
        Response response = readResponse();

        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new AgencyExceptions(err.getMessage());
        }
        GetAllTripsResponse resp = (GetAllTripsResponse) response;
        TripDTO[] trDTO = resp.getTrips();
        Trip[] trips = DTOUtils.getFromDTO(trDTO);
        return trips;
    }

    private void initializeConnection() throws AgencyExceptions {
        try {
            connection = new Socket(host,port);

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();

            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable
    {
        public void run() {
            while (!finished)
            {
                try
                {
                    Object response = input.readObject();
                    System.out.println("response received "+response);
                    if(response instanceof UpdateResponse)
                        handleUpdate((UpdateResponse) response);
                    else{
                        /*responses.add((Response)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (responses){
                            responses.notify();
                        }*/
                        try {
                            qresponse.put((Response)response);
                        } catch (InterruptedException e) {
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

    private void handleUpdate(UpdateResponse update) {
        if (update instanceof NewBookingResponse){
            NewBookingResponse bookRes=(NewBookingResponse)update;
            Trip trip = DTOUtils.getFromDTO(bookRes.getBooking());
//            try {
//                client.makeABook(trip);
//            } catch (AgencyExceptions e) {
//                e.printStackTrace();
//            }
        }
    }
}
