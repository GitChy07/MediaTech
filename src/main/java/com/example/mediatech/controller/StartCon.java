package com.example.mediatech.controller;

import com.example.mediatech.Starter;
import com.example.mediatech.funktionalitaeten.CSVImport;
import com.example.mediatech.medium.AbstractMedium;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.util.Objects;

public class StartCon {

    @FXML private void onCSVImportBTN(ActionEvent e) throws IOException {

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        ObservableList<AbstractMedium> list = CSVImport.importFromCSV(stage);

        // Liste in globaler ObservableList speichern
        Starter.medienListe.setAll(list);

        // Wechsel in Hauptmenü (AddMenuUI)
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mediatech/AddMenuUI.fxml")));
        Scene scene = new Scene(root, 700, 520);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/mediatech/style.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();

    }

    @FXML private void showAddMedia(ActionEvent e) throws IOException {

        // Wechsel in Hauptmenü (AddMenuUI) (ohne CSV-Import)
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/mediatech/AddMenuUI.fxml")));
        Scene scene = new Scene(root, 700, 520);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/mediatech/style.css")).toExternalForm());

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
