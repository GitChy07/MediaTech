package com.example.mediatech;

import com.example.mediatech.medium.*; // Importiere alle Klassen aus dem 'medium' Paket

// JavaFX Imports
import javafx.beans.property.*;  // Für ReadOnlyObjectWrapper und ReadOnlyStringWrapper
import javafx.event.ActionEvent;
import javafx.fxml.*;  // Für FXMLLoader, FXML und Initializable
import javafx.scene.*;  // Für Node, Parent und Scene
import javafx.stage.*;  // Für Stage
import javafx.scene.control.*;  // Für alle JavaFX UI-Komponenten wie Button, Alert, etc.
import javafx.collections.*;  // Für FXCollections und ObservableList

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.Alert.AlertType;

/* ------------------------------------------------------------------------------------------------------------- */

public class Controller implements Initializable {

    public Button addMediaButton;
    public Button searchButton;
    public Button manageButton;

    public TableView<AbstractMedium> mediaTable;
    // public static final ObservableList<AbstractMedium> medienListe = FXCollections.observableArrayList();
    public static final ObservableList<AbstractMedium> medienListe = Starter.medienListe;

    public TableColumn<AbstractMedium, String> titleColumn;
    public TableColumn<AbstractMedium, String> authorColumn;
    public TableColumn<AbstractMedium, Integer> yearColumn;
    public TableColumn<AbstractMedium, String> typColumn;

    public TextField YearTF;
    public TextField AuthorTF;
    public TextField TitleTF;
    public RadioButton buchRB;
    public RadioButton dvdRB;

    public Button addButton;
    public Button deleteButton;

    public Label errorLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextField SearchTF;
    public Button SearchBTN;
    public Button CSVExportBTN;

    /* ------------------------------------------------------------------------------------------------------------- */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaTable.setItems(medienListe);

        titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitel()));
        authorColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAutor()));
        yearColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getErscheinungsjahr()));
        typColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Buch) {
                return new ReadOnlyStringWrapper("Buch");
            } else if (cellData.getValue() instanceof DVD) {
                return new ReadOnlyStringWrapper("DVD");
            } else {
                return new ReadOnlyStringWrapper("Unbekannt");
            }
        });

        mediaTable.setItems(medienListe);

    }

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
            Alert bestaetigung = new Alert(AlertType.CONFIRMATION);
            bestaetigung.setTitle("Löschen bestätigen");
            bestaetigung.setHeaderText("Medium wirklich löschen?");
            bestaetigung.setContentText("Möchtest du das Medium \"" + ausgewaehltesMedium.getTitel() + "\" wirklich entfernen?");

            // Warten auf die Antwort des Benutzers
            bestaetigung.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    medienListe.remove(ausgewaehltesMedium);
                }
            });
        } else {
            Alert warnung = new Alert(AlertType.WARNING);
            warnung.setTitle("Keine Auswahl");
            warnung.setHeaderText("Kein Medium ausgewählt");
            warnung.setContentText("Bitte wähle zuerst ein Medium in der Tabelle aus.");
            warnung.show();
        }
    }

    @FXML
    protected void showAddMedia(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddMenuUI.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 520);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    protected void showSearchMenu(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SearchUI.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 520);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void showManageMenu(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ManageUI.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 520);
        stage.setScene(scene);
        stage.show();
    }

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
                if (((Suchfunktion) medium).suchen(input.toLowerCase())) {
                    matching.add(medium); // Füge das Medium zur Liste hinzu, wenn es passt
                }
            }
        return matching; // Rückgabe der gefilterten Liste
    }

}