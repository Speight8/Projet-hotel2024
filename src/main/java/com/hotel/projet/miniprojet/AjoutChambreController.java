package com.hotel.projet.miniprojet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public  class AjoutChambreController extends AjoutController implements Initializable {

    @FXML
    private TextField nbLits;
    @FXML
    private TextField numChambre;
    @FXML
    private TextField prix;
    @FXML
    private TextField typeSdb;
    @FXML
    private ComboBox<String> boxEtat;
    private ObservableList<String> listeEtat = FXCollections.observableArrayList("Disponible", "Indisponible", "En rénovation","Autre");
    Chambre chambre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        boxEtat.setItems(listeEtat);
    }
    @FXML
    void valider(ActionEvent event) {
        try {
            int nChbre = Integer.parseInt(numChambre.getText());
            int nbLts = Integer.parseInt(nbLits.getText());
            String typesdb = typeSdb.getText();
            float prx = Float.parseFloat(prix.getText());
            String etat = boxEtat.getSelectionModel().getSelectedItem();

            if (nChbre <= 0 || nbLts <= 0 || prx <= 0) {
                throw new IllegalArgumentException("Les valeurs doivent être supérieures à zéro.");
            }
            if (chambreExisteDeja(nChbre)) {
                throw new IllegalArgumentException("Ce numéro de chambre est déjà utilisé.");
            }
            this.chambre = new Chambre(nChbre, nbLts, typesdb, etat, prx);
            if (confirmationAjout) {
                ajoutChambreBD(this.chambre);
            } else if (confirmationModification) {
                modifierChambreBD(this.chambre);
            }
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            NavigationController.messageErreur("Veuillez saisir des nombres valides.", "Erreur de saisie");
        } catch (IllegalArgumentException | SQLException e) {
            NavigationController.messageErreur(e.getMessage(), "Erreur de saisie");
        }
    }

    boolean chambreExisteDeja(int nChbre) throws SQLException {
        String query = "SELECT * FROM client WHERE cin_client = ?";
        ResultSet rs = pst.executeQuery(query);
        Integer nbOccurences =0;
        while (rs.next()) {
            nbOccurences  +=1;
        }
        return (nbOccurences!=0);
    }

    @Override
    public void afficherItem(){
        Chambre chambre = ListeChambresController.listeChambre.get(ListeChambresController.indiceItemModifie);
        numChambre.setText(String.valueOf(chambre.getNumChambre()));
        nbLits.setText(String.valueOf(chambre.getNbLits()));
        typeSdb.setText(chambre.getTypeSdb());
        prix.setText(String.valueOf(chambre.getPrix()));
        boxEtat.setValue(chambre.getEtat());

    }
    void ajoutChambreBD(Chambre chambre){
        String query = "INSERT INTO chambre (num_chambre, nb_lits, type_sdb, prix,etat) VALUES (?,?,?,?,?)";
        try {
            pst = connexion.prepareStatement(query);
            String etat = boxEtat.getSelectionModel().getSelectedItem();
            pst.setInt(1, chambre.getNumChambre());
            pst.setInt(2, chambre.getNbLits());
            pst.setString(3, typeSdb.getText());
            pst.setFloat(4,chambre.getPrix());
            pst.setString(5,etat);
            pst.executeUpdate();
            ListeChambresController.listeChambre.add(chambre);
            ListeChambresController.observeChambre.add(chambre);
            confirmationAjout = false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    void modifierChambreBD(Chambre chambre){
        connexionBD = new ConnexionBD();
        Connection connexion = connexionBD.getConnection();
        String requeteSQL = "UPDATE chambre SET num_chambre = ?, nb_lits = ?, type_sdb = ?, prix  = ?, etat = ? WHERE num_chambre = ?";

        try (
                PreparedStatement pst = connexion.prepareStatement(requeteSQL)) {
            pst.setInt(1, chambre.getNumChambre());
            pst.setInt(2, chambre.getNbLits());
            pst.setString(3, chambre.getTypeSdb());
            pst.setFloat(4, chambre.getPrix());
            pst.setInt(5, chambre.getNumChambre());
            pst.setString(6,chambre.getEtat());
            pst.executeUpdate();
            ListeChambresController.listeChambre.set(ListeChambresController.indiceItemModifie,chambre);
            ListeChambresController.observeChambre.set(ListeChambresController.indiceItemModifie,chambre);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        confirmationModification = false;

    }
    }
