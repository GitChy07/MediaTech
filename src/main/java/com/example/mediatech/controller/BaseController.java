package com.example.mediatech.controller;

import com.example.mediatech.Starter;
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

import javafx.scene.control.Alert.AlertType;

public abstract class BaseController implements Initializable {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    /* ---------------- gemeinsame UI‑Elemente -------------- */
    @FXML
    protected TableView<AbstractMedium> mediaTable;
    @FXML
    protected TableColumn<AbstractMedium, String> titleColumn;
    @FXML
    protected TableColumn<AbstractMedium, String> authorColumn;
    @FXML
    protected TableColumn<AbstractMedium, Integer> yearColumn;
    @FXML
    protected TableColumn<AbstractMedium, String> typColumn;

    /* Gemeinsame Datenliste (kommt aus Starter) */
    protected final ObservableList<AbstractMedium> medienListe = Starter.medienListe;

    /* ---------------- Basis‑Initialisierung -------------- */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (mediaTable != null) {
            mediaTable.setItems(medienListe);

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
                typColumn.setCellValueFactory(c ->
                        new ReadOnlyStringWrapper(
                                c.getValue() instanceof Buch ? "Buch" :
                                        c.getValue() instanceof DVD ? "DVD" : "?"));
        }
    }


    /* ---------------- Navigation -------------- */
    @FXML
    protected void showAddMedia(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        BaseController.class.getResource("/com/example/mediatech/AddMenuUI.fxml")));
        Scene scene = new Scene(root, 700, 520);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        BaseController.class.getResource("/com/example/mediatech/style.css")).toExternalForm());
        ((Stage) ((Node) e.getSource()).getScene().getWindow()).setScene(scene);
    }

    @FXML
    protected void showSearchMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        BaseController.class.getResource("/com/example/mediatech/SearchUI.fxml")));
        Scene scene = new Scene(root, 700, 520);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        BaseController.class.getResource("/com/example/mediatech/style.css")).toExternalForm());
        ((Stage) ((Node) e.getSource()).getScene().getWindow()).setScene(scene);
    }

    @FXML
    protected void showManageMenu(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        BaseController.class.getResource("/com/example/mediatech/ManageUI.fxml")));
        Scene scene = new Scene(root, 700, 520);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        BaseController.class.getResource("/com/example/mediatech/style.css")).toExternalForm());
        ((Stage) ((Node) e.getSource()).getScene().getWindow()).setScene(scene);
    }

}
