package com.example.mediatech.funktionalitaeten;

import com.example.mediatech.medium.AbstractMedium;
import com.example.mediatech.medium.Buch;
import com.example.mediatech.medium.DVD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 *  CSV-Import
 *  ==========
 *  Liest eine CSV-Datei ein und liefert eine Liste von Medien (Buch oder DVD).
 *
 *  Erwartetes Spalten-Layout:
 *  -------------------------------------------
 *  0  Titel
 *  1  Autor
 *  2  Erscheinungsjahr  (Ganzzahl)
 *  3  Typ               (buch | dvd)
 *  4  Extra1            (ISBN bzw. FSK)   – optional
 *  5  Extra2            (Seitenzahl)      – nur Buch, optional
 *  -------------------------------------------
 *
 *  Leere Felder sind erlaubt; sie werden einfach ignoriert.
 */
public class CSVImport {

    /* =======================================================
       1) Button-Methode – öffnet Datei­dialog und ruft readCSV()
       ======================================================= */
    public static ObservableList<AbstractMedium> importFromCSV(Stage stage) {

        // --- Datei auswählen -------------------------------------------------
        FileChooser fc = new FileChooser();
        fc.setTitle("CSV-Datei importieren");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Dateien (*.csv)", "*.csv"));

        File file = fc.showOpenDialog(stage);

        // --- Falls Nutzer*in abbricht → leere Liste zurückgeben -------------
        if (file == null) {
            System.out.println("Keine Datei ausgewählt.");
            return FXCollections.observableArrayList();
        }

        // --- Datei verarbeiten ----------------------------------------------
        return readCSV(file);
    }

    /* =======================================================
       2) Eigentliche Lese-Logik
       ======================================================= */
    private static ObservableList<AbstractMedium> readCSV(File file) {

        ObservableList<AbstractMedium> list = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            boolean first = true;          // erste Zeile = Header → überspringen

            while ((line = br.readLine()) != null) {

                if (first) { first = false; continue; }   // Header skip

                // --- Zeile in Spalten zerlegen (auch leere Spalten behalten)
                String[] p = line.split(",", -1);
                if (p.length < 4) continue;               // zu wenig Spalten → skip

                /* Grunddaten immer vorhanden */
                String titel = p[0].trim();
                String autor = p[1].trim();
                int    jahr  = Integer.parseInt(p[2].trim());
                String typ   = p[3].trim().toLowerCase();

                /* Optionale Extra-Spalten – können leer sein */
                String attr1 = p.length > 4 ? p[4].trim() : "";  // ISBN oder FSK
                String attr2 = p.length > 5 ? p[5].trim() : "";  // Seitenzahl

                // --- Medientyp auswerten ------------------------------------
                switch (typ) {

                    case "buch" -> {
                        Buch b = new Buch(titel, autor, jahr);

                        /* ISBN direkt setzen (kann auch leer sein) */
                        b.setIsbn(attr1);

                        /* Seitenzahl ist optional, muss aber Zahl sein */
                        if (!attr2.isBlank()) {
                            try { b.setSeitenanzahl(Integer.parseInt(attr2)); }
                            catch (NumberFormatException ignored) { /* einfach weglassen */ }
                        }
                        list.add(b);
                    }

                    case "dvd" -> {
                        DVD d = new DVD(titel, autor, jahr);

                        /* FSK ist optional, muss Zahl sein */
                        if (!attr1.isBlank()) {
                            try { d.setFsk(Integer.parseInt(attr1)); }
                            catch (NumberFormatException ignored) { /* kein FSK setzen */ }
                        }
                        list.add(d);
                    }

                    // andere Typen werden (noch) ignoriert
                }
            }

            System.out.println("CSV-Import: " + list.size() + " Medien geladen.");

        } catch (IOException | NumberFormatException ex) {
            System.out.println("CSV-Fehler: " + ex.getMessage());
        }

        return list;
    }
}
