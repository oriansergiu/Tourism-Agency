package agency.network.objectprotocol;

import agency.network.dto.TripDTO;

/**
 * Created by Sergiu on 4/3/2017.
 */
public class NewBookingResponse implements UpdateResponse {
    private TripDTO booking;

    public NewBookingResponse(TripDTO booking) {
        this.booking = booking;
    }

    public TripDTO getBooking() {
        return booking;
    }
}
