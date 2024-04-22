package com.hotel.projet.miniprojet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private ConnexionBD connexionbd;
    private Connection connexion;
    private PreparedStatement pst;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){ connexionbd = new ConnexionBD();
    }
    @FXML
    void gestionLogin(ActionEvent event) throws SQLException {
        connexion = connexionbd.getConnection();
        String query="SELECT * FROM users WHERE name=? AND password=?";
        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1,username.getText());
            pst.setString(2,password.getText());
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                login.getScene().getWindow().hide();
                Stage acceuil = new Stage();
                FXMLLoader  fxmlLoader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                acceuil.setScene(scene);
                acceuil.show();
            }
            else OptionPane("Mot de passe ou nom invalide(s)","Oops");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void OptionPane(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
