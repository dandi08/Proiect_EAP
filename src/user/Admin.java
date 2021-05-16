package user;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
public class Admin extends User {
    private HashMap<String, User> map;

    public Admin(String nume, String parola, String statut, HashMap<String, User> map) {
        super(nume, parola, statut);
        this.map = map;
    }

    public Admin(HashMap<String, User> map) {
        this.map = map;
    }

    public HashMap<String, User> getMap() {
        return map;
    }

    public void setMap(HashMap<String, User> map) {
        this.map = map;
    }

    public void adaugareCont(){
        String nume, parola, statut;
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele: ");
        nume = scan.nextLine();
        System.out.print("Dati parola: ");
        parola = scan.nextLine();
        System.out.print("Dati statutul: ");
        statut = scan.nextLine();
        User user = new User(nume, parola, statut);
        map.put(user.getNume(), user);
    }

    public void afisareConturi(){
        for(User it : map.values()){
            System.out.println("Nume: " + it.getNume() + ", Parola: " + it.getparola() + ", Statut: " + it.getStatut());
        }
    }

    public void stergereCont(){
        String nume;
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele: ");
        nume = scan.nextLine();
        map.remove(nume);
    }

    public void modificareCont(){
        String nume, parola, statut, numeNou;
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele pe care il cautati: ");
        nume = scan.nextLine();
        System.out.print("Dati noul nume(identic daca nu se doreste modificarea lui): ");
        numeNou = scan.nextLine();
        System.out.print("Dati parola: ");
        parola = scan.nextLine();
        System.out.print("Dati statutul: ");
        statut = scan.nextLine();
        map.remove(nume);
        User user = new User(numeNou, parola, statut);
        map.put(user.getNume(), user);
    }
}