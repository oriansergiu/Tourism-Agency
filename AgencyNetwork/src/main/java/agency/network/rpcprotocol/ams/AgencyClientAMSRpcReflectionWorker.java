package agency.network.rpcprotocol.ams;

import agency.model.entities.Agent;
import agency.model.entities.Trip;
import agency.network.dto.AgentDTO;
import agency.network.dto.DTOUtils;
import agency.network.dto.MakeABookingDTO;
import agency.network.dto.TripDTO;
import agency.network.rpcprotocol.Request;
import agency.network.rpcprotocol.Response;
import agency.network.rpcprotocol.ResponseType;
import agency.services.AgencyExceptions;
import agency.services.IAgencyServicesAMS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by Sergiu on 5/10/2017.
 */
public class AgencyClientAMSRpcReflectionWorker implements Runnable {
    private IAgencyServicesAMS server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public AgencyClientAMSRpcReflectionWorker(IAgencyServicesAMS server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected) {
            try {
                Object request = input.readObject();
                System.out.println("Received request");
                Response response = handleRequest((Request) request);
                if(response != null){
                    sendResponse(response);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request)
    {
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            //System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException|InvocationTargetException |IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        AgentDTO adto=(AgentDTO)request.data();
        Agent agent= DTOUtils.getFromDTO(adto);
        try {
            server.login(agent);
            return okResponse;
        } catch (AgencyExceptions e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Login request ..."+request.type());
        AgentDTO adto=(AgentDTO)request.data();
        Agent agent= DTOUtils.getFromDTO(adto);
        try {
            server.logout(agent);
            return okResponse;
        } catch (AgencyExceptions e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleBOOKING(Request request)
    {
        System.out.println("Booking request..."+ request.type());
        MakeABookingDTO mBDTO = (MakeABookingDTO)request.data();
        try{
            server.sendTripToAll(mBDTO.isOk(),mBDTO.getName(),mBDTO.getPhoneNumber(),mBDTO.getTripTickets(), mBDTO.getTicketsPurchased(),mBDTO.getId(), mBDTO.getLandmark(), mBDTO.getTransportCompany(), mBDTO.getTime(), mBDTO.getPrice(), mBDTO.getNumberOfSeats());
            return okResponse;
        } catch (AgencyExceptions agencyExceptions) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(agencyExceptions.getMessage()).build();
        }
    }

    private Response handleGETALLTRIPS(Request request)
    {
        System.out.println("Get all trips request..."+ request.type());
        Trip[] trips;
        try {
            trips = server.getAllTrips();
            TripDTO[] tripsDTO = DTOUtils.getDTO(trips);
            return new Response.Builder().type(ResponseType.GETALLTRIPS).data(tripsDTO).build();
        } catch (AgencyExceptions agencyExceptions) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(agencyExceptions.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }
}
