package com.example.mediatech.controller;

import com.example.mediatech.funktionalitaeten.CsvExport;
import com.example.mediatech.funktionalitaeten.GemeinsameMethoden;
import com.example.mediatech.medium.AbstractMedium;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller für den „Search“-Screen.
 * Er erbt von BaseController, deshalb sind Tabelle und globale Liste
 * schon vorbereitet.
 */
public class SearchMenuCon extends BaseController {

    /* ---------- FXML-Elemente ---------- */
    @FXML private Button SearchBTN;        // „SEARCH“-Button
    @FXML private Button CSVExportBTN;     // „EXPORT CSV“-Button
    @FXML private TextField SearchTF;      // Eingabe­feld für Suchtext
    @FXML private Label errorLabel;        // zeigt „Keine Ergebnisse“ o. Ä. an

    /* ==============================================================
       1) SEARCH-Button – Liste filtern und anzeigen
       ============================================================== */
    @FXML
    protected void onSearchBTN(ActionEvent e) {

        String input = SearchTF.getText().trim().toLowerCase(); // Suchwort holen

        ObservableList<AbstractMedium> matching = filterMatching(input); // filtern
        mediaTable.setItems(matching);                                  // Tabelle neu füllen

        // Hinweis ausgeben, falls nichts gefunden
        errorLabel.setText(matching.isEmpty() ? "Keine Ergebnisse" : "");
    }

    /* ==============================================================
       2) EXPORT-Button – gefilterte Liste als CSV speichern
       ============================================================== */
    @FXML
    public void onCSVExportBTN(ActionEvent e) {

        Stage stage = (Stage) CSVExportBTN.getScene().getWindow();      // aktuelle Stage holen

        String input = SearchTF.getText().trim().toLowerCase();         // selben Filter wie oben
        ObservableList<AbstractMedium> matching = filterMatching(input);

        CsvExport.exportToCSV(stage, matching);                         // Datei schreiben
    }

    /* ==============================================================
       3) Hilfs­methode: Textsuche über Titel, Autor, Jahr, Typ, Extras
       ============================================================== */
    private ObservableList<AbstractMedium> filterMatching(String input) {

        // Leere Eingabe → komplette Liste zurückgeben
        if (input.isEmpty()) return medienListe;

        ObservableList<AbstractMedium> treffend = FXCollections.observableArrayList();

        for (AbstractMedium m : medienListe) {
            // GemeinsameMethoden.suchen() prüft mehrere Felder gleichzeitig
            if (((GemeinsameMethoden) m).suchen(input)) {
                treffend.add(m);
            }
        }
        return treffend;
    }
}
