package agency.services;

import agency.model.notifications.Notification;

/**
 * Created by Sergiu on 5/12/2017.
 */
public interface NotificationSubscriber {
    void notificationReceived(Notification notif);
}
