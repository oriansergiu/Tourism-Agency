package agency.server;

import agency.model.entities.Agent;
import agency.model.entities.Client;
import agency.model.entities.Client_trip;
import agency.model.entities.Trip;
import agency.persistence.repository.AgentRepository;
import agency.persistence.repository.ClientRepository;
import agency.persistence.repository.Client_TripRepository;
import agency.persistence.repository.TripRepository;
import agency.services.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class AgencyServerImpl implements IAgencyServer {

    private AgentRepository agentRepository;
    private TripRepository tripRepository;
    private Client_TripRepository client_tripRepository;
    private ClientRepository clientRepository;
    private Map<String, IAgencyClient> loggedClients;

    public AgencyServerImpl(AgentRepository agentService, TripRepository tripService, Client_TripRepository client_tripService, ClientRepository clientService) {
        this.agentRepository = agentService;
        this.tripRepository = tripService;
        this.client_tripRepository = client_tripService;
        this.clientRepository = clientService;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Agent agent, IAgencyClient client) throws AgencyExceptions {

        System.out.println("ServerImpl");

        Agent theAgent = agentRepository.findOne(agent.getUser());
        System.out.println("AGENCYserver: " + theAgent.getName());

        if(theAgent != null )
        {
            if(loggedClients.get(theAgent.getId())!=null)
                throw new AgencyExceptions("This agent is already logged in.");
            if(theAgent.getPassword().compareTo(agent.getPassword()) == 0)
                loggedClients.put(agent.getUser(), client);
        }
        else
        {
            throw new AgencyExceptions("User / Password is incorrect.");
        }
    }

    @Override
    public synchronized void logout(Agent agent, IAgencyClient client) throws AgencyExceptions {
        IAgencyClient localClient=loggedClients.remove(agent.getUser());
        if (localClient==null)
            throw new AgencyExceptions("User "+agent.getId()+" is not logged in.");

    }

    private final int defaultThreadsNo = 5;

    private void notifyAgents()
    {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for(String user : loggedClients.keySet())
        {
            IAgencyClient agencyClient = loggedClients.get(user);
            executor.execute(() -> {
                        agencyClient.makeABookReceived();
                    });
        }
        executor.shutdown();
    }

    @Override
    public synchronized Trip[] makeABooking(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Trip newTrip) throws AgencyExceptions {

        //IAgencyClient receiverClient=loggedClients.get(agent);

        Client client = clientRepository.findByPhoneNumber("4" + phoneNumber);
        //Agent ag = agentRepository.findOne(agent);

        if(ok && ticketsPurchased <= Integer.parseInt(tripTickets) && ticketsPurchased > 0 && client == null)
        {
            Client addClient = new Client(1, name,phoneNumber);
            clientRepository.save(addClient);
        }
        Client actualClient = clientRepository.findByPhoneNumber("4" + phoneNumber);

        if( ok && ticketsPurchased <= Integer.parseInt(tripTickets) && ticketsPurchased > 0) {


            Integer refreshTickets = Integer.parseInt(tripTickets) - ticketsPurchased;
            newTrip.setNumberOfSeats(refreshTickets.toString());
            tripRepository.update(newTrip);
            Client_trip newClient_trip = new Client_trip(1,actualClient.getId(), newTrip.getId(), ticketsPurchased);
            client_tripRepository.save(newClient_trip);

        } else if (ok) {
            throw new AgencyExceptions("Sorry! We don't have enough tickets.");
        }

        return getAllTrips();
    }

    @Override
    public synchronized Trip[] getAllTrips(IAgencyClient client) throws AgencyExceptions {
        Trip[] trips = new Trip[tripRepository.findAll().size()];
        for (int elem =0; elem < tripRepository.findAll().size(); elem++)
            trips[elem] = tripRepository.findAll().get(elem);
        return trips;
    }

    public Trip[] getAllTrips() throws AgencyExceptions {
        Trip[] trips = new Trip[tripRepository.findAll().size()];
        for (int elem =0; elem < tripRepository.findAll().size(); elem++)
            trips[elem] = tripRepository.findAll().get(elem);
        return trips;
    }

}
