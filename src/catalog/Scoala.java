package catalog;

import com.sun.source.tree.Tree;

import java.util.*;

public class Scoala {
    int id;
    private String nume;
    private String adresa;
    private HashMap<String, Clasa> clase = new HashMap<>();

    public Scoala() {
    }

    public Scoala(int id, String nume, String adresa, HashMap<String, Clasa> clase) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.clase = clase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void addScoalaMap(Clasa clasa){
        clase.put(clasa.getCnpInvatator(), clasa);
    }

}
