//package agency.services;
//
//import agency.client.utils.Observable;
//import agency.client.utils.Observer;
//import agency.model.entities.Client_trip;
//import agency.persistence.repository.Client_TripRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Sergiu on 3/20/2017.
// */
//public class Client_tripService implements Observable<Client_trip> {
//
//    List<Observer<Client_trip>> observers = new ArrayList<Observer<Client_trip>>();
//    Client_TripRepository repo;
//
//    public Client_tripService(Client_TripRepository repo) {
//        this.repo = repo;
//    }
//
//    public void save(Integer client_id, Integer trip_id, Integer numberOfSeats)
//    {
//        Client_trip newClient_trip = new Client_trip(1,client_id, trip_id, numberOfSeats);
//        repo.save(newClient_trip);
//        notifyObserver();
//    }
//
//    @Override
//    public void addObserver(Observer<Client_trip> o) {
//        observers.add(o);
//    }
//
//    @Override
//    public void removeObserver(Observer<Client_trip> o) {
//        observers.remove(o);
//    }
//
//    @Override
//    public void notifyObserver() {
//        for (Observer<Client_trip> obs: observers
//             ) {
//            obs.update(this);
//        }
//    }
//}
