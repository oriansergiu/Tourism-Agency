//package agency.services;
//
//import agency.client.utils.Observable;
//import agency.client.utils.Observer;
//import agency.model.entities.Agent;
//import agency.persistence.repository.AgentRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Sergiu on 3/19/2017.
// */
//public class AgentService implements Observable<Agent>
//{
//    List<Observer<Agent>> observers = new ArrayList<Observer<Agent>>();
//    AgentRepository repo;
//
//    public AgentService(AgentRepository repo) {
//        this.repo = repo;
//    }
//
//    public void save(Agent entity)
//    {
//        repo.save(entity);
//        notifyObserver();
//    }
//
//    @Override
//    public void addObserver(Observer<Agent> o) {
//        observers.add(o);
//    }
//
//    @Override
//    public void removeObserver(Observer<Agent> o) {
//        observers.remove(o);
//    }
//
//    @Override
//    public void notifyObserver() {
//        for (Observer<Agent> obs : observers
//             ) {
//            obs.update(this);
//        }
//    }
//
//    public Agent findUser(String username) {
//        return repo.findOne(username);
//    }
//
//    public Agent findById(Integer agent_id) {
//        System.out.println("Find By ID: "+ agent_id);
//        return repo.findOne(agent_id);
//    }
//}
