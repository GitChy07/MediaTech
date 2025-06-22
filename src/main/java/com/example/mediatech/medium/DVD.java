package com.example.mediatech.medium;

import com.example.mediatech.funktionalitaeten.GemeinsameMethoden;

public class DVD extends AbstractMedium implements GemeinsameMethoden {

    private int fsk;

    public void setFsk(int fsk) {this.fsk = fsk;}
    public int getFsk() { return fsk; }

    @Override
    public String getExtAttrVal(int attrNumber) {
        return switch (attrNumber) {
            case 1 -> String.valueOf(getFsk());
            default -> "";
        };
    }

    public DVD(String titel, String autor, int erscheinungsjahr) {
        super(titel, autor, erscheinungsjahr);
    }

    public String getTyp(){ return "DVD"; }

    @Override
    public boolean suchen(String suchbegriff) {
        // Sucht nach dem Titel, Autor, Erscheinungsjahr und FSK
        return getTitel().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                getAutor().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                String.valueOf(getErscheinungsjahr()).contains(suchbegriff) ||
                getTyp().toLowerCase().contains(suchbegriff);
    }

}