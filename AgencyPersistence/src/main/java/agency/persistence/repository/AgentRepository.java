package agency.persistence.repository;

import agency.model.entities.Agent;
import agency.persistence.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Sergiu on 3/14/2017.
 */
public class AgentRepository implements Repository<Integer, Agent> {

    static SessionFactory sessionFactory;


    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception e){
            StandardServiceRegistryBuilder.destroy( registry );
        }

    }

    public AgentRepository(){
        initialize();
    }



    private void close() {
        if (sessionFactory != null)
            sessionFactory.close();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Agent entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
        } catch (RuntimeException ex){
            if(tx != null)
                tx.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Agent entity) {

    }

    @Override
    public Agent findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Agent> findAll() {
        return null;
    }

//    private JdbcUtils dbUtils;
//
//    public AgentRepository(Properties props) {
//        this.dbUtils = new JdbcUtils(props);
//    }
//
//    @Override
//    public int size() {
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Agen")) {
//            try(ResultSet result = preStmt.executeQuery()) {
//                if (result.next()) {
//                    return result.getInt("SIZE");
//                }
//            }
//        }catch(SQLException ex){
//            System.out.println("Error DB "+ex);
//        }
//        return 0;
//    }
//
//    @Override
//    public void save(Agent entity) {
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("insert into Agent(user,password,name) values (?,?,?)")){
//            preStmt.setString(1,entity.getUser());
//            preStmt.setString(2,entity.getPassword());
//            preStmt.setString(3,entity.getName());
//            int result=preStmt.executeUpdate();
//        }catch (SQLException ex){
//            System.out.println("Error DB "+ex);
//        }
//    }
//
//    @Override
//    public void delete(Integer integer) {
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("delete from Agent where id=?")){
//            preStmt.setInt(1,integer);
//            int result=preStmt.executeUpdate();
//        }catch (SQLException ex){
//            System.out.println("Error DB "+ex);
//        }
//    }
//
//    @Override
//    public void update(Agent entity) {
//        delete(entity.getId());
//        save(entity);
//    }
//
//    @Override
//    public Agent findOne(Integer integer) {
//        Connection con = dbUtils.getConnection();
//        try(PreparedStatement preStmt = con.prepareStatement("select * from Agent where Id=?")) {
//            preStmt.setInt(1,integer);
//            try(ResultSet res = preStmt.executeQuery()) {
//                if(res.next())
//                {
//                    Integer id = res.getInt("Id");
//                    String user = res.getString("user");
//                    String pass = res.getString("password");
//                    String name = res.getString("name");
//                    Agent foundAgent = new Agent(id, user, pass, name);
//                    return foundAgent;
//                }
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("Error DB "+ex);
//        }
//
//        return null;
//    }
//
    public Agent findOne(String username) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Agent> agents = session.createQuery("from Agent as A", Agent.class).list();
            tx.commit();
            System.out.println("REPOSITORY AGENT : " + agents.get(0).getName());

            for (Agent ag: agents
                 ) {
                if (ag.getUser().equals(username))
                    return ag;
            }

        } catch (RuntimeException ex) {
            if (tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

//        Connection con = dbUtils.getConnection();
//        try(PreparedStatement preStmt = con.prepareStatement("select * from Agent where user=?")) {
//            preStmt.setString(1,username);
//            try(ResultSet res = preStmt.executeQuery()) {
//                if(res.next())
//                {
//                    Integer id = res.getInt("Id");
//                    String user = res.getString("user");
//                    String pass = res.getString("password");
//                    String name = res.getString("name");
//                    Agent foundAgent = new Agent(id, user, pass, name);
//                    return foundAgent;
//                }
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("Error DB "+ex);
//        }
//
//        return null;
//    }
//
//
//
//    @Override
//    public List<Agent> findAll() {
//        return null;
//    }
}
