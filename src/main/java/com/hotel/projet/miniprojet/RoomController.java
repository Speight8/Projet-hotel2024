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
import javafx.scene.input.KeyEvent;
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
public class RoomController implements Initializable {
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, Integer> roomNumber;
    @FXML
    private TableColumn<Room, String> Status;
    @FXML
    private TableColumn<Room, Integer> numberOfBeds;
    @FXML
    private TableColumn<Room,String>  bathroomType;
    @FXML
    private TableColumn<Room,String> telephoneNumber;
    @FXML
    private TextField search;
    private Connection connection;

    private ConnexionBD dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<Room> rooms = FXCollections.observableArrayList();

    public static final List<Room> roomList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    //cette méthode initialise la connexion à la base de données, associe les propriétés des objets Room
    // aux colonnes de la table roomTable, initialise la liste des chambres
    // à partir de la base de données et lie cette liste à la table pour afficher les données.
    {
        dbConnection = new ConnexionBD();
        roomNumber = new TableColumn<>();
        connection = dbConnection.getConnection();
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        numberOfBeds.setCellValueFactory(new PropertyValueFactory<>("numberOfBeds"));
        bathroomType.setCellValueFactory(new PropertyValueFactory<>("bathroomType"));
        telephoneNumber.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        try {
            initRoomList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        roomTable.setItems(rooms);
    }

    public void initRoomList() throws IOException {
        roomList.clear();
        rooms.clear();
        String query = "SELECT * FROM rooms";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int roomNumber =  Integer.parseInt(rs.getString("num_chambre"));
                String status = rs.getString("etat");
                int numberOfBeds = Integer.parseInt(rs.getString("nbr_de_lits"));
                String bathroomType = rs.getString("type_salle_bain");
                String telephoneNumber = rs.getString("num_telephone");
                roomList.add(new Room(roomNumber, status, numberOfBeds, bathroomType, telephoneNumber));
                rooms.add(new Room(roomNumber, status, numberOfBeds, bathroomType, telephoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleAddAction(ActionEvent event) {

        int roomNumberValue = Integer.parseInt(roomNumber.getText());
        String statusValue = Status.getText();
        int numberOfBedsValue = Integer.parseInt(numberOfBeds.getText());
        String bathroomTypeValue = bathroomType.getText();
        String telephoneNumberValue = telephoneNumber.getText();

        Room newRoom = new Room(roomNumberValue, statusValue, numberOfBedsValue, bathroomTypeValue, telephoneNumberValue);
        roomList.add(newRoom);
        rooms.add(newRoom);

        roomTable.setItems(rooms);
        addRoomToDatabase(newRoom);
    }
    private void addRoomToDatabase(Room room){
        try {
        String query = "INSERT INTO rooms (roomNumber, status, numberOfBeds, bathroomType, telephoneNumber) VALUES (?, ?, ?, ?, ?)";

        pst = connection.prepareStatement(query);
        pst.setInt(1, room.getRoomNumber());
        pst.setString(2, room.getStatus());
        pst.setInt(3, room.getNumberOfBeds());
        pst.setString(4, room.getBathroomType());
        pst.setString(5, room.getTelephoneNumber());

        pst.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public void clickItem(MouseEvent event) throws IOException {
    if (event.getClickCount() == 2) {
        if (roomTable.getSelectionModel().getSelectedItem() != null) {
            if (roomTable.getSelectionModel().getSelectedItem().getStatus().equals("Booked")) {
               //nahi l commmentaire kif tetgad lclasse ClientController
                // ClientController.setSelectedRoomNumber(roomTable.getSelectionModel().getSelectedItem().getRoomNumber());
                Stage add = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("reservation.fxml"));// najmou narbtouha
                Scene scene = new Scene(root);
                add.setScene(scene);
                add.show();
            }
        }
    }
}
private void Search(ObservableList<Room> rooms, String s) {
    rooms.clear();
    for (int i = 0; i < roomList.size(); i++) {
        if (Integer.toString(roomList.get(i).getRoomNumber()).indexOf(s) == 0) {
            rooms.add(roomList.get(i));
        }
    }
}

public void handleSearchKey(KeyEvent event) {
    if (event.getEventType() == KeyEvent.KEY_RELEASED) {
        String s = search.getText();
        Search(rooms, s);
    }
}
}
