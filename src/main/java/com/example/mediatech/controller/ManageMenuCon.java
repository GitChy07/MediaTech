package com.example.mediatech.controller;

import com.example.mediatech.funktionalitaeten.GemeinsameMethoden;
import com.example.mediatech.medium.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * „Manage“-Screen: Hier kann man Bücher / DVDs auswählen,
 * ALLE Infos sehen und ändern.
 */
public class ManageMenuCon extends BaseController {

    /* -----------------------------------------------------------
       1) Grund-Eingabefelder (immer sichtbar)
       ----------------------------------------------------------- */
    @FXML private TextField titleTF;   // Titel
    @FXML private TextField authorTF;  // Autor
    @FXML private TextField yearTF;    // Erscheinungsjahr

    /* -----------------------------------------------------------
       2) Extra-Felder (je nach Typ sichtbar)
       ----------------------------------------------------------- */
    @FXML private Label     extAttr1,  extAttr2;        // Beschriftungen
    @FXML private TextField extAttr1TF, extAttr2TF;     // Textfelder
    @FXML private TableColumn<AbstractMedium,String> extAttr1Col, extAttr2Col; // Spalten

    /* Fehlermeldungen landen hier */
    @FXML private Label errorLabel;

    /* ===========================================================
       INITIALISIERUNG
       =========================================================== */
    @Override
    public void initialize(URL u, ResourceBundle r) {

        super.initialize(u, r); // Tabelle (Titel/Autor/Jahr) kommt aus BaseController

        /* Cell-Factories für die Extra-Spalten:
           – getExtAttrVal(1) liefert ISBN oder FSK
           – getExtAttrVal(2) liefert Seiten (nur Buch)                */
        extAttr1Col.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(((GemeinsameMethoden) c.getValue()).getExtAttrVal(1)));
        extAttr2Col.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(((GemeinsameMethoden) c.getValue()).getExtAttrVal(2)));

        hideExtras(); // Start: Extras ausblenden

        /* Wenn Nutzer in der Tabelle klickt => Eingabefelder füllen   */
        mediaTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, alt, neu) -> fillFields(neu));
    }

    /** Blendet beide Extra-Spalten, Labels und Textfelder aus. */
    private void hideExtras() {
        extAttr1.setVisible(false); extAttr1Col.setVisible(false); extAttr1TF.setVisible(false);
        extAttr2.setVisible(false); extAttr2Col.setVisible(false); extAttr2TF.setVisible(false);
    }

    /** Überträgt Daten des angeklickten Mediums in die Textfelder. */
    private void fillFields(AbstractMedium m) {
        if (m == null) return;                       // nichts ausgewählt

        titleTF .setText(m.getTitel());              // Grunddaten
        authorTF.setText(m.getAutor());
        yearTF  .setText(String.valueOf(m.getErscheinungsjahr()));

        extAttr1TF.setText(((GemeinsameMethoden) m).getExtAttrVal(1)); // Extra 1
        extAttr2TF.setText(((GemeinsameMethoden) m).getExtAttrVal(2)); // Extra 2
    }

    /* ===========================================================
       BUTTONS FÜR TYP-FILTER (Buch / DVD)
       =========================================================== */
    public void showBuch(ActionEvent e) {
        mediaTable.setItems(filterByTyp("Buch")); // nur Bücher anzeigen

        // Spalten- & Label-Texte anpassen
        extAttr1.setText("ISBN");   extAttr1Col.setText("ISBN");
        extAttr2.setText("Seiten"); extAttr2Col.setText("Seiten");

        // alles sichtbar schalten
        extAttr1.setVisible(true);  extAttr1Col.setVisible(true);  extAttr1TF.setVisible(true);
        extAttr2.setVisible(true);  extAttr2Col.setVisible(true);  extAttr2TF.setVisible(true);
    }

    public void showDVD(ActionEvent e) {
        mediaTable.setItems(filterByTyp("DVD")); // nur DVDs

        extAttr1.setText("FSK");   extAttr1Col.setText("FSK");

        // FSK sichtbar, Seiten verstecken
        extAttr1.setVisible(true);  extAttr1Col.setVisible(true);  extAttr1TF.setVisible(true);
        extAttr2.setVisible(false); extAttr2Col.setVisible(false); extAttr2TF.setVisible(false);
    }

    /** Hilfs­methode: Filtert die globale Liste nach dem Typ-Text. */
    private ObservableList<AbstractMedium> filterByTyp(String typ) {
        return medienListe.filtered(m -> m.getTyp().equals(typ));
    }

    /* ===========================================================
       UPDATE-BUTTON – alle Änderungen speichern
       =========================================================== */
    @FXML
    public void onUpdateBTN(ActionEvent e) {

        // Medium wählen
        AbstractMedium m = mediaTable.getSelectionModel().getSelectedItem();
        if (m == null) { errorLabel.setText("Bitte zuerst ein Medium wählen!"); return; }

        /* -------- Grunddaten prüfen -------- */
        String titel =  safeText(titleTF);
        String autor =  safeText(authorTF);
        String jahrT =  safeText(yearTF);

        if (titel.isEmpty() || autor.isEmpty() || jahrT.isEmpty()) {
            errorLabel.setText("Titel, Autor und Jahr eingeben!");
            return;
        }

        int jahr;
        try { jahr = Integer.parseInt(jahrT); }
        catch (NumberFormatException ex) { errorLabel.setText("Jahr muss Zahl sein!"); return; }

        // Grunddaten setzen
        m.setTitel(titel);
        m.setAutor(autor);
        m.setErscheinungsjahr(jahr);

        /* -------- Extra-Attribute prüfen -------- */
        String v1 = safeText(extAttr1TF);
        String v2 = safeText(extAttr2TF);

        if (m instanceof Buch buch) {            // Buch-spezifisch
            if (v1.isEmpty() || v2.isEmpty()) { errorLabel.setText("ISBN UND Seiten eingeben!"); return; }
            try {
                int seiten = Integer.parseInt(v2);
                buch.setIsbn(v1);
                buch.setSeitenanzahl(seiten);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Seitenzahl muss Zahl sein!");
                return;
            }

        } else if (m instanceof DVD dvd) {       // DVD-spezifisch
            if (v1.isEmpty()) { errorLabel.setText("FSK eingeben!"); return; }
            try {
                int fsk = Integer.parseInt(v1);
                dvd.setFsk(fsk);
            } catch (NumberFormatException ex) {
                errorLabel.setText("FSK als Zahl eingeben!");
                return;
            }
        }

        /* -------- Anzeige auffrischen & Felder leeren -------- */
        mediaTable.refresh();          // Tabelle neu zeichnen
        errorLabel.setText("");        // Meldung löschen
        extAttr1TF.clear(); extAttr2TF.clear();
    }

    /** Holt Text aus TextField, liefert "" falls null. */
    private String safeText(TextField tf) {
        return tf.getText() != null ? tf.getText().trim() : "";
    }
}
