package com.hotel.projet.miniprojet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ListeController {
    @FXML
    private TableView<?> listeItems;
    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;

    String cheminFXML;
    Object ajoutController;
    Object modifierController;

    public static int indiceItemModifie;
    public static final ObservableList<Object> observeItems = FXCollections.observableArrayList();
    public static final List<Object> liste = new ArrayList<>();

    public abstract void afficherListe() throws IOException, SQLException;
    public abstract void afficherListe(PreparedStatement pst) throws IOException;

        public void versAjout () throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();
            AjoutController ajoutController = loader.getController();
            ajoutController.confirmationAjout = true;
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

        public void versModification () throws IOException {

        }

}
