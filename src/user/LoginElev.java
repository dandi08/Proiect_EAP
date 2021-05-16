package user;

import catalog.Elev;
import catalog.Materie;

import java.util.Objects;
import java.util.Scanner;

public class LoginElev extends User {

    private Elev elev;

    public LoginElev(){
    }

    public LoginElev(Elev elev) {
        this.elev = elev;
    }

    public LoginElev(String nume, String parola, String statut, Elev elev) {
        super(nume, parola, statut);
        this.elev = elev;
    }

    public void schimbaParola(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati noua parola: ");
        String parola = scan.nextLine();
        this.parola = parola;

    }

    public void afiseazaCatalog(){
        System.out.println("Nume elev: " + elev.getNumeElev());
        System.out.println("CNP elev: " + elev.getCnp());
        for(Materie it : elev.getMaterii().values()){
            System.out.println("Nume materie: " + it.getNumeMaterie());
            System.out.println("Absente: " + it.getAbsente());
            System.out.println("Note: " + it.getNote());
        }
    }

    public void calculareMedie(String numeMaterie){
        if(elev.getMaterii().containsKey(numeMaterie)) {
            float medie = 0f;
            for (Float i : elev.getMaterii().get(numeMaterie).getNote())
                medie += i;
            medie = medie / elev.getMaterii().get(numeMaterie).getNote().size();
            System.out.println("La materia " + numeMaterie + " ai media " + medie);
        }
        else
            System.out.println("Elevul nu are materia data");
    }

    public void numarAbsente(String numeMaterie){
        if(elev.getMaterii().containsKey(numeMaterie)) {
            String[] numarAbsente = elev.getMaterii().get(numeMaterie).getAbsente().split(" ");
            if(numarAbsente.length == 1 && numarAbsente[0].equals(""))
                System.out.println("La materia " + numeMaterie + " nu ai nicio absenta");
            else
                System.out.println("La materia " + numeMaterie + " ai " + numarAbsente.length + " absente");
        }
        else
            System.out.println("Elevul nu are materia data");
    }

    public Elev getElev() {
        return elev;
    }

    public void setElev(Elev elev) {
        this.elev = elev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Elev elev = (Elev) o;
        return Objects.equals(nume, elev.getCnp());
    }
}
