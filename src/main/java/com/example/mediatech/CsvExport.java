package com.example.mediatech;

import com.example.mediatech.medium.AbstractMedium;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.ObservableList;

public class CsvExport {

    // Methode zum Öffnen des Dialogs und Speichern der CSV-Datei
    public static void exportToCSV(Stage stage, ObservableList<AbstractMedium> matching) {
        // Erstelle einen FileChooser für die Dateiauswahl
        FileChooser fileChooser = new FileChooser();

        // Filter für CSV-Dateien
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV Dateien (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);

        // Öffne das "Speichern unter"-Fenster
        File file = fileChooser.showSaveDialog(stage);

        // Wenn eine Datei ausgewählt wurde, speichere sie
        if (file != null) {
            saveToFile(file, matching); // Übergebe die gefilterte Liste an die Methode
            printToConsole(matching);   // Gebe die gefilterte Liste im Terminal aus
        }
    }

    // Methode zum Speichern der Daten in der CSV-Datei
    private static void saveToFile(File file, ObservableList<AbstractMedium> matching) {
        try (FileWriter writer = new FileWriter(file)) {
            // Schreibe den Header in die CSV-Datei
            writer.append("Titel,Autor,Erscheinungsjahr,Typ\n");

            // Schreibe jedes Medium aus der gefilterten Liste in die CSV-Datei
            for (AbstractMedium medium : matching) {
                writer.append(medium.getTitel())
                        .append(",")
                        .append(medium.getAutor())
                        .append(",")
                        .append(String.valueOf(medium.getErscheinungsjahr()))
                        .append(",")
                        .append(medium.getClass().getSimpleName())
                        .append("\n");
            }

            System.out.println("CSV-Datei wurde erfolgreich exportiert!");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der CSV-Datei: " + e.getMessage());
        }
    }

    // Methode zum Anzeigen der Tabelle im Terminal
    private static void printToConsole(ObservableList<AbstractMedium> matching) {
        // Feste Breite für jede Spalte
        int titelBreite = 30;
        int autorBreite = 30;
        int jahrBreite = 20;
        int typBreite = 10;

        // Header der Tabelle
        System.out.println("Medienliste (Tabellarisch):");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-" + titelBreite + "s %-" + autorBreite + "s %-" + jahrBreite + "s %-" + typBreite + "s%n", "Titel", "Autor", "Erscheinungsjahr", "Typ");
        System.out.println("---------------------------------------------------------------");

        // Ausgabe der gefilterten Medien in der Tabelle
        for (AbstractMedium medium : matching) {
            System.out.printf("%-" + titelBreite + "s %-" + autorBreite + "s %-" + jahrBreite + "d %-" + typBreite + "s%n",
                    medium.getTitel(),
                    medium.getAutor(),
                    medium.getErscheinungsjahr(),
                    medium.getClass().getSimpleName());
        }
        System.out.println("---------------------------------------------------------------");
    }
}
