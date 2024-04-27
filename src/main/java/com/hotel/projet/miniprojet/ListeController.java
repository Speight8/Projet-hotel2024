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

    public TableView<?> tableItems;
    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;

    String cheminFXML;


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

       public void versModification () throws IOException, SQLException {
            indiceItemModifie = tableItems.getSelectionModel().getFocusedIndex();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.cheminFXML));
            Parent root = loader.load();
            AjoutController modifController = loader.getController(); //Polymprphisme
            modifController.confirmationModification = true;
            modifController.afficherItem();
            afficherListe();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }

        public  void choisirMeilleur(String requete, int numChoix){
            try {
                pst = connexion.prepareStatement(requete);
                pst.setInt(1, numChoix);
                afficherListe(pst);

            } catch (IOException e) {
                e.printStackTrace();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
}
