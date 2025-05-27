package com.example.mediatech.medium;

public class DVD extends AbstractMedium {
    private String fsk;

    public DVD(String titel, String autor, int erscheinungsjahr) {
        super(titel, autor, erscheinungsjahr);
    }

    public String getFsk() {
        return fsk;
    }
}