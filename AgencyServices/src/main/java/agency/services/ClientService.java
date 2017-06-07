//package agency.services;
//
//
//import agency.client.utils.Observable;
//import agency.client.utils.Observer;
//import agency.model.entities.Client;
//import agency.persistence.repository.ClientRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Sergiu on 3/20/2017.
// */
//public class ClientService implements Observable<Client> {
//
//    List<Observer<Client>> observers = new ArrayList<>();
//    ClientRepository repo;
//
//    public ClientService(ClientRepository repo) {
//        this.repo = repo;
//    }
//
//    public void update(Client newClient)
//    {
//        repo.update(newClient);
//        notifyObserver();
//    }
//
//    public Client findByPhoneNumber(String phoneNumber)
//    {
//        for (Client cl: getAll()
//             ) {
//            if(cl.getPhoneNumber().equals(phoneNumber))
//                return cl;
//        }
//        return null;
//    }
//
//    public List<Client> getAll() {
//        List<Client> clients = new ArrayList<Client>();
//        for (Client t: repo.findAll()
//                ) { clients.add(t);
//
//        }
//        return clients;
//    }
//
//    @Override
//    public void addObserver(Observer<Client> o) {
//        observers.add(o);
//    }
//
//    @Override
//    public void removeObserver(Observer<Client> o) {
//        observers.remove(o);
//    }
//
//    @Override
//    public void notifyObserver() {
//        for (Observer<Client> obs: observers
//             ) {
//            obs.update(this);
//        }
//    }
//
//    public void save(Client addClient) {
//
//        repo.save(addClient);
//        notifyObserver();
//    }
//}
