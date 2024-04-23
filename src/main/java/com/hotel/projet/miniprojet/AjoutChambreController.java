package com.hotel.projet.miniprojet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
    }
    @FXML
    void gestionAjoutChambre(ActionEvent event) {
        String query = "INSERT INTO chambre (num_chambre, nb_lits, type_sdb, prix) VALUES (?,?,?,?)";
        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1, numChambre.getText());
            pst.setString(2, nbLits.getText());
            pst.setString(3, typeSdb.getText());
            pst.setString(4,prix.getText());
            Chambre chambre = new Chambre(Integer.parseInt(numChambre.getText()), Integer.parseInt(nbLits.getText()), typeSdb.getText(), "Disponible", Float.parseFloat(prix.getText()));
            ChambreController.listeChambre.add(chambre);
            ChambreController.observeChambre.add(chambre);
            pst.executeUpdate();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
