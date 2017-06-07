package agency.services;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class AgencyExceptions extends Exception {

    public AgencyExceptions() {
    }

    public AgencyExceptions(String message) {
        super(message);
    }

    public AgencyExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
