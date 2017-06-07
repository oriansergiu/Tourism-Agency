package agency.client.view;

import agency.model.entities.Trip;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiu on 4/20/2017.
 */
public class TripsListModel extends AbstractListModel {

    private List<Trip> trips;

    public TripsListModel() {
        trips = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return trips.size();
    }

    @Override
    public Object getElementAt(int index) {
        return trips.get(index);
    }

    public void makeABooking(Trip trip)
    {
        boolean ok = false;
        for (int elem = 0; elem < trips.size(); elem++)
            if(trips.get(elem).getId().equals(trip.getId())) {
                trips.get(elem).setNumberOfSeats(trip.getNumberOfSeats());
                ok = true;
                elem = trips.size();
            }
        if(!ok)
        {
            trips.add(trip);
        }
        fireContentsChanged(this, 0, trips.size());
    }
}
