package agency.network.utils;

import java.net.Socket;

/**
 * Created by Sergiu on 4/2/2017.
 */
public abstract class AbsConcurrentServer extends AbstractServer {


    public AbsConcurrentServer(int port) {
        super(port);
        System.out.println("Concurent Abstractserver");
    }

    protected void processRequest(Socket client)
    {
        Thread tw = createWorker(client);
        tw.start();
    }

    protected abstract Thread createWorker(Socket client);

}
