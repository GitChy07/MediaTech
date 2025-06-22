package com.example.mediatech.funktionalitaeten;

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
        ObservableList<AbstractMedium> list = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }          // Header überspringen

                String[] p = line.split(",", -1);                // -1  →  leere Felder bleiben leer
                if (p.length < 4) continue;                      // Minimalprüfung

                String titel = p[0].trim();
                String autor = p[1].trim();
                int jahr = Integer.parseInt(p[2].trim());
                String typ = p[3].trim().toLowerCase();

                String attr1 = p.length > 4 ? p[4].trim() : "";  // ISBN oder FSK
                String attr2 = p.length > 5 ? p[5].trim() : "";  // Seitenzahl (nur Buch)

                switch (typ) {
                    case "buch" -> {
                        Buch b = new Buch(titel, autor, jahr);
                        b.setIsbn(attr1);
                        if (!attr2.isBlank()) {
                            try {
                                b.setSeitenanzahl(Integer.parseInt(attr2));
                            } catch (NumberFormatException ignored) {
                            }
                        }
                        list.add(b);
                    }
                    case "dvd" -> {
                        DVD d = new DVD(titel, autor, jahr);
                        if (!attr1.isBlank()) {
                            try {
                                d.setFsk(Integer.parseInt(attr1));
                            } catch (NumberFormatException ignored) {
                            }
                        }
                        list.add(d);
                    }
                }
            }
            System.out.println("CSV-Import: " + list.size() + " Medien geladen.");

        } catch (IOException | NumberFormatException ex) {
            System.err.println("CSV-Fehler: " + ex.getMessage());
        }
        return list;
    }
}
