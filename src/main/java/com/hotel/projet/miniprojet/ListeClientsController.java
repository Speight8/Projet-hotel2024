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

    @FXML
    private TableView<Client> listeClients;

    @FXML
    private TableColumn<Client, String> nomClient;


    private Connection connexion;
    private ConnexionBD connexionBD;
    private PreparedStatement pst;

    public static int indiceClientModifie;
    public static final ObservableList<Client> observeClient = FXCollections.observableArrayList();
    public static final List<Client> clientList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
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
        String query = "SELECT * FROM client";
        try {
            pst = connexion.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("nom_client");
                long cin = rs.getLong("cin_client");
                String nationalite = rs.getString("nationalite_client");
                String telephone = rs.getString("telephone_client");
                String genre = rs.getString("genre");
                String mail = rs.getString("adresse_email");
                Client client = new Client(cin,nom,nationalite,telephone,genre,mail);
                clientList.add(client);
                observeClient.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gestionAjoutClient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        Parent root = loader.load();
        ClientController ajoutController = loader.getController();
        ajoutController.confirmationAjout=true;
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void gestionModifierClient(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        Parent root = loader.load();
        Client clientModifie = listeClients.getSelectionModel().getSelectedItem();
        indiceClientModifie = listeClients.getSelectionModel().getFocusedIndex();
        ClientController modifController = loader.getController();
        modifController.afficherClient(clientModifie);
        modifController.confirmationModification = true;
        initremp();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();



    }

    @FXML
    void gestionSupprimerClient(ActionEvent event) throws IOException {
        Client clientSupprime = listeClients.getSelectionModel().getSelectedItem();
        ClientController supprimerController = new ClientController();
        supprimerController.supprimerClientBD(clientSupprime);
        initremp();

    }
    }


