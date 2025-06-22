package com.example.mediatech.controller;

import com.example.mediatech.funktionalitaeten.GemeinsameMethoden;
import com.example.mediatech.medium.AbstractMedium;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class ManageMenuCon extends BaseController {

    @FXML public Label extAttr1, extAttr2;
    @FXML public TableColumn extAttr1Col, extAttr2Col;
    @FXML public TextField extAttr1TF, extAttr2TF;

    @FXML public Label errorLabel;

    @FXML
    private ObservableList<AbstractMedium> filterMatchingTyp(String input) {
        ObservableList<AbstractMedium> matching = FXCollections.observableArrayList();
        // Durchlaufe alle Medien und prüfe, ob der Suchbegriff in Titel, Autor, Jahr oder Typ enthalten ist
        for (AbstractMedium medium : medienListe) {
            String typ = ((GemeinsameMethoden) medium).getTyp();
            if (input.equals(typ)) {
                matching.add(medium); // Füge das Medium zur Liste hinzu, wenn es passt
            }
        }
        return matching; // Rückgabe der gefilterten Liste
    }

    public void showBuch(ActionEvent actionEvent) {
        // Filtern nach typ Buch
        ObservableList<AbstractMedium> matching = filterMatchingTyp("Buch");
        mediaTable.setItems(matching);

    }
    public void showDVD(ActionEvent actionEvent) {
        // Filtern nach typ DVD
        ObservableList<AbstractMedium> matching = filterMatchingTyp("DVD");
        mediaTable.setItems(matching);

    }

    public void onUpdateBTN(ActionEvent actionEvent) {

    }

}