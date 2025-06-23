package com.example.mediatech.controller;

import com.example.mediatech.Starter;
import com.example.mediatech.medium.*;          // Buch, DVD, AbstractMedium …

// JavaFX-Imports
import javafx.beans.property.*;                // ReadOnly…Wrapper für Spalten
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *  Gemeinsame Basisklasse für ALLE Screens (Add / Search / Manage).
 *  – Stellt eine Tabelle mit Grundspalten bereit
 *  – Verknüpft die Tabelle mit der globalen ObservableList
 *  – Enthält 3 Hilfsmethoden zum Szenen-Wechsel (Navigation)
 *  Vorteil: Jede Unterklasse erbt diese Funktionen
 *           und muss nur noch ihr Spezial-Zeug programmieren.
 */
public abstract class BaseController implements Initializable {

    /* ==========================================================
       1) Member-Variablen für GUI (werden via FXML injiziert)
       ========================================================== */

    // ---- TableView und Grundspalten ----
    @FXML protected TableView<AbstractMedium> mediaTable;
    @FXML protected TableColumn<AbstractMedium,String>  titleColumn;
    @FXML protected TableColumn<AbstractMedium,String>  authorColumn;
    @FXML protected TableColumn<AbstractMedium,Integer> yearColumn;
    @FXML protected TableColumn<AbstractMedium,String>  typColumn;

    // ---- Gemeinsame Datenliste (kommt aus Starter) ----
    protected final ObservableList<AbstractMedium> medienListe = Starter.medienListe;

    /* ==========================================================
       2) Tabelle konfigurieren (wird automatisch beim Laden aufgerufen)
       ========================================================== */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // FXML kann „leer“ sein, wenn ein Screen gar keine Tabelle hat.
        if (mediaTable == null) return;

        // Tabelle mit globaler Liste verbinden
        mediaTable.setItems(medienListe);

        // Jede Spalte bekommt ihren Wert-Lieferanten (CellValueFactory)

        if (titleColumn != null)
            titleColumn.setCellValueFactory(c ->
                    new ReadOnlyStringWrapper(c.getValue().getTitel()));

        if (authorColumn != null)
            authorColumn.setCellValueFactory(c ->
                    new ReadOnlyStringWrapper(c.getValue().getAutor()));

        if (yearColumn != null)
            yearColumn.setCellValueFactory(c ->
                    new ReadOnlyObjectWrapper<>(c.getValue().getErscheinungsjahr()));

        if (typColumn != null)
            typColumn.setCellValueFactory(c -> new ReadOnlyStringWrapper(
                    c.getValue() instanceof Buch ? "Buch" :
                            c.getValue() instanceof DVD ? "DVD" : "?"));
    }

    /* ==========================================================
       3) Navigation – FXML-Datei laden und Szene austauschen
          (wird von Buttons in den Unter-Controllern aufgerufen)
       ========================================================== */

    /** Springt zum „Add Media“-Screen. */
    @FXML
    protected void showAddMedia(ActionEvent e) throws IOException {
        switchScene((Node) e.getSource(), "/com/example/mediatech/AddMenuUI.fxml");
    }

    /** Springt zum „Search“-Screen. */
    @FXML
    protected void showSearchMenu(ActionEvent e) throws IOException {
        switchScene((Node) e.getSource(), "/com/example/mediatech/SearchUI.fxml");
    }

    /** Springt zum „Manage“-Screen. */
    @FXML
    protected void showManageMenu(ActionEvent e) throws IOException {
        switchScene((Node) e.getSource(), "/com/example/mediatech/ManageUI.fxml");
    }

    /* ----------------------------------------------------------
       private Hilfsmethode, damit der Lade-Code nur EIN Mal steht
       ---------------------------------------------------------- */
    private void switchScene(Node sender, String fxmlPath) throws IOException {

        // 1. FXML laden
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(BaseController.class.getResource(fxmlPath)));

        // 2. Neue Szene bauen (Breite/Höhe konstant 700 × 520)
        Scene scene = new Scene(root, 700, 520);

        // 3. CSS anhängen
        scene.getStylesheets().add(
                Objects.requireNonNull(
                                BaseController.class.getResource("/com/example/mediatech/style.css"))
                        .toExternalForm());

        // 4. Aktuelle Stage (= Fenster) ermitteln und Szene setzen
        ((Stage) sender.getScene().getWindow()).setScene(scene);
    }
}
