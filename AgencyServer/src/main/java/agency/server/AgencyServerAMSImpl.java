package agency.server;

import agency.model.entities.Agent;
import agency.model.entities.Client;
import agency.model.entities.Client_trip;
import agency.model.entities.Trip;
import agency.persistence.repository.AgentRepository;
import agency.persistence.repository.ClientRepository;
import agency.persistence.repository.Client_TripRepository;
import agency.persistence.repository.TripRepository;
import agency.services.AgencyExceptions;
import agency.services.IAgencyNotificationService;
import agency.services.IAgencyServicesAMS;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Sergiu on 5/10/2017.
 */
public class AgencyServerAMSImpl implements IAgencyServicesAMS{

    private AgentRepository agentRepo;
    private TripRepository tripRepo;
    private ClientRepository clientRepo;
    private Client_TripRepository cl_trRepo;
    private Map<String, Agent> loggedClients;
    private IAgencyNotificationService notificationService;

    public AgencyServerAMSImpl(AgentRepository agentRepo, TripRepository tripRepo, ClientRepository clientRepo, Client_TripRepository cl_trRepo, IAgencyNotificationService notificationService) {
        this.agentRepo = agentRepo;
        this.tripRepo = tripRepo;
        this.clientRepo = clientRepo;
        this.cl_trRepo = cl_trRepo;
        loggedClients = new ConcurrentHashMap<>();
        this.notificationService = notificationService;
    }


    @Override
    public void login(Agent agent) throws AgencyExceptions {
        Agent theAgent = agentRepo.findOne(agent.getUser());

        if(theAgent != null )
        {
            if(loggedClients.get(theAgent.getId())!=null)
                throw new AgencyExceptions("This agent is already logged in.");
            if(theAgent.getPassword().compareTo(agent.getPassword()) == 0) {
                loggedClients.put(agent.getUser(), agent);
            }
        }
        else
        {
            throw new AgencyExceptions("User / Password is incorrect.");
        }
    }

    @Override
    public void sendTripToAll(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Integer id, String landmark, String transportCompanyName, String time, String price, String numberOfSeats) throws AgencyExceptions {


        Trip newTripp = new Trip(id, landmark, transportCompanyName, time, price, numberOfSeats);

        Client client = clientRepo.findByPhoneNumber("4" + phoneNumber);
        //Agent ag = agentRepository.findOne(agent);

        if(ok && ticketsPurchased <= Integer.parseInt(tripTickets) && ticketsPurchased > 0 && client == null)
        {
            Client addClient = new Client(1, name,phoneNumber);
            clientRepo.save(addClient);
        }
        Client actualClient = clientRepo.findByPhoneNumber("4" + phoneNumber);

        if( ok && ticketsPurchased <= Integer.parseInt(tripTickets) && ticketsPurchased > 0) {

            Integer refreshTickets = Integer.parseInt(tripTickets) - ticketsPurchased;
            newTripp.setNumberOfSeats(refreshTickets.toString());
            tripRepo.update(newTripp);
            Client_trip newClient_trip = new Client_trip(1,actualClient.getId(), newTripp.getId(), ticketsPurchased);
            cl_trRepo.save(newClient_trip);
            notificationService.newBooking(newTripp);

        } else if (ok) {
            throw new AgencyExceptions("Sorry! We don't have enough tickets.");
        }
    }

    @Override
    public void logout(Agent agent) throws AgencyExceptions {

    }

    @Override
    public Trip[] getAllTrips() throws AgencyExceptions {
        Trip[] trips = new Trip[tripRepo.findAll().size()];
        for (int elem =0; elem < tripRepo.findAll().size(); elem++)
            trips[elem] = tripRepo.findAll().get(elem);
        return trips;
    }
}
