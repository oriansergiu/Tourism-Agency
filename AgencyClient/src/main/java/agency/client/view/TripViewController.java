package agency.client.view;


import agency.client.ams.AgencyClientCtrlAMS;
import agency.client.utils.Observable;
import agency.client.utils.Observer;
import agency.model.entities.Trip;
import agency.model.exceptions.AgentException;
import agency.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by Sergiu on 3/19/2017.
 */
public class TripViewController implements IAgencyClient, Observer<Trip> {

    @FXML
    private TableView<Trip> tripTable;

    @FXML
    private TableColumn<Trip, Integer> idColumn;
    @FXML
    private TableColumn<Trip, String> landmarkColumn;
    @FXML
    private TableColumn<Trip, String> transportCompanyNameColumn;
    @FXML
    private TableColumn<Trip, String> timeColumn;
    @FXML
    private TableColumn<Trip, String> priceColumn;
    @FXML
    private TableColumn<Trip, String> numberOfSeatsColumn;

    @FXML
    private TableView<Trip> tripTable1;

    @FXML
    private TableColumn<Trip, Integer> idColumn1;
    @FXML
    private TableColumn<Trip, String> landmarkColumn1;
    @FXML
    private TableColumn<Trip, String> transportCompanyNameColumn1;
    @FXML
    private TableColumn<Trip, String> timeColumn1;
    @FXML
    private TableColumn<Trip, String> priceColumn1;
    @FXML
    private TableColumn<Trip, String> numberOfSeatsColumn1;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField ticketsTextField;

    @FXML
    private TextField landmarkTextField;

    @FXML
    private TextField timeSlotTextField;

    @FXML
    private Button bookButton;

    @FXML
    private Button logoutButton;

    String agent;
    AgencyClientCtrlAMS ctrl;
    ObservableList<Trip> model;

    List<Trip> filtersList;

    public TripViewController() {
    }
    public void colorRow() {
        tripTable.setRowFactory(tv -> new TableRow<Trip>() {
            @Override
            public void updateItem(Trip item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getNumberOfSeats().equals("0")) {
                    setStyle("-fx-background-color: red;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    public void setController(String user, AgencyClientCtrlAMS ctrl)
    {
        this.agent = user;
        this.ctrl = ctrl;

        try {
            System.out.println(ctrl.getAll().length);
            //tripTable.getItems().setAll(ctrl.getAll());
            this.model = FXCollections.observableArrayList(ctrl.getAllTrips());
            tripTable.setItems(model);

        } catch (AgencyExceptions agencyExceptions) {
            agencyExceptions.printStackTrace();
        }
    }

    @FXML
    private void initialize()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("id"));
        landmarkColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("landmark"));
        transportCompanyNameColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("transportCompanyName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("time"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("price"));
        numberOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("numberOfSeats"));
        colorRow();
        idColumn1.setCellValueFactory(new PropertyValueFactory<Trip, Integer>("id"));
        landmarkColumn1.setCellValueFactory(new PropertyValueFactory<Trip, String>("landmark"));
        transportCompanyNameColumn1.setCellValueFactory(new PropertyValueFactory<Trip, String>("transportCompanyName"));
        timeColumn1.setCellValueFactory(new PropertyValueFactory<Trip, String>("time"));
        priceColumn1.setCellValueFactory(new PropertyValueFactory<Trip, String>("price"));
        numberOfSeatsColumn1.setCellValueFactory(new PropertyValueFactory<Trip, String>("numberOfSeats"));
    }

    @FXML
    public void handleBook()
    {
        boolean ok = true;
        String name = nameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String tickets = ticketsTextField.getText();

        Integer ticketsPurchased = 0;
        String tripTickets = "";

        try {
            tripTickets = tripTable.getSelectionModel().getSelectedItem().getNumberOfSeats();

        } catch (NullPointerException e)
        {
            ok = false;
            MessageAlert.showErrorMessage(null, "Select an trip from trips table.");
        }

        if(ok)
        try {
            ticketsPurchased = Integer.parseInt(tickets);
        } catch (Exception e)
        {
            MessageAlert.showErrorMessage(null, "In tickets texfield please insert a number biger than 0.");
            ticketsTextField.clear();
            ok = false;
        }



        if(ok) {
            Trip newTrip = tripTable.getSelectionModel().getSelectedItem();
            try {
                ctrl.sendTripToAll(ok, name, phoneNumber, tripTickets, ticketsPurchased, newTrip.getId(), newTrip.getLandmark(), newTrip.getTransportCompanyName(), newTrip.getTime(), newTrip.getPrice(), newTrip.getNumberOfSeats());
                tripTable.getItems().setAll(ctrl.getAllTrips());

            } catch (AgencyExceptions agencyExceptions) {
                agencyExceptions.printStackTrace();
            }
        }
        if (ticketsPurchased == Integer.parseInt(tripTickets)) {
            colorRow();
        }

        /*
        Client client = clientService.findByPhoneNumber("4" + phoneNumber);

        if(ok && ticketsPurchased <= Integer.parseInt(tripTickets) && ticketsPurchased > 0 && client == null)
        {
            Client addClient = new Client(1, name,phoneNumber, agent.getId().toString());
            clientService.save(addClient);
        }
        Client actualClient = clientService.findByPhoneNumber("4" + phoneNumber);

        if( ok && ticketsPurchased <= Integer.parseInt(tripTickets) && ticketsPurchased > 0) {


            Integer refreshTickets = Integer.parseInt(tripTickets) - ticketsPurchased;
            newTrip.setNumberOfSeats(refreshTickets.toString());
            service.update(newTrip);
            client_tripService.save(actualClient.getId(), newTrip.getId(), ticketsPurchased);

        } else if (ok) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Tickets are insufficient", "Sorry! We don't have enough tickets.");
        }*/

    }



    @FXML
    public void handleSearch()
    {
        List<Trip> lista = new ArrayList<>();
        lista = ctrl.filterByLandmarkAndTimeSlot(landmarkTextField.getText(), timeSlotTextField.getText());
        tripTable1.getItems().setAll(lista);
    }

    @FXML
    public void handleLogout() {
        //System.out.println("LOGOUT: " + user);
        // Agent user = agentService.findUser(agent.getUser());

        try {
            System.out.println("LOGOUT: TRY");
            ctrl.logout();
            System.out.println("LOGOUT: TRY - OUT");

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();

        } catch (AgencyExceptions agencyExceptions) {
            agencyExceptions.printStackTrace();
        }
    }

        @Override
    public void makeABookReceived() {

    }

    @Override
    public void update(Observable<Trip> o) {
        AgencyClientCtrlAMS ctrl= (AgencyClientCtrlAMS) o;
        model.setAll(ctrl.getAllTrips());
    }
}
