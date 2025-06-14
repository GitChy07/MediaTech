package com.example.mediatech.medium;

public class Buch extends AbstractMedium implements Suchfunktion {

    private String isbn;
    private int seitenanzahl;

    public void setIsbn(String isbn) {this.isbn = isbn;}
    public void setSeitenanzahl(int seitenanzahl) {this.seitenanzahl = seitenanzahl;}

    public Buch(String titel, String autor, int erscheinungsjahr) {
        super(titel, autor, erscheinungsjahr);
    }

    public String getIsbn() {
        return isbn;
    }

    public int getSeitenanzahl() {
        return seitenanzahl;
    }

    @Override
    public boolean suchen(String suchbegriff) {
        // Sucht nach dem Titel, Autor, Erscheinungsjahr und ISBN
        return  getTitel().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                getAutor().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                String.valueOf(getErscheinungsjahr()).contains(suchbegriff) ||
                (isbn != null && isbn.contains(suchbegriff));  // Suche nach ISBN
    }

}