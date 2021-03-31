package PackageElev;

import java.util.*;
import PackageMaterie.*;

public class Elev {
    private String numeElev;
    private String cnp;
    private HashMap<String, Materie> materii = new HashMap<>();

    public Elev(String numeElev, String cnp, HashMap<String, Materie> materii) {
        this.numeElev = numeElev;
        this.cnp = cnp;
        this.materii = materii;
    }

    public void adaugaMaterie(Materie materie){
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

    @Override
    public String toString() {
        String afisare = "Nume: " + numeElev + "\n";
        for(Materie it : materii.values()){
            afisare.concat("Nume materie: " + it.getNumeMaterie() + "\n" + "Absente: " + it.getAbsente() + "\n" + "Note: " + it.getNote() + "\n");
        }
        return afisare;
    }
}
