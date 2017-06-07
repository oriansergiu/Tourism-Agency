package agency.persistence.repository;

import agency.model.entities.Trip;
import agency.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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
@Component
//@Configuration
//@ComponentScan(basePackages = "AgencyServer")
//@PropertySource("classpath:agencyserver.properties")
public class TripRepository implements Repository<Integer, Trip> {

    private JdbcUtils dbUtils;

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
//    private String x;
//    @Value("${tasks.jdbc.url}")
//    public void testValue(String value) {
//        this.x = value;
//    }
//
//    @Value("${tasks.jdbc.url}")
//    private String jdbcUrl;
//
//    @Value("${tasks.jdbc.driver}")
//    private String jdbcDriver;

    public TripRepository() {

        //System.out.println("TRIPREPO DRIVER " + x + "URL " + jdbcUrl);
        this.dbUtils = new JdbcUtils("jdbc:sqlite:C:\\\\Users\\\\Sergiu\\\\Downloads\\\\SQLiteStudio\\\\ProjectMPP_Java", "org.sqlite.JDBC");
    }

    /*public TripRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }*/

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Trip")) {
            try(ResultSet rez = preStmt.executeQuery()) {
                if (rez.next()) {
                    return rez.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Trip entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Trip(landmark, transportCompanyName, time, price, numberOfSeats) values (?,?,?,?,?)")){
            preStmt.setString(1,entity.getLandmark());
            preStmt.setString(2,entity.getTransportCompanyName());
            preStmt.setString(3,entity.getTime());
            preStmt.setInt(4,Integer.parseInt(entity.getPrice()));
            preStmt.setInt(5,Integer.parseInt(entity.getNumberOfSeats()));
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Trip where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update( Trip entity) {
        //delete(entity.getId());
        //save(entity);

        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("UPDATE Trip SET numberOfSeats=? where id=? ")){
            preStmt.setInt(1,Integer.parseInt(entity.getNumberOfSeats()));
            preStmt.setInt(2,entity.getId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    public Iterable<Trip> filterBy(String landmark, String time) {
        String command = "";
        String parameter = "";


            Connection con = dbUtils.getConnection();
            List<Trip> trips = new ArrayList<>();
            try (PreparedStatement preStmt = con.prepareStatement("select * from Trip where landmark=? and time=?")) {
                preStmt.setString(1, landmark);
                preStmt.setString(2,time);
                try (ResultSet rez = preStmt.executeQuery()) {
                    while (rez.next()) {
                        Integer id_ = rez.getInt("id");
                        String landmark1 = rez.getString("landmark");
                        String transport = rez.getString("transportCompanyName");
                        String time1 = rez.getString("time");
                        Integer price = rez.getInt("price");
                        Integer numberOfSeats = rez.getInt("numberOfSeats");
                        String stPrice = price.toString();
                        String stNumberOfSeats = numberOfSeats.toString();
                        Trip trip1 = new Trip(id_, landmark1, transport, time1, stPrice, stNumberOfSeats);
                        trips.add(trip1);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error DB " + e);
            }
            return trips;

    }

    @Override
    public Trip findOne(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Trip where Id=?")) {
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("Id");
                    String landmark = result.getString("landmark");
                    String transport = result.getString("transportCompanyName");
                    String time = result.getString("time");
                    Integer price = result.getInt("price");
                    Integer numberOfSeats = result.getInt("numberOfSeats");

                    String strPrice = price.toString();
                    String strNumberOfSeats = numberOfSeats.toString();

                    Trip trip = new Trip(id, landmark, transport, time, strPrice, strNumberOfSeats);
                    return trip;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return null;
    }

    @Override
    public List<Trip> findAll() {

        Connection con=dbUtils.getConnection();
        List<Trip> trips=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Trip")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String landmark = result.getString("landmark");
                    String transport = result.getString("transportCompanyName");
                    String time = result.getString("time");
                    Integer price = result.getInt("price");
                    Integer numberOfSeats = result.getInt("numberOfSeats");

                    String strPrice = price.toString();
                    String strNumberOfSeats = numberOfSeats.toString();

                    Trip trip = new Trip(id, landmark, transport, time, strPrice, strNumberOfSeats);
                    trips.add(trip);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return trips;

    }

    public Trip[] getAll()
    {
//        List<Trip> allTrips = findAll();
//        Trip[] trips = new Trip[allTrips.size()];
//
//        for (Integer elem=0; elem < allTrips.size(); elem++) {
//            trips[elem] = allTrips.get(elem);
//        }
        System.out.println("FINDALL " + findAll().size());
        System.out.println("FINDALL array " + findAll().toArray(new Trip[findAll().size()]).length);
        return findAll().toArray(new Trip[findAll().size()]);
    }
}
