package agency.network.objectprotocol;

import agency.network.dto.TripDTO;

import java.util.List;

/**
 * Created by Sergiu on 4/20/2017.
 */
public class GetAllTripsResponse implements Response {

    public TripDTO[] trips;

    public GetAllTripsResponse(TripDTO[] trips) {
        this.trips = trips;
    }

    public TripDTO[] getTrips() {
        return trips;
    }
}
