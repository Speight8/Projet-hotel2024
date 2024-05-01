module com.hotel.projet.miniprojet {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.base;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.j;
    opens com.hotel.projet.miniprojet to javafx.fxml;
    exports com.hotel.projet.miniprojet;
}