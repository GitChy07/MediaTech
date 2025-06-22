module com.example.mediatech {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.core;

    opens com.example.mediatech to javafx.fxml;
    exports com.example.mediatech;
    exports com.example.mediatech.funktionalitaeten;
    opens com.example.mediatech.funktionalitaeten to javafx.fxml;
    exports com.example.mediatech.controller;
    opens com.example.mediatech.controller to javafx.fxml;
}