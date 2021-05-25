package catalog;

import java.util.*;

public class Elev {
    private String numeElev;
    private String cnp;
    private String numeClasa;
    private HashMap<String, Materie> materii = new HashMap<>();

    public Elev(String numeClasa, String numeElev, String cnp, HashMap<String, Materie> materii) {
        this.numeElev = numeElev;
        this.cnp = cnp;
        this.materii = materii;
        this.numeClasa = numeClasa;
    }

    public Elev(){

    }

    public String getNumeClasa() {
        return numeClasa;
    }

    public void setNumeClasa(String numeClasa) {
        this.numeClasa = numeClasa;
    }

    public void adaugaMaterie(Materie materie) {
        materii.put(materie.getNumeMaterie(), materie);
    }

    public String getNumeElev() {
        return numeElev;
    }

    public void setNumeElev(String numeElev) {
        this.numeElev = numeElev;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public HashMap<String, Materie> getMaterii() {
        return materii;
    }

    public void setMaterii(HashMap<String, Materie> materii) {
        this.materii = materii;
    }

    public void addMaterieMap(Materie materie){
        this.materii.put(materie.getNumeMaterie(), materie);
    }

    @Override
    public String toString() {
        String afisare = "Nume: " + numeElev + " CNP: " + cnp + " Clasa: " + numeClasa;
        return afisare;
    }
}
