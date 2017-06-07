package agency.network.objectprotocol;

/**
 * Created by Sergiu on 4/2/2017.
 */
public class ErrorResponse implements Response {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
