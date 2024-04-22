package com.hotel.projet.miniprojet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class ListeClientsController implements Initializable {

    /*@FXML
    private TableView<Client> listeClients;
    @FXML
    private TableColumn<Client, String> nomClient;
        */
    @FXML
    private TableView<Client> listeClients;

    @FXML
    private TableColumn<Client, String> nomClient;


    private Connection connexion;
    private ConnexionBD connexionBD;
    private PreparedStatement pst;

    public static final ObservableList<Client> observeClient = FXCollections.observableArrayList();
    public static final List<Client> clientList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    //cette méthode initialise la connexion à la base de données, associe les propriétés des objets Room
    // aux colonnes de la table roomTable, initialise la liste des chambres
    // à partir de la base de données et lie cette liste à la table pour afficher les données.
    {
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        nomClient.setCellValueFactory(new PropertyValueFactory<>("nom"));
        try {
            initremp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listeClients.setItems(observeClient);
    }

    public void initremp() throws IOException {
        observeClient.clear();
        clientList.clear();
        String query = "SELECT nom_client FROM client";
        try {
            pst = connexion.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom_client");
                clientList.add(new Client(nom));
                observeClient.add(new Client(nom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gestionAjoutClient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        }
    }


