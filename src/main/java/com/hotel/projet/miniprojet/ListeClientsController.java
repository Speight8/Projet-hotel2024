package com.hotel.projet.miniprojet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class ListeClientsController extends ListeController implements Initializable {

    @FXML
    private TableView<Client> listeClients;
    @FXML
    private TableColumn<Client, String> nomClient;
    @FXML
    private TableColumn<Client, Integer> cinClient;
    @FXML
    private TableColumn<Client, String> nationClient;
    @FXML
    private TableColumn<Client, String> genreClient;
    @FXML
    private TableColumn<Client, String> emailClient;
    @FXML
    private TableColumn<Client, String> telClient;
    @FXML
    private ComboBox<Integer> meilleurClient;
    @FXML
    private Button BoutonRecherche;



    private ObservableList<Integer> listeNombres = FXCollections.observableArrayList(1, 2, 3, 4,5);

    public static final ObservableList<Client> observeClient = FXCollections.observableArrayList();
    public static final List<Client> clientList = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.cheminFXML = "client.fxml";
        this.tableItems = listeClients;
        connexionBD = new ConnexionBD();
        connexion = connexionBD.getConnection();
        nomClient.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cinClient.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nationClient.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
        telClient.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        genreClient.setCellValueFactory(new PropertyValueFactory<>("genre"));
        emailClient.setCellValueFactory(new PropertyValueFactory<>("email"));
        try {
            afficherListe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listeClients.setItems(observeClient);
        meilleurClient.setItems(listeNombres);
    }


    @FXML
    public void gestionAjoutClient(ActionEvent event) throws IOException {
        versAjout();
    }
    @FXML
    void gestionModifierClient(ActionEvent event) throws IOException, SQLException {
        versModification();
    }

    @FXML
    void gestionSupprimerClient(ActionEvent event) throws IOException {
        Client clientSupprime = listeClients.getSelectionModel().getSelectedItem();
        AjoutClientController supprimerController = new AjoutClientController();
        supprimerController.supprimerClientBD(clientSupprime);
        afficherListe();
    }


    @Override
    public void afficherListe() throws IOException {
            try {
                pst = connexion.prepareStatement("SELECT * FROM client");
                afficherListe(pst);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
    }
    @Override
    public void afficherListe(PreparedStatement pst)throws IOException {
            clientList.clear();
            observeClient.clear();
            try {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String nom = rs.getString("nom_client");
                    long cin = rs.getLong("cin_client");
                    String nationalite = rs.getString("nationalite_client");
                    String telephone = rs.getString("telephone_client");
                    String genre = rs.getString("genre");
                    String mail = rs.getString("adresse_email");
                    Client client = new Client(cin, nom, nationalite, telephone, genre, mail);
                    clientList.add(client);
                    observeClient.add(client);
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
    void choixMeilleur(ActionEvent event) {
        clientList.clear();
        observeClient.clear();
        int numChoix = meilleurClient.getSelectionModel().getSelectedItem();
        String requete = "SELECT c.* FROM client c\n  JOIN (\n SELECT cin_client, COUNT(*) AS nombre_reservations\n FROM reservation r\n GROUP BY cin_client\n ORDER BY COUNT(*) DESC\n LIMIT ?) r  ON c.cin_client = r.cin_client;";
        choisirMeilleur(requete,numChoix);

    }
    @FXML
    void vershome(MouseEvent event) {
        NavigationController.retourHomePage(event);
    }
    @FXML
    void rechercheClient(ActionEvent event) {

    }

}


