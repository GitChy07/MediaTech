package com.example.mediatech.medium;

public class DVD extends AbstractMedium implements Suchfunktion {

    private String fsk;

    public void setFsk(String fsk) {this.fsk = fsk;}

    public DVD(String titel, String autor, int erscheinungsjahr) {
        super(titel, autor, erscheinungsjahr);
    }

    public String getFsk() {
        return fsk;
    }

    @Override
    public boolean suchen(String suchbegriff) {
        // Sucht nach dem Titel, Autor, Erscheinungsjahr und FSK
        return getTitel().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                getAutor().toLowerCase().contains(suchbegriff.toLowerCase()) ||
                String.valueOf(getErscheinungsjahr()).contains(suchbegriff) ||
                (fsk != null && fsk.contains(suchbegriff));  // Suche nach FSK
    }

}