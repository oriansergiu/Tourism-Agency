import agency.model.entities.Agent;
import agency.network.utils.AbstractServer;
import agency.network.utils.AgencyObjectConcurrentServer;
import agency.network.utils.ServerExceptions;
import agency.persistence.repository.ClientRepository;
import agency.persistence.repository.Client_TripRepository;
import agency.persistence.repository.TripRepository;
import agency.persistence.repository.AgentRepository;
import agency.server.AgencyServerImpl;
import agency.services.IAgencyServer;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class ServerMain {
    private static int defaultPort = 55555;



    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try
        {
            serverProps.load(ServerMain.class.getResourceAsStream("/agencyserver.properties"));
            System.out.println("Server properties set.");

            serverProps.list(System.out);
        }catch (IOException e) {
            e.printStackTrace();
        }

        AgentRepository agentRepository = new AgentRepository();

        //Agent ag = new Agent(1,"sergiu123","123444","Orian Sergiu");

        //agentRepository.save(ag);

        ClientRepository clientRepository = new ClientRepository(serverProps);

        TripRepository tripRepository = new TripRepository(serverProps);

        Client_TripRepository client_tripRepository = new Client_TripRepository(serverProps);

        IAgencyServer agencyServerImpl = new AgencyServerImpl(agentRepository,tripRepository,client_tripRepository,clientRepository);

        int agencyServerPort = defaultPort;
        try
        {
            agencyServerPort = Integer.parseInt(serverProps.getProperty("tasks.server.port"));
        }catch (NumberFormatException nef)
        {
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }

        System.out.println("Starting server on port: "+agencyServerPort);
        AbstractServer server = new AgencyObjectConcurrentServer(agencyServerPort, agencyServerImpl);
        try {
            server.start();
        } catch (ServerExceptions serverExceptions) {
            System.err.println("Error starting the server" + serverExceptions.getMessage());
        }
    }


}
