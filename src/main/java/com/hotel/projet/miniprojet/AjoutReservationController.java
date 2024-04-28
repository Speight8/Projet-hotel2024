package com.hotel.projet.miniprojet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static com.hotel.projet.miniprojet.NavigationController.messageErreur;
import static java.lang.Integer.parseInt;

public  class AjoutReservationController extends AjoutController implements Initializable {

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
    private Label prix;
    @FXML
    private Label total;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        setChoixNbLits();
    }

    private void setChoixNbLits() {
        nbLits.getItems().removeAll(nbLits.getItems());
        String query = "SELECT DISTINCT nb_lits FROM chambre";
        try {
            pst = connexion.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Integer nb_lits = rs.getInt("nb_lits");
                nbLits.getItems().add(nb_lits);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void setChoixNumChambre() {

        numChambre.getItems().removeAll(numChambre.getItems());
        Integer nbLitsValue = nbLits.getSelectionModel().getSelectedItem();
        String query = "SELECT num_chambre FROM chambre WHERE nb_lits=? AND etat='Disponible'";
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, nbLitsValue);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Integer numChbre = rs.getInt("num_chambre");
                numChambre.getItems().add(numChbre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gestionAjoutReservation(ActionEvent event) throws SQLException {
        try {
            String nom = nomClient.getText();
            long cin = Long.parseLong(cinClient.getText());
            String nationalite = nationaliteClient.getText();
            String genre = genreClient.getText();
            String email = emailClient.getText();
            String telephone = numTelClient.getText();
            String checkIn = dateArrivee.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String checkOut = dateDepart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Integer nChbre = numChambre.getSelectionModel().getSelectedItem();

            // Vérification des champs obligatoires
            if (nom.isEmpty() || nationalite.isEmpty() || genre.isEmpty() || email.isEmpty() || telephone.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty() || nChbre == null) {
                throw new IllegalArgumentException("Veuillez remplir tous les champs obligatoires.");
            }

            // Vérification du format de l'email (optionnel)
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Veuillez saisir une adresse e-mail valide.");
            }
            if (LocalDate.parse(checkOut).isBefore(LocalDate.parse(checkIn))) {
                throw new IllegalArgumentException("La date de départ doit être postérieure à la date d'arrivée.");
            }

            Client client = new Client(cin, nom, nationalite, telephone, genre, email);

            String query = "SELECT * FROM client WHERE cin_client = ?";
            pst = connexion.prepareStatement(query);
            pst.setLong(1, client.getCin());
            ResultSet rs = pst.executeQuery();
            Integer nbOccurencesClient = 0;
            while (rs.next()) {
                nbOccurencesClient++;
            }
            if (nbOccurencesClient == 0) {
                AjoutClientController ajoutController = new AjoutClientController();
                ajoutController.ajouterClientBD(client);
            } else {
                AjoutClientController modificationController = new AjoutClientController();
                modificationController.modifierClientBD();
            }

            String insertReservation = "INSERT INTO reservation(cin_client,num_chambre, date_debut, date_fin) VALUES (?, ?, ?, ?)";
            pst = connexion.prepareStatement(insertReservation);
            pst.setLong(1, client.getCin());
            pst.setInt(2, nChbre);
            pst.setString(3, checkIn);
            pst.setString(4, checkOut);
            pst.executeUpdate();

            String updateChambre = "UPDATE chambre SET etat='Indisponible' WHERE num_chambre=?";
            pst = connexion.prepareStatement(updateChambre);
            pst.setInt(1, nChbre);
            pst.executeUpdate();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            messageErreur("Veuillez saisir un numéro de téléphone valide.", "Erreur de saisie");
        } catch (IllegalArgumentException e) {
            messageErreur(e.getMessage(), "Erreur de saisie");
        }
    }

    boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    @FXML
    void gestionArrivee(ActionEvent event) {
        String date = dateArrivee.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    @FXML
    void gestionDepart(ActionEvent event) {
        int nbNuits = dateDepart.getValue().compareTo(dateArrivee.getValue());
        duree.setText("" + nbNuits);
        try {
            float prixNuit = Float.parseFloat(prix.getText());
            total.setText("" + (prixNuit * nbNuits));
        }
        catch (Exception e){
            messageErreur("veuillez","Oops");
        }
    }
    @FXML
    void gestionNbLits(ActionEvent event) {
        if (!nbLits.getSelectionModel().getSelectedItem().equals("")) {
            setChoixNumChambre();
        }
    }
    @FXML
    void gestionPrixChambre(ActionEvent event) {
        Integer no = numChambre.getSelectionModel().getSelectedItem();
        String requetePrix = "SELECT prix FROM chambre WHERE num_chambre=?";
        float valeurPrix =  0;
        try {
            pst = connexion.prepareStatement(requetePrix);
            pst.setInt(1, no);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                valeurPrix = rs.getFloat("prix");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prix.setText(Float.toString(valeurPrix));
    }
    @FXML
    void gestionAnnulation(ActionEvent event) {
       // NavigationUtils.retourPageBouton(event, "ajout-reservation.fxml");
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

}
