package com.example.mediatech.medium;

import com.example.mediatech.funktionalitaeten.GemeinsameMethoden;

public class Buch extends AbstractMedium implements GemeinsameMethoden {

    private String isbn;
    private int seitenanzahl;

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setSeitenanzahl(int seitenanzahl) {
        this.seitenanzahl = seitenanzahl;
    }

    public String getIsbn() {
        return isbn;
    }
    public int getSeitenanzahl() {
        return seitenanzahl;
    }

    @Override
    public String getExtAttrDesc(int attrNumber) {
        return switch (attrNumber) {
            case 1 -> "ISBN";
            case 2 -> "Seitenzahl";
            default -> "NULL";
        };
    }
    @Override
    public String getExtAttrVal(int attrNumber) {
        return switch (attrNumber) {
            case 1 -> getIsbn();
            case 2 -> String.valueOf(getSeitenanzahl());
            default -> "";
        };
    }

    public Buch(String titel, String autor, int erscheinungsjahr) {
        super(titel, autor, erscheinungsjahr);
    }

    public String getTyp(){
        return "Buch";
    }

    @Override
    public boolean suchen(String suchbegriff) {
        return  getTitel().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                getAutor().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                String.valueOf(getErscheinungsjahr()).contains(suchbegriff) ||
                getTyp().toLowerCase().contains(suchbegriff);
    }

}