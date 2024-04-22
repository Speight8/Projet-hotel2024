package com.hotel.projet.miniprojet;

import com.hotel.projet.miniprojet.ConnexionBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class AjoutReservationController implements Initializable {

    @FXML
    private ComboBox<Integer> nbLits;

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
    private ComboBox<Integer> numChambre;

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
    private void insertRoomNo() {
        numChambre.getItems().removeAll(numChambre.getItems());
        Integer nbLitsValue = nbLits.getSelectionModel().getSelectedItem();
        String query = "SELECT roomNumber FROM chambre WHERE nbLits=? AND status=NULL";
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, nbLitsValue);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String room_no = rs.getString("roomNumber");
               // numChambre.getItems().add(room_no);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gestionArrivee(ActionEvent event) {

    }

    @FXML
    void gestionDepart(ActionEvent event) {

    }

    @FXML
    void gestionNumChambre(ActionEvent event) {

    }

    @FXML
    void gestionNbLits(ActionEvent event) {

    }

}
