package com.example.mediatech;

import com.example.mediatech.medium.AbstractMedium;
import com.example.mediatech.medium.Buch;
import com.example.mediatech.medium.DVD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestDataGenerator {

    // Methode zum Erzeugen der Beispiel-Daten
    public static void generateSampleData() {

        // Beispielhafte Bücher
        Buch buch1 = new Buch("Der Herr der Ringe", "J.R.R. Tolkien", 1954);
        buch1.setIsbn("978-0261103573");
        buch1.setSeitenanzahl(1216);

        Buch buch2 = new Buch("1984", "George Orwell", 1949);
        buch2.setIsbn("978-0451524935");
        buch2.setSeitenanzahl(328);

        // Beispielhafte DVDs
        DVD dvd1 = new DVD("Inception", "Christopher Nolan", 2010);
        dvd1.setFsk("12");

        DVD dvd2 = new DVD("The Dark Knight", "Christopher Nolan", 2008);
        dvd2.setFsk("12");

        // Füge die erstellten Objekte zur Liste hinzu
        Starter.medienListe.add(buch1);
        Starter.medienListe.add(buch2);
        Starter.medienListe.add(dvd1);
        Starter.medienListe.add(dvd2);

    }

}
