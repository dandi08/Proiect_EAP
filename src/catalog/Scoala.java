package catalog;

import java.util.*;

public class Scoala {
    private String nume;
    private String adresa;
    private HashMap<String, Clasa> clase = new HashMap<>();

    public Scoala() {
    }

    public Scoala(String nume, String adresa, HashMap<String, Clasa> clase) {
        this.nume = nume;
        this.adresa = adresa;
        this.clase = clase;
    }

    public void adaugaClasa(Clasa clasa) {
        clase.put(clasa.getNumeClasa(), clasa);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public HashMap<String, Clasa> getClase() {
        return clase;
    }

    public void setClase(HashMap<String, Clasa> clase) {
        this.clase = clase;
    }
}
