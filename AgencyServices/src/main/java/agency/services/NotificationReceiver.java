package agency.services;

/**
 * Created by Sergiu on 5/12/2017.
 */
public interface NotificationReceiver {
    void start(NotificationSubscriber subscriber);
    void stop();
}
