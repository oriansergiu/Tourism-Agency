package agency.network.utils;

import agency.network.rpcprotocol.ams.AgencyClientAMSRpcReflectionWorker;
import agency.services.IAgencyServicesAMS;

import java.net.Socket;

/**
 * Created by Sergiu on 5/10/2017.
 */
public class AgencyRpcAMSConcurrentServer extends AbsConcurrentServer {

    private IAgencyServicesAMS agencyServer;

    public AgencyRpcAMSConcurrentServer(int port, IAgencyServicesAMS server) {

        super(port);
        this.agencyServer = server;
        System.out.println("Agency- AgencyRpcAMSConcurrentServer port "+port);
    }

    @Override
    protected Thread createWorker(Socket client) {
        AgencyClientAMSRpcReflectionWorker worker = new AgencyClientAMSRpcReflectionWorker(agencyServer, client);

        Thread tw = new Thread(worker);
        return tw;
    }
}
