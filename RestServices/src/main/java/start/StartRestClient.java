package start;

import agency.model.entities.Agent;
import agency.model.entities.Trip;
import agency.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import rest.client.TripsClient;

/**
 * Created by Sergiu on 5/18/2017.
 */
public class StartRestClient {

    private final static TripsClient tripssClient = new TripsClient();

    public static void main(String[] args) {
        try {
            show(() -> {
                Trip[] res = tripssClient.getAll();
                for (Trip a : res) {
                    System.out.println(a.getId() + ": " + a.getLandmark());
                }
            });
        } catch (RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }
    }

    private static void show(Runnable task) {
        try {
            task.run();
        }catch (ServiceException e){
            System.out.println("Service exception " + e);
        }
    }

}
