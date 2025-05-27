package com.example.mediatech.medium;

public class Buch extends AbstractMedium {
    private String isbn;
    private int seitenanzahl;

    public Buch(String titel, String autor, int erscheinungsjahr) {
        super(titel, autor, erscheinungsjahr);
        this.isbn = isbn;
        this.seitenanzahl = seitenanzahl;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getSeitenanzahl() {
        return seitenanzahl;
    }


}