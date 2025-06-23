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

/**
 *  Start-Fenster des Programms
 *  ---------------------------
 *  • Button 1:  „CSV importieren“  →  liest eine Datei ein und springt dann ins Add-Menü
 *  • Button 2:  „Neue Liste“       →  überspringt den Import und öffnet direkt das Add-Menü
 *
 *  Idee:  Schon am Anfang entscheidet der / die Nutzer*in, ob bestehende Daten
 *         geladen oder ganz neu begonnen werden sollen.
 */
public class StartCon {

    /* =======================================================
       1)  CSV-Import  (Button: Import)
       ======================================================= */
    @FXML
    private void onCSVImportBTN(ActionEvent e) throws IOException {

        // -------- 1. Aktuelles Fenster (Stage) ermitteln --------
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        // -------- 2. Datei auswählen & einlesen --------
        ObservableList<AbstractMedium> list = CSVImport.importFromCSV(stage);

        // -------- 3. Eingelesene Medien in die globale Liste kopieren --------
        Starter.medienListe.setAll(list);

        // -------- 4. Zum Add-Menü wechseln (siehe Methode unten) --------
        switchToAddMenu(stage);
    }

    /* =======================================================
       2)  Ohne Import starten  (Button: Neue Liste)
       ======================================================= */
    @FXML
    private void showAddMedia(ActionEvent e) throws IOException {

        // Fenster ermitteln …
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        // … und direkt zum Add-Screen springen
        switchToAddMenu(stage);
    }

    /* =======================================================
       3)  Gemeinsame Hilfsmethode für den Szenenwechsel
       ======================================================= */
    private void switchToAddMenu(Stage stage) throws IOException {

        // 1. FXML laden
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("/com/example/mediatech/AddMenuUI.fxml")));

        // 2. Neue Szene erstellen
        Scene scene = new Scene(root, 700, 520);

        // 3. Stylesheet anhängen
        scene.getStylesheets().add(
                Objects.requireNonNull(
                                getClass().getResource("/com/example/mediatech/style.css"))
                        .toExternalForm());

        // 4. Szene in der Stage setzen und anzeigen
        stage.setScene(scene);
        stage.show();
    }
}
