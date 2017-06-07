package agency.client.view;

import agency.client.ams.AgencyClientCtrlAMS;
import agency.client.utils.Observable;
import agency.model.entities.Agent;
import agency.model.exceptions.AgentException;
import agency.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by Sergiu on 3/19/2017.
 */
public class AgentViewController implements IAgencyClient {

    @FXML
    private TextField textFieldUser;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    Agent agent;
    AgencyClientCtrlAMS ctrl;

    public AgentViewController() {
    }

    public void setAgentController(AgencyClientCtrlAMS ctrl) {
        this.ctrl = ctrl;
    }

    public void handleLoginButton()
    {
        String username = textFieldUser.getText();
        String pass = password.getText();

        try {
            ctrl.login(username, pass);
            System.out.println("Login succeeded...");
            openAgentView(username, ctrl);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            //stage.close();
        } catch (AgencyExceptions agencyExceptions) {
            MessageAlert.showErrorMessage(null,"This client is already connect.");
        }

    }

    private void openAgentView(String user, AgencyClientCtrlAMS ctrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/trips.fxml"));
            Pane myPane = (AnchorPane) loader.load();
            /*Properties props=new Properties();
            try {
                props.load(new FileInputStream("/bd.config"));
            } catch (IOException e) {
                System.out.println("Eroare: "+e);
            }


            JdbcUtils jb = new JdbcUtils(props);

            ClientRepository clientRepo = new ClientRepository(props);
            ClientService clientService = new ClientService(clientRepo);

            Client_TripRepository client_tripRepository = new Client_TripRepository(props);
            Client_tripService client_tripService = new Client_tripService(client_tripRepository);

            TripRepository tripRepo = new TripRepository(props);
            TripService tripService = new TripService(tripRepo);*/
            TripViewController tripCtrl = loader.getController();
            System.out.println("Open Agent View: " + user);
            tripCtrl.setController(user, ctrl);
            ctrl.addObserver(tripCtrl);
            Scene myScene = new Scene(myPane);
            Stage stage = new Stage();

            stage.setTitle(user);
            stage.setScene(myScene);
            stage.show();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void makeABookReceived() {

    }
}
