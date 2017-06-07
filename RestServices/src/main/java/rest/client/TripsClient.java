package rest.client;

import agency.model.entities.Trip;
import agency.services.rest.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

/**
 * Created by Sergiu on 5/18/2017.
 */
public class TripsClient {

    public static final String URL = "http://localhost:8080/agency/trips";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable)
    {
        try{
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e){
          throw new ServiceException(e);
        }catch (Exception e) {
            throw new ServiceException(e);
        }
    }



    public Trip[] getAll() { return execute(()-> restTemplate.getForObject(URL, Trip[].class));}

}
