package agency.client.ams;

import agency.client.utils.Observable;
import agency.client.utils.Observer;
import agency.model.entities.Agent;
import agency.model.entities.Trip;
import agency.model.notifications.Notification;
import agency.services.AgencyExceptions;
import agency.services.IAgencyServicesAMS;
import agency.services.NotificationSubscriber;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiu on 5/11/2017.
 */
public class AgencyClientCtrlAMS implements NotificationSubscriber, Observable<Trip> {

    private IAgencyServicesAMS server;
    private Agent agent;
    private Trip[] allTrips;
    private NotificationReceiverImpl receiver;
    private List<Observer<Trip>> listObservers = new ArrayList<Observer<Trip>>();

    public AgencyClientCtrlAMS(IAgencyServicesAMS server) throws AgencyExceptions {
        this.server = server;
    }

    public void setReceiver(NotificationReceiverImpl receiver)
    {
        this.receiver = receiver;
    }

    public void logout() throws AgencyExceptions{

    }

    public void login(String user, String password) throws AgencyExceptions {
        Agent agent = new Agent(user, password);
        server.login(agent);
        this.agent = agent;
        receiver.start(this);
    }

    public Trip[] getAll() throws AgencyExceptions {
        this.allTrips = server.getAllTrips();
        return allTrips;
    }

    @Override
    public void notificationReceived(Notification notif) {

        Integer tripElem = 0;

        for(int elem=0; elem < allTrips.length; elem++)
            if (allTrips[elem].getId().equals(notif.getTrip().getId()))
            {
                tripElem = elem;
                elem = allTrips.length;
            }
        final Integer el = tripElem;

        try {
            System.out.println("Ctrl notification received..." + notif.getType());
            SwingUtilities.invokeLater(() -> {
                        switch (notif.getType()) {
                            case BOOKING: {
                                System.out.println("AM INTRAT IN BOOKING.");
                                allTrips[el].setNumberOfSeats(notif.getTrip().getNumberOfSeats());
                                System.out.println(allTrips[el].getNumberOfSeats());
                                notifyObserver();
                                break;
                            }}});
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public List<Trip> filterByLandmarkAndTimeSlot(String landmark, String time) {
        List<Trip> trips = new ArrayList<Trip>();
        for (int elem =0; elem < this.allTrips.length; elem++) {
            if(this.allTrips[elem].getLandmark().compareTo(landmark) == 0 || this.allTrips[elem].getTime().compareTo(time) == 0)
                trips.add(this.allTrips[elem]);

        }
        return trips;
    }

    public Trip[] getAllTrips() {
        return allTrips;
    }

    public void sendTripToAll(boolean ok, String name, String phoneNumber, String tripTickets, Integer ticketsPurchased, Integer id, String landmark, String transportCompanyName, String time, String price, String numberOfSeats) throws AgencyExceptions {
        server.sendTripToAll(ok, name, phoneNumber, tripTickets, ticketsPurchased, id, landmark, transportCompanyName, time, price, numberOfSeats);
    }

    @Override
    public void addObserver(Observer<Trip> o) {
        listObservers.add(o);
    }

    @Override
    public void removeObserver(Observer<Trip> o) {

    }

    @Override
    public void notifyObserver() {
        for (Observer<Trip> o: listObservers) {
            o.update(this);
        }
    }
}
