package agency.network.dto;

import agency.model.entities.Trip;

import java.io.Serializable;

/**
 * Created by Sergiu on 4/20/2017.
 */
public class MakeABookingDTO implements Serializable {

    private boolean ok;
    private String name;
    private String phoneNumber;
    private String tripTickets;
    private Integer ticketsPurchased;
    private Integer id;
    private String landmark;
    private String transportCompany;
    private String time;
    private String price;
    private String numberOfSeats;

    public MakeABookingDTO(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Integer id, String landmark, String transportCompanyName, String time, String price, String numberOfSeats) {
        this.ok = ok;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.tripTickets = tripTickets;
        this.ticketsPurchased = ticketsPurchased;
        this.id = id;
        this.landmark = landmark;
        this.transportCompany = transportCompanyName;
        this.time = time;
        this.price = price;
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isOk() {
        return ok;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTripTickets() {
        return tripTickets;
    }

    public Integer getTicketsPurchased() {
        return ticketsPurchased;
    }

    public Integer getId() {
        return id;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getTransportCompany() {
        return transportCompany;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }
}
