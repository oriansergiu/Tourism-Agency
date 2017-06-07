package agency.client;

import agency.client.view.AgentViewController;
import agency.client.view.ClientController;
import agency.model.entities.Client;
import agency.network.objectprotocol.AgencyObjectServerProxy;
import agency.services.IAgencyServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sergiu on 4/3/2017.
 */
public class ClientMain extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Login");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Pane myPane = (Pane) loader.load();

        Properties clientProps=new Properties();
        try {
            clientProps.load(ClientMain.class.getResourceAsStream("/agencyclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("agency.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("agency.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        IAgencyServer server=new AgencyObjectServerProxy(serverIP, serverPort);
        AgentViewController agCtrl = loader.getController();

        ClientController clCtrl = new ClientController(server);

      //  agCtrl.setAgentController(clCtrl);
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);

        primaryStage.show();
    }

    public static void main(String[] args) { launch(args);


    }



}
