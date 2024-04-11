package com.hotel.hotelmanagement;

import com.hotel.projet.miniprojet.ConnexionBD;
import com.hotel.projet.miniprojet.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class AjoutReservationController implements Initializable {

    @FXML
    private ComboBox<Integer> NbLits;
    @FXML
    private Label amount;
    @FXML
    private Button boutonValider;
    @FXML
    private TextField cinClient;
    @FXML
    private DatePicker dateArrivee;
    @FXML
    private DatePicker dateDepart;
    @FXML
    private Label days;
    @FXML
    private Label duree;
    @FXML
    private TextField emailClient;

    @FXML
    private TextField genreClient;

    @FXML
    private TextField nationaliteClient;

    @FXML
    private TextField nomClient;

    @FXML
    private ComboBox<?> numChambre;

    @FXML
    private TextField numTelClient;

    @FXML
    private Label price;

    @FXML
    private Label prix;

    @FXML
    private Label total;

    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
    }
    @FXML
    void gestionAjoutReservation(ActionEvent event) {
        //int nbLitsValue = parseInt(nbLits.)

    }

    @FXML
    void handleCheckInPick(ActionEvent event) {

    }

    @FXML
    void handleCheckOutPick(ActionEvent event) {

    }

    @FXML
    void handleSelectRoomNumber(ActionEvent event) {

    }

    @FXML
    void handleSelectRoomType(ActionEvent event) {

    }

}
