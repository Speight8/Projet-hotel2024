package com.hotel.projet.miniprojet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.hotel.projet.miniprojet.NavigationController.messageErreur;

public  class AjoutClientController extends AjoutController implements Initializable {
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
    Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
    }

    @FXML
    private void Valider(ActionEvent event) {
        try {
            String nom = name.getText();
            long cinClient = Long.parseLong(cin.getText());
            String nationalite = nationalité.getText();
            String genre = gender.getText();
            String emailClient = email.getText();
            String telephone = phone.getText();

            if (nom.isEmpty() || nationalite.isEmpty() || genre.isEmpty() || emailClient.isEmpty() || telephone.isEmpty()) {
                throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires.");
            }
            if (!isValidCin(cinClient) && confirmationAjout) {
                throw new IllegalArgumentException("Le client avec ce CIN existe déjà");
            }
            if (!isValidEmail(emailClient)) {
                throw new IllegalArgumentException("Veuillez saisir une adresse e-mail valide.");
            }

            this.client = new Client(cinClient, nom, nationalite, telephone, genre, emailClient);

            if (confirmationAjout) {
                ajouterClientBD(client);
            } else if (confirmationModification) {
                modifierClientBD();
            }

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            messageErreur("Veuillez saisir un numéro de téléphone valide.", "Erreur de saisie");
        } catch (IllegalArgumentException | SQLException e) {
            messageErreur(e.getMessage(), "Erreur de saisie");
        }
    }

    boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }
    boolean isValidCin(Long cin) throws SQLException {

        String query = "SELECT * FROM client WHERE cin_client = ?";
        pst = connexion.prepareStatement(query);
        pst.setLong(1, cin);
        ResultSet rs = pst.executeQuery();
        Integer nbOccurencesClient = 0;
        while (rs.next()) {
            nbOccurencesClient++;
        }
        return (nbOccurencesClient==0);
    }


    @Override
    public void afficherItem(){
        Client client = ListeClientsController.clientList.get(ListeClientsController.indiceItemModifie);
        name.setText(client.getNom());
        cin.setText(String.valueOf(client.getCin()));
        nationalité.setText(client.getNationalite());
        gender.setText(client.getGenre());
        email.setText(client.getEmail());
        phone.setText(client.getNum_tel());
    }
    public void ajouterClientBD(Client client) {
        connexionBD = new ConnexionBD();
        Connection connexion = connexionBD.getConnection();
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
            ListeClientsController.clientList.add(client);
            ListeClientsController.observeClient.add(client);
            confirmationAjout = false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void modifierClientBD() {

        connexionBD = new ConnexionBD();
        Connection connexion = connexionBD.getConnection();
        String requeteSQL = "UPDATE client SET cin_client = ?, nom_client = ?, nationalite_client = ?, telephone_client  = ?, genre = ?, adresse_email = ? WHERE cin_client = ?";
        Client client = this.client;
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
            ListeClientsController.clientList.set(ListeClientsController.indiceItemModifie,client);
            ListeClientsController.observeClient.set(ListeClientsController.indiceItemModifie,client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        confirmationModification = false;
    }
    public void supprimerClientBD(Client client){
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        String requeteSQL = "DELETE FROM reservation WHERE cin_client = ?" ;
        String requete ="DELETE  FROM  client WHERE cin_client = ?";

        try (PreparedStatement pst = connexion.prepareStatement(requeteSQL)) {
            pst.setLong(1, client.getCin());
            pst.executeUpdate();
            PreparedStatement pst2 =connexion.prepareStatement((requete));
            pst2.setLong(1, client.getCin());
            pst2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}