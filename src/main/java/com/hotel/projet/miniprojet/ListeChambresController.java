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
import java.util.List;
import java.util.ResourceBundle;
public class ListeChambresController extends ListeController implements Initializable {

    @FXML
    private TableView<Chambre> tableChambres;
    @FXML
    private TableColumn<Chambre, String> etat;
    @FXML
    private TableColumn<Chambre, Integer> nbLits;
    @FXML
    private TableColumn<Chambre, Integer> numChambre;
    @FXML
    private TableColumn<Chambre, Float> prix;
    @FXML
    private TableColumn<Chambre, String> typeSdb;
    @FXML
    private ComboBox<Integer> Choix;

    private ObservableList<Integer> numbersList = FXCollections.observableArrayList(1, 2, 3, 4);
    public static final ObservableList<Chambre> observeChambre = FXCollections.observableArrayList();
    public static final List<Chambre> listeChambre = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)

    {
        this.cheminFXML = "ajout-chambre.fxml";
        this.tableItems = tableChambres;
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        numChambre.setCellValueFactory(new PropertyValueFactory<>("numChambre"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        nbLits.setCellValueFactory(new PropertyValueFactory<>("nbLits"));
        typeSdb.setCellValueFactory(new PropertyValueFactory<>("typeSdb"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        try {
            afficherListe();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableChambres.setItems(observeChambre);
        Choix.setItems(numbersList);
    }

    @Override
    public void afficherListe() throws IOException, SQLException {
        try{
            pst = connexion.prepareStatement("SELECT * FROM chambre");
            afficherListe(pst);}
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afficherListe(PreparedStatement pst) throws IOException{
        listeChambre.clear();
        observeChambre.clear();
        try {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int numChambre1 =  Integer.parseInt(rs.getString("num_chambre"));
                String etat1 = rs.getString("etat");
                int nbLits1 = Integer.parseInt(rs.getString("nb_lits"));
                String typeSdb1 = rs.getString("type_sdb");
                float prix1 = Float.parseFloat(rs.getString("prix"));
                Chambre chambre = new Chambre(numChambre1,nbLits1,typeSdb1,etat1,prix1);
                listeChambre.add(chambre);
                observeChambre.add(chambre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    @FXML
    public void gestionAjoutChambre(ActionEvent actionEvent) throws IOException {
        versAjout();
    }
    @FXML
    void gestionModifierChambre(ActionEvent event) throws IOException, SQLException {
        versModification();
    }
    @FXML
    void versHome(MouseEvent event) {
        NavigationController.retourHomePage(event);
    }

    @FXML
    void ChoixFiltre(ActionEvent event) {
        listeChambre.clear();
        observeChambre.clear();
        int numChoix = Choix.getSelectionModel().getSelectedItem();
        String requete = "SELECT * FROM chambre WHERE num_chambre IN (\n SELECT num_chambre FROM (\n SELECT num_chambre, COUNT(*) AS nombre_reservations \n FROM reservation \n  GROUP BY num_chambre \n  ORDER BY nombre_reservations DESC  \n LIMIT ? )AS subquery );";
        choisirMeilleur(requete,numChoix);
    }
}
