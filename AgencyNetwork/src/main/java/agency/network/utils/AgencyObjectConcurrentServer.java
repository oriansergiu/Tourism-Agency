package agency.network.utils;

import agency.network.objectprotocol.AgencyClientObjectWorker;
import agency.services.IAgencyServer;

import java.net.Socket;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class AgencyObjectConcurrentServer extends AbsConcurrentServer {

    IAgencyServer server;

    public AgencyObjectConcurrentServer(int port, IAgencyServer server) {
        super(port);
        this.server = server;
    }

    @Override
    protected Thread createWorker(Socket client) {
        AgencyClientObjectWorker worker = new AgencyClientObjectWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;

    }
}
