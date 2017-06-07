import agency.network.utils.AbstractServer;
import agency.network.utils.AgencyRpcAMSConcurrentServer;
import agency.network.utils.ServerExceptions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Sergiu on 5/10/2017.
 */
public class ServerAMSMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        AbstractServer server = context.getBean("agencyTCPServer", AgencyRpcAMSConcurrentServer.class);
        try {
            server.start();
        } catch (ServerExceptions e) {
            System.err.println("Error starting the server" + e.getMessage());
        }

    }
}