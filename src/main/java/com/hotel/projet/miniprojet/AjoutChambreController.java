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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjoutChambreController implements Initializable {

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

    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;

    public boolean confirmationAjout = false ;
    public boolean confirmationModification = false ;
    private ObservableList<String> listeEtat = FXCollections.observableArrayList("Disponible", "Indisponible", "En rénovation","Autre");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        boxEtat.setItems(listeEtat);
    }
    @FXML
    void valider(ActionEvent event) {
        int nChbre = Integer.parseInt(numChambre.getText());
        int nbLts = Integer.parseInt(nbLits.getText());
        String typesdb = typeSdb.getText();
        float prx = Float.parseFloat(prix.getText());
        String etat = boxEtat.getSelectionModel().getSelectedItem();
        Chambre chambre = new Chambre(nChbre,nbLts,typesdb,etat,prx);
        if(confirmationAjout){
            ajoutChambreBD(chambre);
        } else if (confirmationModification) {
            modifierChambreBD(chambre);
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    void afficherChambre(Chambre chambre){
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
            ListeChambresController.listeChambre.set(ListeChambresController.indiceChambreModifie,chambre);
            ListeChambresController.observeChambre.set(ListeChambresController.indiceChambreModifie,chambre);
            confirmationModification = false;

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    }
