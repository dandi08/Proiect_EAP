package user;

import java.util.*;

public class User {
    protected String nume;
    protected String parola;
    protected String statut;

    public User(String nume, String parola, String statut) {
        this.nume = nume;
        this.parola = parola;
        this.statut = statut;
    }

    public User() {
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getparola() {
        return parola;
    }

    public void setparola(String parola) {
        this.parola = parola;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nume, user.nume);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}





