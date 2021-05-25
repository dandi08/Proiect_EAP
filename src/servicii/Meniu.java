package servicii;

//import admin.Admin;
import catalog.Clasa;
import catalog.Elev;
//import loginelev.LoginElev;
//import profesor.Profesor;
import catalog.Scoala;
import user.*;

import java.util.HashMap;
import java.util.Scanner;

public class Meniu {

    public static void main(String[] args) {
        Scanner scan1 = new Scanner(System.in);
        System.out.println("1 - baza de date \n2 - csv");
        int op = scan1.nextInt();
        Servicii servicii = new Servicii();
        SqlConnection sql = new SqlConnection();
        if(op == 2) {
            servicii.citireCatalog();
            servicii.setBd(false);
        }
        else {
            servicii.setScoala(sql.citireTabel());
            servicii.setBd(true);
        }
        //servicii.afisareCatalog();
        //HashMap<String, User> userMap = new HashMap();
        servicii.citireFisier();
        // Afisare HashMap dupa valori si chei
        /*for(String it : userMap.keySet()){
            System.out.println(it);
        }
        for(User it : userMap.values()){
            System.out.println(it.getNume() + " " + it.getparola() + " " + it.getStatut());
        }*/
        User user = new User();
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("Logarea se va face folosing CNPul ca username si parola asociata!");
            System.out.print("Nume: ");
            String nume = scan.nextLine();
            System.out.print("Parola: ");
            String parola = scan.nextLine();
            user = servicii.logare(nume, parola);
        }while(user.getNume().equals("gresit"));
        if(user.getStatut().equals("admin"))
        {
            servicii.logs("Logare admin");
            System.out.println("-----" + user.getStatut());
            Admin admin = new Admin(user.getNume(), user.getparola(), user.getStatut(), servicii.getUserMap());
            String x  = "1";
            Scanner scan = new Scanner(System.in);
            while(!x.equals("0")){
                System.out.println("Pentru a iesi si a salva modificarile introdu 0.");
                System.out.println("1 - Administrare conturi");
                System.out.println("2 - Administrare catalog");
                x = scan.nextLine();
                if(x.equals("1"))
                    servicii.administrareConturi(admin);
                else if(x.equals("2"))
                    servicii.administrareCatalog();
                else if(!x.equals("0"))
                    System.out.println("Nu ati introdus o valoare corecta");
            }
            servicii.afisareFisier(admin.getMap());
        }
        else if(user.getStatut().equals("elev")){
            servicii.logs("Logare elev");
            System.out.println("-----" + user.getStatut());
            HashMap<String, Clasa> mapClase = servicii.getScoala().getClase();
            LoginElev loginElev = new LoginElev();
            for (Clasa it : mapClase.values()) {
                HashMap<String, Elev> mapElevi = it.getElevi();
                for (Elev ite : mapElevi.values()){
                    if(ite.getCnp().equals(user.getNume())){
                        loginElev = new LoginElev(user.getNume(), user.getparola(), user.getStatut(), ite);
                        break;
                    }
                }
            }
            String x  = "1";
            Scanner scan = new Scanner(System.in);
            while(!x.equals("0")) {
                System.out.println("Pentru a iesi si a salva modificarile introdu 0.");
                System.out.println("1 - Schimba parola");
                System.out.println("2 - Vizualizare catalog");
                System.out.println("3 - Calculare Medie");
                System.out.println("4 - Numar absente");
                x = scan.nextLine();
                if (x.equals("1")){
                    servicii.logs("Schimba parola");
                    loginElev.schimbaParola();
                    //userMap.get(loginElev.getNume()).setparola(loginElev.getparola());
                }
                else if (x.equals("2")) {
                    servicii.logs("Vizualizare catalog");
                    loginElev.afiseazaCatalog();
                }
                else if(x.equals("3")) {
                    servicii.logs("Calculare medie");
                    System.out.print("Dati numele materiei la care doriti sa vedeti media: ");
                    loginElev.calculareMedie(scan.nextLine());
                }
                else if(x.equals("4")){
                    servicii.logs("Afisare numar absente");
                    System.out.print("Dati numele materiei la care doriti sa vedeti numarul de absente: ");
                    loginElev.numarAbsente(scan.nextLine());
                }
                else if (!x.equals("0"))
                    System.out.println("Nu ati introdus o valoare corecta");
            }
            servicii.afisareFisier();
        }
        else if(user.getStatut().equals("profesor")){
            servicii.logs("Logare profesor");
            if(servicii.getScoala().getClase().containsKey(user.getNume())){
                String x  = "1";
                Scanner scan = new Scanner(System.in);
                Profesor profesor = new Profesor(user.getNume(), user.getparola(), user.getStatut(), servicii.getScoala().getClase().get(user.getNume()));
                while(!x.equals("0")) {
                    System.out.println("Pentru a iesi si a salva modificarile introdu 0.");
                    System.out.println("1 - Schimba parola");
                    System.out.println("2 - Vizualizare catalog");
                    System.out.println("3 - Sterge nota");
                    System.out.println("4 - Adauga nota");
                    System.out.println("5 - Sterge absenta");
                    System.out.println("6 - Adauga absenta");
                    System.out.println("7 - Sterge materie");
                    System.out.println("8 - Adauga materie");
                    System.out.println("9 - Modifica nume materie");
                    x = scan.nextLine();
                    if (x.equals("1")){
                        servicii.logs("Schimba parola");
                        profesor.schimbaParola();
                        //userMap.get(profesor.getNume()).setparola(profesor.getparola());
                    }
                    else if (x.equals("2")) {
                        servicii.logs("Vizualizare catalog");
                        profesor.afiseazaCatalog();
                    }
                    else if(x.equals("3")) {
                        servicii.logs("Sterge nota");
                        profesor.stergeNota();
                        servicii.getScoala().getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("4")){
                        servicii.logs("Adauga nota");
                        profesor.adaugaNota();
                        servicii.getScoala().getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("5")) {
                        servicii.logs("Sterge absenta");
                        profesor.stergeAbsenta();
                        servicii.getScoala().getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("6")){
                        servicii.logs("Adauga absenta");
                        profesor.adaugaAbsenta();
                        servicii.getScoala().getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("7")) {
                        servicii.logs("Sterge materie");
                        profesor.stergeMaterie();
                        servicii.getScoala().getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("8")){
                        servicii.logs("Adauga materie");
                        profesor.adaugaMaterie();
                        servicii.getScoala().getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("9")){
                        servicii.logs("Modifica nume materie");
                        profesor.modificaNumeMaterie();
                        servicii.getScoala().getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if (!x.equals("0"))
                        System.out.println("Nu ati introdus o valoare corecta");
                }
            }
            else
                System.out.println("Profesorul nu are nicio clasa momentan!");
            servicii.afisareFisier();
        }
        servicii.scriereCatalog();
    }
}
