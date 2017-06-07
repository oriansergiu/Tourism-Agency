package agency.persistence.repository;


import agency.model.entities.Client;
import agency.persistence.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Sergiu on 3/14/2017.
 */
public class ClientRepository implements Repository<Integer, Client> {

    private JdbcUtils dbUtils;

    public ClientRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Client")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Client entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Client(name, phoneNumber) values (?,?)")){
            preStmt.setString(1,entity.getName());
            preStmt.setString(2,"4"+entity.getPhoneNumber());
            System.out.println(entity.getPhoneNumber());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Client where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Client entity) {
        delete(entity.getId());
        save(entity);
    }

    @Override
    public Client findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        Connection con=dbUtils.getConnection();
        List<Client> clients=new ArrayList<Client>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Client")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String name = result.getString("name");
                    String phoneNumber = result.getString("phoneNumber");


                    Client client = new Client(id, name, phoneNumber);
                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return clients;
    }

    public Client findByPhoneNumber(String phoneNumber)
    {
        for (Client cl: findAll()
                ) {
            if(cl.getPhoneNumber().equals(phoneNumber))
                return cl;
        }
        return null;
    }
}
