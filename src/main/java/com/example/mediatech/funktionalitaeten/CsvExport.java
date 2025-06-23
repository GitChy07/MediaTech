package com.example.mediatech.funktionalitaeten;

import com.example.mediatech.medium.AbstractMedium;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  CsvExport
 *  =========
 *  Speichert eine Liste von Medien (Buch oder DVD) in eine CSV-Datei.
 *
 *  - Spalten 0-3 :  Titel | Autor | Jahr | Typ
 *  - Spalte 4    :  Extra-Attribut 1  (ISBN bzw. FSK)
 *  - Spalte 5    :  Extra-Attribut 2  (Seitenzahl oder leer)
 *
 *  Ablauf:
 *    1.  exportToCSV()   →  Dateidialog »Speichern unter« öffnen
 *    2.  saveToFile()    →  ausgewählte Datei Zeile für Zeile beschreiben
 */
public class CsvExport {

    /* ==============================================================
       1) Öffnet den FileChooser und ruft anschließend saveToFile()
       ============================================================== */
    public static void exportToCSV(Stage stage,
                                   ObservableList<AbstractMedium> listToSave) {

        // --- Dialogfenster vorbereiten -------------------------------------
        FileChooser fc = new FileChooser();
        fc.setTitle("CSV speichern");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Dateien (*.csv)", "*.csv"));

        // --- Benutzer wählt Speicherort ------------------------------------
        File file = fc.showSaveDialog(stage);

        // Wenn »Abbrechen« gedrückt wurde → nichts tun
        if (file == null) return;

        // --- Datei wirklich schreiben --------------------------------------
        saveToFile(file, listToSave);
    }

    /* ==============================================================
       2) Schreibt Kopfzeile + Datenzeilen in die übergebene Datei
       ============================================================== */
    private static void saveToFile(File file,
                                   ObservableList<AbstractMedium> data) {

        try (FileWriter w = new FileWriter(file)) {

            /* ---- Kopfzeile ------------------------------------------------ */
            w.append("Titel,Autor,Erscheinungsjahr,Typ,Attr1,Attr2\n");

            /* ---- Jede Zeile der Tabelle ---------------------------------- */
            for (AbstractMedium m : data) {

                // Extra-Spalten via Interface abfragen (ISBN/FSK, Seiten)
                String attr1 = ((GemeinsameMethoden) m).getExtAttrVal(1);
                String attr2 = ((GemeinsameMethoden) m).getExtAttrVal(2);

                // Zeile zusammenbauen
                w.append(m.getTitel()).append(",")
                        .append(m.getAutor()).append(",")
                        .append(String.valueOf(m.getErscheinungsjahr())).append(",")
                        .append(m.getClass().getSimpleName().toLowerCase()).append(",")
                        .append(attr1).append(",")
                        .append(attr2).append("\n");
            }

            System.out.println("CSV-Export: Datei erfolgreich geschrieben.");

        } catch (IOException ex) {
            System.out.println("CSV-Export-Fehler: " + ex.getMessage());
        }
    }
}
