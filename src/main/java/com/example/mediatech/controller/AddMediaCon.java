package com.example.mediatech.controller;

import com.example.mediatech.medium.AbstractMedium;
import com.example.mediatech.medium.Buch;
import com.example.mediatech.medium.DVD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller für den „Add Media“-Screen.
 * Er erbt von BaseController, damit die Tabelle und die globale Liste
 * schon fertig eingerichtet sind.
 */
public class AddMediaCon extends BaseController {

    /* ---------- FXML-Felder ---------- */
    // Eingabefelder für Grunddaten
    @FXML private TextField TitleTF, AuthorTF, YearTF;
    // RadioButtons, damit der Benutzer den Medientyp wählen kann
    @FXML private RadioButton buchRB, dvdRB;
    // Label für Fehlermeldungen
    @FXML private Label errorLabel;

    /* ==============================================================
       RadioButtons: es darf immer nur einer gleichzeitig aktiv sein
       ============================================================== */

    @FXML
    protected void toggleA() {
        // Wenn DVD gewählt wurde, „Buch“ automatisch deaktivieren
        if (dvdRB.isSelected()) {
            dvdRB.setSelected(false);
            buchRB.setSelected(true);
        }
    }

    @FXML
    protected void toggleB() {
        // Wenn Buch gewählt wurde, „DVD“ automatisch deaktivieren
        if (buchRB.isSelected()) {
            buchRB.setSelected(false);
            dvdRB.setSelected(true);
        }
    }

    /* ==============================================================
       ADD-Button: neues Medium anlegen und in die Liste einfügen
       ============================================================== */

    @FXML
    protected void onAddButtonClick() {
        // 1) Daten aus den Textfeldern lesen
        String titel = TitleTF.getText();
        String autor = AuthorTF.getText();

        /* ------- Gültige Jahreszahl prüfen ------- */
        int jahr;
        try {
            jahr = Integer.parseInt(YearTF.getText());
            if (jahr == 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            errorLabel.setText("Bitte ein gültiges Jahr eingeben");
            return;                           // Abbruch, wenn Jahr ungültig
        }

        /* ------- Pflichtfelder leer? ------- */
        if (titel.isEmpty() || autor.isEmpty()) {
            errorLabel.setText("Titel und Autor dürfen nicht leer sein");
            return;
        }

        /* ------- Passenden Objekttyp erzeugen ------- */
        AbstractMedium medium;
        if (buchRB.isSelected()) {
            medium = new Buch(titel, autor, jahr);
        } else if (dvdRB.isSelected()) {
            medium = new DVD(titel, autor, jahr);
        } else {
            errorLabel.setText("Bitte Buch oder DVD auswählen");
            return;
        }

        /* ------- Objekt in die globale Liste legen ------- */
        medienListe.add(medium);

        /* ------- Eingabefelder & Meldungen zurücksetzen ------- */
        TitleTF.clear();
        AuthorTF.clear();
        YearTF.clear();
        errorLabel.setText("");
    }

    /* ==============================================================
       DELETE-Button: Medium aus Tabelle + Liste entfernen
       ============================================================== */

    @FXML
    protected void onDeleteButtonClick() {
        // aktuell markiertes Medium in der Tabelle holen
        AbstractMedium ausgewählt = mediaTable.getSelectionModel().getSelectedItem();

        if (ausgewählt != null) {
            // Sicherheitsabfrage, damit man nichts versehentlich löscht
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Löschen bestätigen");
            confirm.setHeaderText("Medium wirklich löschen?");
            confirm.setContentText("Möchtest du \"" + ausgewählt.getTitel() + "\" entfernen?");

            // Dialog anzeigen und auf Antwort warten
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    medienListe.remove(ausgewählt);   // wirklich löschen
                }
            });
        } else {
            // Es war gar nichts ausgewählt → Hinweis anzeigen
            Alert warnung = new Alert(Alert.AlertType.WARNING);
            warnung.setTitle("Keine Auswahl");
            warnung.setHeaderText("Kein Medium ausgewählt");
            warnung.setContentText("Bitte zuerst ein Medium in der Tabelle markieren.");
            warnung.show();
        }
    }
}
