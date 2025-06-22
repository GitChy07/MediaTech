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

public class ManageMenuCon extends BaseController {

    /* ---------- Grunddaten ---------- */
    @FXML private TextField titleTF, authorTF, yearTF;

    /* ---------- Extra-Attribute ---------- */
    @FXML private Label     extAttr1,  extAttr2;
    @FXML private TextField extAttr1TF, extAttr2TF;
    @FXML private TableColumn<AbstractMedium,String> extAttr1Col, extAttr2Col;

    /* ---------- Sonstiges ---------- */
    @FXML private Label errorLabel;

    /* ---------- Initialisierung ---------- */
    @Override
    public void initialize(URL u, ResourceBundle r) {
        super.initialize(u, r);   // Basis-Spalten (Titel/Autor/Jahr) kommen aus BaseController

        /* Cell-Factories für die beiden Extra-Spalten */
        extAttr1Col.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(((GemeinsameMethoden) c.getValue()).getExtAttrVal(1)));
        extAttr2Col.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(((GemeinsameMethoden) c.getValue()).getExtAttrVal(2)));

        /* Extras zu Beginn ausblenden */
        hideExtras();

        /* Beim Klick in die Tabelle -> Felder füllen */
        mediaTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> fillFields(newVal));
    }

    /** Macht beide Extra-Spalten, Labels und Textfelder unsichtbar. */
    private void hideExtras() {
        extAttr1.setVisible(false);  extAttr1Col.setVisible(false);  extAttr1TF.setVisible(false);
        extAttr2.setVisible(false);  extAttr2Col.setVisible(false);  extAttr2TF.setVisible(false);
    }

    /** Überträgt Daten des gewählten Mediums in die Textfelder. */
    private void fillFields(AbstractMedium m) {
        if (m == null) return;

        titleTF .setText(m.getTitel());
        authorTF.setText(m.getAutor());
        yearTF  .setText(String.valueOf(m.getErscheinungsjahr()));

        extAttr1TF.setText(((GemeinsameMethoden) m).getExtAttrVal(1));
        extAttr2TF.setText(((GemeinsameMethoden) m).getExtAttrVal(2));
    }

    /* ---------- Typ-Buttons ---------- */
    public void showBuch(ActionEvent e) {
        mediaTable.setItems(filterByTyp("Buch"));

        extAttr1.setText("ISBN");   extAttr1Col.setText("ISBN");
        extAttr2.setText("Seiten"); extAttr2Col.setText("Seiten");

        extAttr1.setVisible(true);  extAttr1Col.setVisible(true);  extAttr1TF.setVisible(true);
        extAttr2.setVisible(true);  extAttr2Col.setVisible(true);  extAttr2TF.setVisible(true);
    }

    public void showDVD(ActionEvent e) {
        mediaTable.setItems(filterByTyp("DVD"));

        extAttr1.setText("FSK");    extAttr1Col.setText("FSK");

        extAttr1.setVisible(true);  extAttr1Col.setVisible(true);  extAttr1TF.setVisible(true);
        extAttr2.setVisible(false); extAttr2Col.setVisible(false); extAttr2TF.setVisible(false);
    }

    private ObservableList<AbstractMedium> filterByTyp(String typ) {
        return medienListe.filtered(m ->
                ((GemeinsameMethoden) m).getTyp().equals(typ));
    }

    /* ---------- UPDATE-Button ---------- */
    @FXML
    public void onUpdateBTN(ActionEvent e) {

        AbstractMedium m = mediaTable.getSelectionModel().getSelectedItem();
        if (m == null) { errorLabel.setText("Bitte zuerst ein Medium wählen!"); return; }

        /* Grunddaten lesen & validieren */
        String titel  = titleTF.getText()  != null ? titleTF.getText().trim()  : "";
        String autor  = authorTF.getText() != null ? authorTF.getText().trim() : "";
        String jahrTx = yearTF.getText()   != null ? yearTF.getText().trim()   : "";

        if (titel.isEmpty() || autor.isEmpty() || jahrTx.isEmpty()) {
            errorLabel.setText("Titel, Autor und Jahr eingeben!");
            return;
        }

        int jahr;
        try { jahr = Integer.parseInt(jahrTx); }
        catch (NumberFormatException ex) { errorLabel.setText("Jahr muss Zahl sein!"); return; }

        m.setTitel(titel);
        m.setAutor(autor);
        m.setErscheinungsjahr(jahr);

        /* Extra-Attribute bearbeiten */
        String v1 = extAttr1TF.getText() != null ? extAttr1TF.getText().trim() : "";
        String v2 = extAttr2TF.getText() != null ? extAttr2TF.getText().trim() : "";

        if (m instanceof Buch buch) {
            if (v1.isEmpty() || v2.isEmpty()) {
                errorLabel.setText("ISBN und Seitenzahl eingeben!");
                return;
            }
            try {
                int seiten = Integer.parseInt(v2);
                buch.setIsbn(v1);
                buch.setSeitenanzahl(seiten);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Seitenzahl muss Zahl sein!");
                return;
            }

        } else if (m instanceof DVD dvd) {
            if (v1.isEmpty()) { errorLabel.setText("FSK eingeben!"); return; }
            try {
                int fsk = Integer.parseInt(v1);
                dvd.setFsk(fsk);
            } catch (NumberFormatException ex) {
                errorLabel.setText("FSK als Zahl eingeben!");
                return;
            }
        }

        /* Anzeige aktualisieren & Felder leeren */
        mediaTable.refresh();
        errorLabel.setText("");
        extAttr1TF.clear(); extAttr2TF.clear();
    }
}
