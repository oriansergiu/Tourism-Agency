package agency.server;

import agency.model.entities.Trip;
import agency.model.notifications.Notification;
import agency.model.notifications.NotificationType;
import agency.services.IAgencyNotificationService;
import org.springframework.jms.core.JmsOperations;


/**
 * Created by Sergiu on 5/10/2017.
 */
public class NotificationServiceImpl implements IAgencyNotificationService {

    private JmsOperations jmsOperations;


    public NotificationServiceImpl(JmsOperations operations) {
        this.jmsOperations = operations;
    }

    @Override
    public void newBooking(Trip trip) {
        Notification notif = new Notification(trip, NotificationType.BOOKING);
        jmsOperations.convertAndSend(notif);
        System.out.println("Make a booking to ActiveMq..."  + trip.getNumberOfSeats());
    }
}
