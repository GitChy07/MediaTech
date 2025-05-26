package com.example.mediatech.medium;

public abstract class IMedium implements AbstractMedium {
    private String titel;
    private String autor;
    private int erscheinungsjahr;

    public IMedium(String titel, String autor, int erscheinungsjahr) {
        this.titel = titel;
        this.autor = autor;
        this.erscheinungsjahr = erscheinungsjahr;
    }

    @Override
    public String getTitel() {
        return titel;
    }

    @Override
    public String getAutor() { return autor; }

    @Override
    public int getErscheinungsjahr() {
        return erscheinungsjahr;
    }

}