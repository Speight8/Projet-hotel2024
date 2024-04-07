package com.hotel.projet.miniprojet;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

import javafx.fxml.FXMLLoader;

public class LoginController {
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Button login;
    @FXML
    public Button forgotpassword;
    @FXML
    public Button signup;

    private Connection connection;
    private ConnexionDB dbConnection;
    private PreparedStatement pst;
    @FXML
    public void gestionLogin(javafx.event.ActionEvent actionEvent) throws IOException {
        connection = dbConnection.getConnection();
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, username.getText());
            pst.setString(2, password.getText());
            ResultSet rs = pst.executeQuery();
            int count = 0;
            while (rs.next()) {
                HomePageController.name = rs.getString("name");
                count = 1;
            }
            if (count == 1) {
                login.getScene().getWindow().hide();
                Stage signup = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                Scene scene = new Scene(root);
                signup.setScene(scene);
                signup.show();
            } else {
                JOptionPane.showMessageDialog(null,"Username or Password is not Correct Error Message");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
