package com.example.mediatech.funktionalitaeten;

/**
 *  GemeinsameMethoden
 *  ==================
 *  Dieses Interface liefert zwei Hilfs­funktionen, die *jedes Medium*
 *  (Buch, DVD, …) anbieten soll:
 *
 *  1)  suchen(...)          →  Volltext-Suche in allen relevanten Feldern
 *  2)  getExtAttrVal(int)   →  Wert der Zusatz-Attribute zurückgeben
 *
 *  Warum Interface?
 *  ----------------
 *  •  Die Oberklasse AbstractMedium kennt nur Titel, Autor, Jahr.
 *     Extras (ISBN, Seiten, FSK …) sind von Typ zu Typ verschieden.
 *  •  Ein Interface zwingt jede Unterklasse, **ihre eigene** Logik dafür
 *     bereitzustellen → saubere Polymorphie, kein `instanceof` nötig.
 */
public interface GemeinsameMethoden {

    /**
     *  sucht in Titel, Autor, Jahr **und** Extra-Feldern.
     *
     *  @param suchbegriff  Alles in Kleinbuchstaben übergeben!
     *  @return             true, wenn der Begriff irgendwo vorkommt
     */
    boolean suchen(String suchbegriff);


    /**
     *  Liefert ein Extra-Attribut des Mediums.
     *
     *  @param attrNumber   1 = erstes Extra (ISBN / FSK)
     *                      2 = zweites Extra (Seitenzahl) oder "" wenn nicht vorhanden
     *  @return             String-Darstellung des Attributs
     */
    String getExtAttrVal(int attrNumber);
}
