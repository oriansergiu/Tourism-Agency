package agency.client;

import agency.client.ams.AgencyClientCtrlAMS;
import agency.client.view.AgentViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;


/**
 * Created by Sergiu on 5/11/2017.
 */
public class ClientAMSMain extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Login");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Pane myPane = (Pane) loader.load();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        AgencyClientCtrlAMS ctrl=context.getBean("chatCtrl",AgencyClientCtrlAMS.class);


        AgentViewController agCtrl = loader.getController();
        agCtrl.setAgentController(ctrl);
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);

        primaryStage.show();
    }


    public static void main(String[] args) {
     launch(args);
    }

}
