package agency.network.dto;

import java.io.Serializable;

/**
 * Created by Sergiu on 4/3/2017.
 */
public class TripDTO implements Serializable {
    private Integer id;
    private String landmark;
    private String transportCompanyName;
    private String time;
    private String price;
    private String numberOfSeats;

    public TripDTO(Integer id, String landmark, String transportCompanyName, String time, String price, String numberOfSeats) {
        this.id = id;
        this.landmark = landmark;
        this.transportCompanyName = transportCompanyName;
        this.time = time;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getTransportCompanyName() {
        return transportCompanyName;
    }

    public void setTransportCompanyName(String transportCompanyName) {
        this.transportCompanyName = transportCompanyName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer integer) {
        this.id = integer;
    }
}
