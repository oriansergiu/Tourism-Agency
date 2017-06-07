package agency.network.utils;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class ServerExceptions extends Exception {
    public ServerExceptions() {
    }

    public ServerExceptions(String message) {
        super(message);
    }

    public ServerExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
