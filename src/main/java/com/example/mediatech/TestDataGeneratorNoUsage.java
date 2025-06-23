package com.example.mediatech;

import com.example.mediatech.medium.Buch;
import com.example.mediatech.medium.DVD;

public class TestDataGeneratorNoUsage {

    public static void generateSampleData() {

        Buch buch1 = new Buch("Der Herr der Ringe", "J.R.R. Tolkien", 1954);
        buch1.setIsbn("978-0261103573");
        buch1.setSeitenanzahl(1216);

        Buch buch2 = new Buch("1984", "George Orwell", 1949);
        buch2.setIsbn("978-0451524935");
        buch2.setSeitenanzahl(328);


        DVD dvd1 = new DVD("Inception", "Christopher Nolan", 2010);
        dvd1.setFsk(12);

        DVD dvd2 = new DVD("The Dark Knight", "Christopher Nolan", 2008);
        dvd2.setFsk(12);

        Starter.medienListe.add(buch1);
        Starter.medienListe.add(buch2);
        Starter.medienListe.add(dvd1);
        Starter.medienListe.add(dvd2);

    }

}
