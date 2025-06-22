package com.example.mediatech.controller;

import com.example.mediatech.funktionalitaeten.GemeinsameMethoden;
import com.example.mediatech.medium.AbstractMedium;
import com.example.mediatech.medium.Buch;
import com.example.mediatech.medium.DVD;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageMenuCon extends BaseController {

    @FXML public Label extAttr1, extAttr2;
    @FXML private TableColumn<AbstractMedium,String> extAttr1Col, extAttr2Col;
    @FXML public TextField extAttr1TF, extAttr2TF;

    @FXML public Label errorLabel;


    @Override
    public void initialize(URL u, ResourceBundle r) {super.initialize(u,r);// Basisspalten (Titel/Autor) sind vorhanden
        extAttr1Col.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(((GemeinsameMethoden) c.getValue()).getExtAttrVal(1)));
        extAttr2Col.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(((GemeinsameMethoden) c.getValue()).getExtAttrVal(2)));
    }

    /* -------- Buttons -------- */
    public void showBuch(ActionEvent e) {
        mediaTable.setItems(filterByTyp("Buch"));
        extAttr1.setText("ISBN");  extAttr1Col.setText("ISBN");
        extAttr2.setText("Seiten"); extAttr2Col.setText("Seiten");
        extAttr2.setVisible(true);  extAttr2Col.setVisible(true); extAttr2TF.setVisible(true);
    }

    public void showDVD(ActionEvent e) {
        mediaTable.setItems(filterByTyp("DVD"));
        extAttr1.setText("FSK");    extAttr1Col.setText("FSK");
        extAttr2.setVisible(false); extAttr2Col.setVisible(false); extAttr2TF.setVisible(false);
    }

    private ObservableList<AbstractMedium> filterByTyp(String typ) {
        return medienListe.filtered(m ->
                ((GemeinsameMethoden) m).getTyp().equals(typ));
    }

    @FXML
    public void onUpdateBTN(ActionEvent e) {

        // 1) aktuell markierte Zeile holen
        AbstractMedium m = mediaTable.getSelectionModel().getSelectedItem();
        if (m == null) {
            errorLabel.setText("Bitte zuerst ein Medium wählen!");
            return;
        }

        // 2) Werte aus den Textfeldern einlesen
        String v1 = extAttr1TF.getText().trim();   // ISBN oder FSK
        String v2 = extAttr2TF.getText().trim();   // Seitenzahl (nur Buch)

        // 3) je nach Medium-Typ validieren und setzen
        if (m instanceof Buch buch) {                       // Java 17 pattern matching
            if (v1.isEmpty() || v2.isEmpty()) {
                errorLabel.setText("ISBN und Seitenzahl eingeben!");
                return;
            }
            try {
                int seiten = Integer.parseInt(v2);          // Seitenzahl muss Zahl sein
                buch.setIsbn(v1);
                buch.setSeitenanzahl(seiten);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Seitenzahl muss eine Zahl sein!");
                return;
            }

        } else if (m instanceof DVD dvd) {
            if (v1.isEmpty()) {
                errorLabel.setText("FSK eingeben!");
                return;
            }
            try {
                int fsk = Integer.parseInt(v1);          // FSK muss Zahl sein
                dvd.setFsk(fsk);
            } catch (NumberFormatException ex) {
                errorLabel.setText("FSK als Zahl eingeben!");
                return;
            }

        }

        // 4) Tabelle neu zeichnen
        mediaTable.refresh();

        // 5) Eingabefelder & Fehlermeldung zurücksetzen
        extAttr1TF.clear();
        extAttr2TF.clear();
        errorLabel.setText("");
    }


}