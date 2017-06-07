package agency.client.view;

import agency.model.entities.Agent;
import agency.model.entities.Trip;
import agency.model.exceptions.AgentException;
import agency.services.AgencyExceptions;
import agency.services.IAgencyClient;
import agency.services.IAgencyServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiu on 4/20/2017.
 */
public class ClientController implements IAgencyClient {

    private IAgencyServer server;
    private Agent agent;
    private Trip[] trips;

    public ClientController(IAgencyServer server) {
        this.server = server;
    }

    public void login(String user, String password) throws AgencyExceptions, AgentException
    {
        Agent agent = new Agent(user, password);
        server.login(agent, this);
        this.agent = agent;
    }

    public void logout() throws AgencyExceptions, AgentException {
        server.logout(agent, this);
    }

    public Trip[] getAll() throws AgencyExceptions {
        this.trips = server.getAllTrips(this);
        return trips;
    }

    public Trip[] makeABook(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Trip newTrip) throws AgencyExceptions {

        this.trips = server.makeABooking(ok, name, phoneNumber, tripTickets, ticketsPurchased, newTrip);

        return trips;
    }

    @Override
    public void makeABookReceived() {

    }

    public List<Trip> filterByLandmarkAndTimeSlot(String landmark, String time) {
        List<Trip> trips = new ArrayList<Trip>();
        for (int elem =0; elem < this.trips.length; elem++) {
            if(this.trips[elem].getLandmark().compareTo(landmark) == 0 || this.trips[elem].getTime().compareTo(time) == 0)
                trips.add(this.trips[elem]);

        }
        return trips;
    }
}
