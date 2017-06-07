package agency.services;

import agency.model.entities.Agent;
import agency.model.entities.Trip;
import agency.model.exceptions.AgentException;

/**
 * Created by Sergiu on 4/2/2017.
 */
public interface IAgencyServer {
    public void login(Agent agent, IAgencyClient client) throws AgencyExceptions, AgentException;
    public void logout(Agent agent, IAgencyClient client) throws AgencyExceptions, AgentException;
    public Trip[] makeABooking(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Trip newTrip) throws AgencyExceptions;

    public Trip[] getAllTrips(IAgencyClient client) throws AgencyExceptions;
}
