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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class AgencyClientObjectWorker implements Runnable, IAgencyClient {

    private IAgencyServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public AgencyClientObjectWorker(IAgencyServer server, Socket connection) {
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

        while(connected){
            try {
                Object request=input.readObject();
                Object response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse((Response) response);
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

    private Response handleRequest(Request request)
    {
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            AgentDTO adto=logReq.getAgent();
            Agent agent= DTOUtils.getFromDTO(adto);
            try {
                server.login(agent, this);
                return new OkResponse();
            } catch (AgencyExceptions e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            } catch (AgentException e) {
                e.printStackTrace();
            }
        }
        if (request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logReq=(LogoutRequest)request;
            AgentDTO adto=logReq.getAgentDTO();
            Agent agent= DTOUtils.getFromDTO(adto);
            try {
                server.logout(agent, this);
                connected=false;
                return new OkResponse();

            } catch (AgencyExceptions e) {
                return new ErrorResponse(e.getMessage());
            } catch (AgentException e) {
                e.printStackTrace();
            }
        }

        /// All data from database.
        if (request instanceof GetAllTripsRequest) {
            System.out.println("GetAllTripsRequest request ...");
            Trip[] trips;
            try {
                trips = server.getAllTrips(this);
                TripDTO[] tripsDTO = DTOUtils.getDTO(trips);
                return new GetAllTripsResponse(tripsDTO);
            } catch (AgencyExceptions e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }

        /// Make a booking
//        if (request instanceof MakeABookingRequest) {
//            System.out.println("MakeABookingRequest request ...");
//            MakeABookingRequest mABookReq=(MakeABookingRequest)request;
//            MakeABookingDTO mAdto=mABookReq.getBook();
//            Trip[] trips;
//            try {
//                trips = server.makeABooking(mAdto.isOk(),mAdto.getName(),mAdto.getPhoneNumber(),mAdto.getTripTickets(),mAdto.getTicketsPurchased(),mAdto.getNewTrip());
//                TripDTO[] tripsDTO = DTOUtils.getDTO(trips);
//                return new GetAllTripsResponse(tripsDTO);
//            } catch (AgencyExceptions e) {
//                connected = false;
//                return new ErrorResponse(e.getMessage());
//            }
//        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void makeABookReceived() {
        try {
            sendResponse(new MakeABookReceivedResponse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
