package agency.persistence.repository;


import agency.model.entities.Client_trip;
import agency.persistence.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Sergiu on 3/19/2017.
 */
public class Client_TripRepository implements Repository<Integer, Client_trip> {

    private JdbcUtils dbUtils;

    public Client_TripRepository(Properties props) {this.dbUtils = new JdbcUtils(props);}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Client_trip entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Client_trip(client_id, trip_id, numberOfSeats) values (?,?,?)")){
            preStmt.setInt(1,entity.getClient_id());
            preStmt.setInt(2,entity.getTrip_id());
            preStmt.setInt(3,entity.getNumberOfSeats());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Client_trip entity) {

    }

    @Override
    public Client_trip findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Client_trip> findAll() {
        return null;
    }
}
