package com.example.mediatech;

import com.example.mediatech.medium.AbstractMedium;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {

    public static final ObservableList<AbstractMedium> medienListe = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("StartMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 520);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("MediaTech");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
