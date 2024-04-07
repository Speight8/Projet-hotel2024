module com.hotel.projet.miniprojet {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    opens com.hotel.projet.miniprojet to javafx.fxml;
    exports com.hotel.projet.miniprojet;
}