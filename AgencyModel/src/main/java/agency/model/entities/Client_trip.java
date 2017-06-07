package agency.model.entities;

/**
 * Created by Sergiu on 3/18/2017.
 */
public class Client_trip implements HasID<Integer> {

    private Integer id;
    private Integer client_id;
    private Integer trip_id;
    private Integer numberOfSeats;

    public Client_trip(Integer id, Integer client_id, Integer trip_id, Integer numberOfSeats) {
        this.id = id;
        this.client_id = client_id;
        this.trip_id = trip_id;
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Integer trip_id) {
        this.trip_id = trip_id;
    }

    @Override
    public Integer getId() {
        return 0;
    }

    @Override
    public void setId(Integer i) {

    }
}
