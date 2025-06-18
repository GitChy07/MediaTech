package com.example.mediatech;

import com.example.mediatech.medium.AbstractMedium;
import com.example.mediatech.medium.Buch;
import com.example.mediatech.medium.DVD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class CSVImport {

    public static ObservableList<AbstractMedium> importFromCSV(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CSV-Datei importieren");

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV Dateien (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            return readCSV(file);
        } else {
            System.out.println("Kein Datei ausgewählt.");
            return FXCollections.observableArrayList();
        }
    }

    private static ObservableList<AbstractMedium> readCSV(File file) {
        ObservableList<AbstractMedium> liste = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Überspringe Header-Zeile
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 4) continue; // Sicherheitscheck

                String titel = parts[0].trim();
                String autor = parts[1].trim();
                int jahr = Integer.parseInt(parts[2].trim());
                String typ = parts[3].trim().toLowerCase();

                AbstractMedium medium = null;
                if (typ.contains("buch")) {
                    medium = new Buch(titel, autor, jahr);
                } else if (typ.contains("dvd")) {
                    medium = new DVD(titel, autor, jahr);
                }

                if (medium != null) {
                    liste.add(medium);
                }
            }

            System.out.println("CSV-Import erfolgreich. Anzahl importierter Medien: " + liste.size());

        } catch (IOException | NumberFormatException e) {
            System.err.println("Fehler beim Lesen der CSV-Datei: " + e.getMessage());
        }

        return liste;
    }
    //tEST
}
