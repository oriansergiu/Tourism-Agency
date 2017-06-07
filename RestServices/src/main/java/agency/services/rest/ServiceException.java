package agency.services.rest;

/**
 * Created by Sergiu on 5/18/2017.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }
}
