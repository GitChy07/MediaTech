package com.example.mediatech;

import com.example.mediatech.medium.AbstractMedium;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {

    // Die ObservableList, die mit Beispiel-Daten gefüllt wird
    public static final ObservableList<AbstractMedium> medienListe = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {

        // Beispiel-Datensätze beim Start hinzufügen
        TestDataGenerator.generateSampleData();  // Aufruf der Testdatengenerierung

        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("AddMenuUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 520);
        stage.setTitle("MediaTech");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
