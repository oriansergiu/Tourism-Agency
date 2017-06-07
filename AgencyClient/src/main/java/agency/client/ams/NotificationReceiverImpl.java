package agency.client.ams;

import agency.model.notifications.Notification;
import agency.services.NotificationReceiver;
import agency.services.NotificationSubscriber;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by Sergiu on 5/12/2017.
 */
public class NotificationReceiverImpl implements NotificationReceiver{

    private JmsOperations jmsOperations;
    private boolean running;

    public NotificationReceiverImpl(JmsOperations operations) {
        this.jmsOperations = operations;
    }

    ExecutorService service;
    NotificationSubscriber subscriber;


    @Override
    public void start(NotificationSubscriber subscriber) {
        System.out.println("Starting notification receiver...");
        running = true;
        this.subscriber = subscriber;
        service = Executors.newSingleThreadExecutor();
        service.submit(()->{run();});
    }

    private void run(){
        while (running){
            try {
                Notification notif = (Notification) jmsOperations.receiveAndConvert();

                System.out.println("Received notification..." + notif);
                subscriber.notificationReceived(notif);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        running = false;
        try {
            service.awaitTermination(100, TimeUnit.MILLISECONDS);
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopped notification receiver");
    }
}
