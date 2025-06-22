package com.example.mediatech.controller;

import com.example.mediatech.medium.AbstractMedium;
import com.example.mediatech.medium.Buch;
import com.example.mediatech.medium.DVD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddMediaCon extends BaseController {
    
    @FXML
    private TextField TitleTF, AuthorTF, YearTF;
    @FXML
    private RadioButton buchRB, dvdRB;
    @FXML
    private Label errorLabel;

    @FXML
    protected void toggleA() {
        if (dvdRB.isSelected()) {
            dvdRB.setSelected(false);
            buchRB.setSelected(true);
        }
    }
    @FXML
    protected void toggleB() {
        if (buchRB.isSelected()) {
            buchRB.setSelected(false);
            dvdRB.setSelected(true);
        }
    }

    @FXML
    protected void onAddButtonClick() {
        String titel = TitleTF.getText();
        String autor = AuthorTF.getText();
        int jahr;

        if (titel.isEmpty() || autor.isEmpty() || YearTF.getText().isEmpty()) {
            if (!YearTF.getText().isEmpty()) {
                try {
                    Integer.parseInt(YearTF.getText());
                } catch (NumberFormatException e) {
                    System.out.println("Ungültiges Jahr");
                    errorLabel.setText("Ungültiges Jahr");
                    return;
                }
            }
        }

        if (titel.isEmpty() || autor.isEmpty()) {
            System.out.println("Bitte alle Felder ausfüllen");
            errorLabel.setText("Bitte alle Felder ausfüllen");
            return;
        }

        try {
            Integer.parseInt(YearTF.getText());
        } catch (NumberFormatException e) {
            System.out.println("Ungültiges Jahr");
            errorLabel.setText("Ungültiges Jahr");
            return;
        }

        jahr = Integer.parseInt(YearTF.getText());

        if (jahr == 0) {
            System.out.println("Ungültiges Jahr");
            errorLabel.setText("Ungültiges Jahr");
            return;
        }

        AbstractMedium medium = null;

        if (buchRB.isSelected()) {
            medium = new Buch(titel, autor, jahr);
        } else if (dvdRB.isSelected()) {
            medium = new DVD(titel, autor, jahr);
        } else {
            System.out.println("Medien Typ auswählen");
            errorLabel.setText("Medien Typ auswählen");
            return;
        }

        medienListe.add(medium);
        TitleTF.clear();
        AuthorTF.clear();
        YearTF.clear();
        errorLabel.setText("");

    }
    @FXML
    protected void onDeleteButtonClick() {
        AbstractMedium ausgewaehltesMedium = mediaTable.getSelectionModel().getSelectedItem();

        if (ausgewaehltesMedium != null) {
            Alert bestaetigung = new Alert(Alert.AlertType.CONFIRMATION);
            bestaetigung.setTitle("Löschen bestätigen");
            bestaetigung.setHeaderText("Medium wirklich löschen?");
            bestaetigung.setContentText("Möchtest du das Medium \"" + ausgewaehltesMedium.getTitel() + "\" wirklich entfernen?");

            // Warten auf die Antwort des Benutzers
            bestaetigung.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            medienListe.remove(ausgewaehltesMedium);
                        }
                    }
            );
        } else {
            Alert warnung = new Alert(Alert.AlertType.WARNING);
            warnung.setTitle("Keine Auswahl");
            warnung.setHeaderText("Kein Medium ausgewählt");
            warnung.setContentText("Bitte wähle zuerst ein Medium in der Tabelle aus.");
            warnung.show();

        }
    }
    
    

}
