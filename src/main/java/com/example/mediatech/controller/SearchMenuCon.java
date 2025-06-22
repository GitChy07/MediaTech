package com.example.mediatech.controller;

import com.example.mediatech.funktionalitaeten.CsvExport;
import com.example.mediatech.funktionalitaeten.GemeinsameMethoden;
import com.example.mediatech.medium.AbstractMedium;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchMenuCon extends BaseController {

    @FXML public Button SearchBTN;
    @FXML public Button CSVExportBTN;
    @FXML private TextField SearchTF;
    @FXML private Label errorLabel;

    @FXML
    protected void onSearchBTN(ActionEvent actionEvent) {
        // Hole den Suchbegriff
        String input = SearchTF.getText().trim().toLowerCase();

        // Hole die gefilterte Liste basierend auf dem Suchbegriff
        ObservableList<AbstractMedium> matching = filterMatching(input);

        // Zeige die gefilterte Liste in der TableView an
        mediaTable.setItems(matching);

        // Fehlermeldung, wenn keine Ergebnisse gefunden wurden
        if (matching.isEmpty()) {
            errorLabel.setText("Keine Ergebnisse");
        } else {
            errorLabel.setText("");
        }
    }

    @FXML
    public void onCSVExportBTN(ActionEvent actionEvent) {
        // Hole die Stage (Fenster), um sie an den CsvExport-Service zu übergeben
        Stage stage = (Stage) CSVExportBTN.getScene().getWindow();

        // Hole die gefilterte Liste basierend auf der Suche
        String input = SearchTF.getText().trim().toLowerCase();
        ObservableList<AbstractMedium> matching = filterMatching(input);

        // Rufe den Export-Service auf und übergebe die gefilterte Liste
        CsvExport.exportToCSV(stage, matching);
    }

    @FXML
    private ObservableList<AbstractMedium> filterMatching(String input) {
        ObservableList<AbstractMedium> matching = FXCollections.observableArrayList();

        // Wenn der Suchbegriff leer ist, gebe alle Medien zurück
        if (input.isEmpty()) {
            return medienListe; // Gibt die komplette Liste zurück, wenn kein Suchbegriff angegeben wurde
        }

        // Durchlaufe alle Medien und prüfe, ob der Suchbegriff in Titel, Autor, Jahr oder Typ enthalten ist
        for (AbstractMedium medium : medienListe) {
            if (((GemeinsameMethoden) medium).suchen(input.toLowerCase())) {
                matching.add(medium); // Füge das Medium zur Liste hinzu, wenn es passt
            }
        }
        return matching; // Rückgabe der gefilterten Liste
    }

}