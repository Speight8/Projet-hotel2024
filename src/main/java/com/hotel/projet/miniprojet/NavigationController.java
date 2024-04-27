package com.hotel.projet.miniprojet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class NavigationController {

    public static void retourHomePage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource("homepage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void retourPageBouton(ActionEvent event, String path) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void messageErreur(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /* public static void redirectionAjout(String cheminFXML, boolean indiceConfirmation){
         try {
             FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(path));
             Parent root = loader.load();
             Scene scene = new Scene(root);
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }*/
    //methode generique pour remplir un objet selon une requete utile pour : init / recherche /trie

    public static Object remplirObjet(Object obj, PreparedStatement pst) throws IOException, SQLException {
        try {
            ResultSet rs = pst.executeQuery();
            if (obj instanceof Reservation) {
                List<Reservation> listeObjet = new ArrayList<>();
                while (rs.next()) {
                    int numRes = Integer.parseInt(rs.getString("num_reservation"));
                    int numChbre = Integer.parseInt(rs.getString("num_chambre"));
                    String nomClt = rs.getString("nom_client");
                    String dateDbt = rs.getString("date_debut");
                    String dateFn = rs.getString("date_fin");
                    float ttl = rs.getFloat("total");
                    int duree1 = rs.getInt("duree");
                    String status1 = rs.getString("etat");
                    obj = new Reservation(numRes, numChbre, nomClt, dateDbt, dateFn, duree1, ttl, status1);
                    listeObjet.add(obj);
                }

            } else if (obj instanceof Client) {
                List<Client> listeObjet = new ArrayList<>();
                while (rs.next()) {
                    String nom = rs.getString("nom_client");
                    long cin = rs.getLong("cin_client");
                    String nationalite = rs.getString("nationalite_client");
                    String telephone = rs.getString("telephone_client");
                    String genre = rs.getString("genre");
                    String mail = rs.getString("adresse_email");
                    obj = new Client(cin, nom, nationalite, telephone, genre, mail);
                    listeObjet.add(obj);
                }
            } else {
                List<Chambre> listeObjet = new ArrayList<>();
                while (rs.next()) {
                    int numChambre1 = Integer.parseInt(rs.getString("num_chambre"));
                    String etat1 = rs.getString("etat");
                    int nbLits1 = Integer.parseInt(rs.getString("nb_lits"));
                    String typeSdb1 = rs.getString("type_sdb");
                    float prix1 = Float.parseFloat(rs.getString("prix"));
                    obj = new Chambre(numChambre1, nbLits1, typeSdb1, etat1, prix1);
                    listeObjet.add(obj);
                }
            }
            return listeObjet;
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}


