//package agency.services;
//
//import agency.client.utils.Observable;
//import agency.client.utils.Observer;
//import agency.model.entities.Trip;
//import agency.persistence.repository.TripRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Sergiu on 3/19/2017.
// */
//public class TripService implements Observable<Trip> {
//
//
//    List<Observer<Trip>> observers = new ArrayList<Observer<Trip>>();
//    TripRepository repo;
//
//    public TripService(TripRepository repo) {
//        this.repo = repo;
//    }
//
//    public void update(Trip newTrip) {
//        repo.update(newTrip);
//        notifyObserver();
//    }
//
//    @Override
//    public void addObserver(Observer<Trip> o) {
//        observers.add(o);
//    }
//
//    @Override
//    public void removeObserver(Observer<Trip> o) {
//        observers.remove(o);
//    }
//
//    @Override
//    public void notifyObserver() {
//        for (Observer<Trip> obs: observers
//             ) {
//            obs.update(this);
//        }
//    }
//
//    public List<Trip> getAll() {
//        List<Trip> trips = new ArrayList<>();
//        for (Trip t: repo.findAll()
//             ) { trips.add(t);
//
//        }
//        return trips;
//    }
//
//
//    public List<Trip> filterByLandmarkAndTimeSlot(String landmark, String time)
//    {
//        List<Trip> trips = new ArrayList<Trip>();
//        for (Trip t: repo.filterBy(landmark, time)
//                ) { trips.add(t);
//
//        }
//        return trips;
//    }
//
//
//}
