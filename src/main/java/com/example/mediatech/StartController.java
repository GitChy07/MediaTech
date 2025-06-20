package com.example.mediatech;

import com.example.mediatech.medium.AbstractMedium;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartController {

    @FXML
    public void onCSVImportBTN(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Liste importieren
        ObservableList<AbstractMedium> importierteListe = CSVImport.importFromCSV(stage);

        if (importierteListe != null && !importierteListe.isEmpty()) {
            // Liste in globaler ObservableList speichern
            Starter.medienListe.setAll(importierteListe);

            // Wechsel in Hauptmenü (AddMenuUI)
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddMenuUI.fxml")));
            Scene scene = new Scene(root, 700, 520);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Keine gültigen Daten importiert.");
            // Optional: zeige Warnung an
        }
    }

    @FXML
    public void showAddMedia(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddMenuUI.fxml")));
        Scene scene = new Scene(root, 700, 520);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
