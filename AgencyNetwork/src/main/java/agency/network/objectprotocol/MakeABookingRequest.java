package agency.network.objectprotocol;

import agency.network.dto.MakeABookingDTO;

/**
 * Created by Sergiu on 4/20/2017.
 */
public class MakeABookingRequest implements Request {

    private MakeABookingDTO book;

    public MakeABookingRequest(MakeABookingDTO book) {
        this.book = book;
    }

    public MakeABookingDTO getBook() {
        return book;
    }
}
