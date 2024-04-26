package com.hotel.projet.miniprojet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class HomePageController implements Initializable {

    @FXML
    private Label adminName1;

    @FXML
    private Button dash1;

    @FXML
    private Button dash2;

    @FXML
    private Button dash3;

    @FXML
    private Button dash4;

    @FXML
    private AnchorPane holdPane;

    @FXML
    private ImageView logout;

    private AnchorPane Pane;

    public static String name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private void setNode(Node node) {
        holdPane.getChildren().clear();
        holdPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void createDash(ActionEvent event) {

    }

    @FXML
    void handleLogout(MouseEvent event) {

    }

    @FXML
    void redirectToInterface(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String interfaceName = "";

        if (clickedButton == dash1) {
            interfaceName = "reservations.fxml";
        } else if (clickedButton ==dash2) {
            interfaceName = "chambre.fxml";
        } else if (clickedButton == dash3) {
            interfaceName = "PaiementInterface.fxml";
        } else if (clickedButton == dash4) {
            interfaceName = "liste-clients.fxml";
        }
        if (!interfaceName.isEmpty()) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(interfaceName)));
                clickedButton.getScene().getWindow().hide();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


