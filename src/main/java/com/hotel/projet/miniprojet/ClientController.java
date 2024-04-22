package com.hotel.projet.miniprojet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.security.auth.callback.ConfirmationCallback;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField cin;
    @FXML
    private TextField gender;
    @FXML
    private TextField email;
    @FXML
    private TextField nationalité;
    @FXML
    private TextField phone;
    @FXML
    private Button valider;
    private ConnexionBD connexionbd;
    private Connection connexion;
    private PreparedStatement pst;
    public boolean confirmationAjout = false ;
    public boolean confirmationModification = false ;

    public ClientController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connexionbd = new ConnexionBD();
    }

    @FXML
    private void Valider(ActionEvent event) {
        String nom = name.getText();
        long cinClient = Long.parseLong(cin.getText());
        String nationalite = nationalité.getText();
        String genre = gender.getText();
        String emailClient = email.getText();
        String telephone = (phone.getText());
        Client client = new Client(cinClient, nom, nationalite, telephone, genre, emailClient);


        if (confirmationAjout) {
            ajouterClientBD(client);
            ListeClientsController.clientList.add(client);
            ListeClientsController.observeClient.add(client);
        } else if (confirmationModification) {
            modifierClientBD(client);
        }


        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    public void afficherClient(Client client){
        name.setText(client.getNom());
        cin.setText(String.valueOf(client.getCin()));
        nationalité.setText(client.getNationalite());
        gender.setText(client.getGenre());
        email.setText(client.getEmail());
        phone.setText(client.getNum_tel());
    }
    public void ajouterClientBD(Client client) {
        connexionbd = new ConnexionBD();
        Connection connexion = connexionbd.getConnection();
        String requeteSQL = "INSERT INTO client (cin_client, nom_client, nationalite_client, telephone_client, genre, adresse_email)  VALUES (?, ?, ?, ?, ?, ?)";

        try (
                PreparedStatement pst = connexion.prepareStatement(requeteSQL)) {

            pst.setLong(1, client.getCin());
            pst.setString(2, client.getNom());
            pst.setString(3, client.getNationalite());
            pst.setString(4, client.getNum_tel());
            pst.setString(5, client.getGenre());
            pst.setString(6, client.getEmail());
            pst.executeUpdate();
            confirmationAjout = false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void modifierClientBD(Client client) {

        connexionbd = new ConnexionBD();
        Connection connexion = connexionbd.getConnection();
        String requeteSQL = "UPDATE client SET cin_client = ?, nom_client = ?, nationalite_client = ?, telephone_client  = ?, genre = ?, adresse_email = ? WHERE cin_client = ?";

        try (
                PreparedStatement pst = connexion.prepareStatement(requeteSQL)) {
            pst.setLong(1, client.getCin());
            pst.setString(2, client.getNom());
            pst.setString(3, client.getNationalite());
            pst.setString(4, client.getNum_tel());
            pst.setString(5, client.getGenre());
            pst.setString(6, client.getEmail());
            pst.setLong(7, client.getCin());
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        confirmationModification = false;
    }

}