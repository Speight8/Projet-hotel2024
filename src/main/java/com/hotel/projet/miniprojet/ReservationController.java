package com.hotel.projet.miniprojet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    @FXML
    private Button boutonRechercher;

    @FXML
    private Button buttonCreerRes;
    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Date> dateArrive;

    @FXML
    private TableColumn<Reservation, Date> dateDepart;

    @FXML
    private TableColumn<Reservation, Integer> duree;

    @FXML
    private TableColumn<Reservation, String> nom_client;

    @FXML
    private TableColumn<Reservation, Float> prixTotal;

    @FXML
    private TextField recherche;

    @FXML
    private TableColumn<Reservation, Integer> roomNumber;

    @FXML
    private ComboBox<?> sort;

    @FXML
    private TableColumn<Reservation, String> status;

    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;

    public static final ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    public static final List<Reservation> reservationList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        dateArrive.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        dateDepart.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duration"));
        nom_client.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        prixTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        status.setCellValueFactory(new PropertyValueFactory<>("reservationStatus"));
    }
    @FXML
    void clickItem(MouseEvent event) {

    }

    @FXML
    void creerRes(ActionEvent event) {
            //int resIDValue = Integer.parseInt(0);
            int roomNumberValue = Integer.parseInt(roomNumber.getText());
            String nomClientValue = nom_client.getText();
            Date dateArriveValue = new Date();
            Date dateDepartValue = new Date();

            Reservation newReservation = new Reservation(0, roomNumberValue, nomClientValue, dateArriveValue, dateDepartValue, 0, 0, "");

            reservationList.add(newReservation);
            reservations.add(newReservation);
            reservationTable.setItems(reservations);


        }

    @FXML
    void handleComboboxSelection(ActionEvent event) {

    }

    @FXML
    void handleSearchKey(KeyEvent event) {

    }

}
