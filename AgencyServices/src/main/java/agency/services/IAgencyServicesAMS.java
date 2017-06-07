package agency.services;

import agency.model.entities.Agent;
import agency.model.entities.Trip;

/**
 * Created by Sergiu on 5/10/2017.
 */
public interface IAgencyServicesAMS {
    void login(Agent agent) throws AgencyExceptions;
    void sendTripToAll(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Integer id, String landmark, String transportCompanyName, String time, String price, String numberOfSeats) throws AgencyExceptions;
    void logout(Agent agent) throws AgencyExceptions;
    Trip[] getAllTrips() throws AgencyExceptions;
}
