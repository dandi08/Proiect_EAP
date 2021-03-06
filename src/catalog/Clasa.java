package catalog;

import java.util.*;

public class Clasa implements Comparable<Clasa>{
    int idScoala;
    private String numeClasa;
    private String invatator;
    private String cnpInvatator;
    private HashMap<String, Elev> elevi = new HashMap<>();

    public Clasa() {
    }

    public Clasa(int idScoala, String numeClasa, String invatator, String cnpInvatator, HashMap<String, Elev> elevi) {
        this.idScoala = idScoala;
        this.numeClasa = numeClasa;
        this.invatator = invatator;
        this.cnpInvatator = cnpInvatator;
        this.elevi = elevi;
    }

    public int getIdScoala() {
        return idScoala;
    }

    public void setIdScoala(int idScoala) {
        this.idScoala = idScoala;
    }

    public void adaugaElev(Elev elev){
        elevi.put(elev.getCnp(), elev);
    }

    public void setNumeClasa(String numeClasa) {
        this.numeClasa = numeClasa;
    }

    public String getNumeClasa() {
        return numeClasa;
    }

    public String getInvatator() {
        return invatator;
    }

    public void setInvatator(String invatator) {
        this.invatator = invatator;
    }

    public String getCnpInvatator() {
        return cnpInvatator;
    }

    public void setCnpInvatator(String cnpInvatator) {
        this.cnpInvatator = cnpInvatator;
    }

    public HashMap<String, Elev> getElevi() {
        return elevi;
    }

    public void setElevi(HashMap<String, Elev> elevi) {
        this.elevi = elevi;
    }

    public void addElevMap(Elev elev){
        elevi.put(elev.getCnp(), elev);
    }

    public int compareTo(Clasa clasa){
        return this.getNumeClasa().compareTo(clasa.getNumeClasa());
    }

}
