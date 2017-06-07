package agency.network.utils;

import agency.network.utils.ServerExceptions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sergiu on 4/2/2017.
 */
public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;

    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() throws ServerExceptions
    {
        try
        {
            server = new ServerSocket(port);

            while(true)
            {
                System.out.println("Waiting for clients...");
                Socket client = server.accept();
                System.out.println("Client connected...");

                processRequest(client);
            }

        } catch (IOException e)
        {
            throw new ServerExceptions("Starting server error " + e);
        }finally {
            try {
                server.close();
            } catch (IOException e)
            {
                throw new ServerExceptions("Closing server error " +e);
            }
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerExceptions
    {
        try{
            server.close();
        } catch (IOException e)
        {
            throw new ServerExceptions("Closing server error "+e);
        }
    }
}
