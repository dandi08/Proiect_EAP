package catalog;

import java.util.ArrayList;

public class Materie {
    private String numeMaterie;
    private String absente;
    private ArrayList<Float> note = new ArrayList<Float>();

    public Materie() {
    }

    public Materie(String numeMaterie, String absente, ArrayList<Float> note) {
        this.numeMaterie = numeMaterie;
        this.absente = absente;
        this.note = note;
    }

    public String getNumeMaterie() {
        return numeMaterie;
    }

    public void setNumeMaterie(String numeMaterie) {
        this.numeMaterie = numeMaterie;
    }

    public String getAbsente() {
        return absente;
    }

    public void setAbsente(String absente) {
        this.absente = absente;
    }

    public ArrayList<Float> getNote() {
        return note;
    }

    public void setNote(ArrayList<Float> note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return numeMaterie + " Note: " + note + " Absente: " + absente;
    }
}

