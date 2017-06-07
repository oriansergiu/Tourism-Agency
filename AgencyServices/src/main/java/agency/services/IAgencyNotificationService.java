package agency.services;

import agency.model.entities.Trip;

/**
 * Created by Sergiu on 5/10/2017.
 */
public interface IAgencyNotificationService {
    void newBooking(Trip trip);
}
