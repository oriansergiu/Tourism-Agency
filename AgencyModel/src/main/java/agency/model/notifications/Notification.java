package agency.model.notifications;

import agency.model.entities.Trip;

/**
 * Created by Sergiu on 5/11/2017.
 */
public class Notification {
    private Trip trip;
    private NotificationType type;

    public Notification() {
    }

    public Notification(NotificationType type) {
        this.type = type;
    }

    public Notification(Trip trip, NotificationType type) {
        this.trip = trip;
        this.type = type;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "trip=" + trip +
                ", type=" + type +
                '}';
    }
}
