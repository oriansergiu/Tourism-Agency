package agency.persistence.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by grigo on 3/4/17.
 */
public class JdbcUtils {
    private Properties jdbcProps;
    private String jdbcUrl;
    private String jdbcDriver;

    public JdbcUtils(Properties props){
        jdbcProps=props;
    }
    private  Connection instance=null;

    public JdbcUtils(String jdbcUrl, String jdbcDriver) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcDriver = jdbcDriver;
    }

    private Connection getNewConnection(){
        //String driver=jdbcProps.getProperty("tasks.jdbc.driver");
        //String url=jdbcProps.getProperty("tasks.jdbc.url");
        //String user=jdbcProps.getProperty("tasks.jdbc.user");
        //String pass=jdbcProps.getProperty("tasks.jdbc.pass");
        String driver = jdbcDriver;
        String url = jdbcUrl;
        String user = null;
        String pass = "";
        Connection con=null;
        System.out.println("DRIVER " + jdbcDriver + "URL " + jdbcUrl);
        try {
            Class.forName(driver);
            if (user!=null && pass!=null)
                con= DriverManager.getConnection(url,user,pass);
            else
                con=DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver "+e);
        } catch (SQLException e) {
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return instance;
    }
}
