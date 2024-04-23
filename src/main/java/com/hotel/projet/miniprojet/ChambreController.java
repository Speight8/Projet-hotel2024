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
public class ChambreController implements Initializable {

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

    private Connection connexion;
    private ConnexionBD connexionBD;
    private PreparedStatement pst;

    public static final ObservableList<Chambre> observeChambre = FXCollections.observableArrayList();
    public static final List<Chambre> listeChambre = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)

    {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        numChambre.setCellValueFactory(new PropertyValueFactory<>("numChambre"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        nbLits.setCellValueFactory(new PropertyValueFactory<>("nbLits"));
        typeSdb.setCellValueFactory(new PropertyValueFactory<>("typeSdb"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        try {
            initListeChambre();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableChambres.setItems(observeChambre);
    }

    public void initListeChambre() throws IOException {
        listeChambre.clear();
        observeChambre.clear();
        String query = "SELECT * FROM chambre";
        try {
            pst = connexion.prepareStatement(query);
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
        }
    }


    @FXML
    public void gestionAjoutChambre(ActionEvent actionEvent) throws IOException {
        Stage add = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ajout-chambre.fxml"));
        Scene scene = new Scene(root);
        add.setScene(scene);
        add.show();
    }
}
