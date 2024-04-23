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
    private TableView<Reservation> tableReservation;
    @FXML
    private TableColumn<Reservation, String> dateArrive;
    @FXML
    private TableColumn<Reservation, String> dateDepart;
    @FXML
    private TableColumn<Reservation, Integer> duree;
    @FXML
    private TableColumn<Reservation, String> nom_client;
    @FXML
    private TableColumn<Reservation, Float> prixTotal;
    @FXML
    private TextField recherche;
    @FXML
    private TableColumn<Reservation, Integer> numChambre;
    @FXML
    private ComboBox<?> sort;
    @FXML
    private TableColumn<Reservation, String> status;

    public static final ObservableList<Reservation> observeReservations = FXCollections.observableArrayList();
    public static final List<Reservation> listeReservation = new ArrayList<>();

    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        dateArrive.setCellValueFactory(new PropertyValueFactory<>("dateArrive"));
        dateDepart.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        nom_client.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        prixTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        numChambre.setCellValueFactory(new PropertyValueFactory<>("numChambre"));
        status.setCellValueFactory(new PropertyValueFactory<>("statut"));
        try {
            initListeReservation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableReservation.setItems(observeReservations);
    }

    public void initListeReservation() throws IOException, SQLException {
        listeReservation.clear();
        observeReservations.clear();

        String requete = "SELECT  res.num_reservation, res.num_chambre, c.nom_client, res.date_debut, res.date_fin, DATEDIFF(res.date_fin, res.date_debut) AS duree, (ch.prix * DATEDIFF(res.date_fin, res.date_debut)) AS total, ch.etat FROM client c\n INNER JOIN reservation res ON c.cin_client = res.cin_client\n INNER JOIN chambre ch ON ch.num_chambre = res.num_chambre\n";
        pst = connexion.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int numRes = Integer.parseInt(rs.getString("num_reservation"));
            int numChbre = Integer.parseInt(rs.getString("num_chambre"));
            String nomClt = rs.getString("nom_client");
            String dateDbt = rs.getString("date_debut");
            String dateFn = rs.getString("date_fin");
            float ttl = rs.getFloat("total");
            int duree1 = rs.getInt("duree");
            String status1 = rs.getString("etat");
            Reservation reservation = new Reservation(numRes, numChbre, nomClt, dateDbt, dateFn, duree1, ttl, status1);
            listeReservation.add(reservation);
            observeReservations.add(reservation);
        }
    }
    @FXML
    void creerRes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ajout-reservation.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

        }



}
