package agency.services.rest;

import agency.model.entities.Trip;
import agency.persistence.repository.RepositoryException;
import agency.persistence.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Sergiu on 5/18/2017.
 */

@RestController
@RequestMapping("agency/trips")
public class AgencyTripController
{
    //    private static final String template

    @Autowired
    private TripRepository tripRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Trip[] getAll() { return tripRepository.getAll();}

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@RequestBody Trip trip){
        tripRepository.update(trip);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id)
    {
        Trip trip = tripRepository.findOne(id);
        if (trip==null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Trip>(trip, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody Trip trip)
    {
        tripRepository.save(trip);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            tripRepository.delete(id);
            return new ResponseEntity<Trip>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete trip exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
