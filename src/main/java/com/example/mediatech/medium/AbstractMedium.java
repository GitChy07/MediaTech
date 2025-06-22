package com.example.mediatech.medium;

public abstract class AbstractMedium{
    private String titel;
    private String autor;
    private int erscheinungsjahr;

    public AbstractMedium(String titel, String autor, int erscheinungsjahr) {
        this.titel = titel;
        this.autor = autor;
        this.erscheinungsjahr = erscheinungsjahr;
    }

    public String getTitel() {
        return titel;
    }

    public String getAutor() { return autor; }

    public int getErscheinungsjahr() {
        return erscheinungsjahr;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    };
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setErscheinungsjahr(int jahr) {this.erscheinungsjahr = erscheinungsjahr;}

}