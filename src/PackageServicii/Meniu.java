package PackageServicii;

import PackageAdmin.Admin;
import PackageClasa.Clasa;
import PackageElev.Elev;
import PackageLoginElev.LoginElev;
import PackageProfesor.Profesor;
import PackageScoala.Scoala;
import PackageUser.User;

import java.util.HashMap;
import java.util.Scanner;

public class Meniu {

    public static void main(String[] args) {
        Scoala scoala = new Scoala();
        scoala = Servicii.citireCatalog();
        HashMap<String, User> userMap = new HashMap();
        userMap = Servicii.citireFisier();
        // Afisare HashMap dupa valori si chei
        /*for(String it : userMap.keySet()){
            System.out.println(it);
        }
        for(User it : userMap.values()){
            System.out.println(it.getNume() + " " + it.getparola() + " " + it.getStatut());
        }*/
        String nume = Servicii.logare(userMap);
        while(nume.equals("gresit")) {
            System.out.println("Datele introduse sunt gresite. Incercati din nou!");
            nume = Servicii.logare(userMap);
        }
        if(userMap.get(nume).getStatut().equals("admin"))
        {
            System.out.println("-----" + userMap.get(nume).getStatut());
            Admin admin = new Admin(userMap.get(nume).getNume(), userMap.get(nume).getparola(), userMap.get(nume).getStatut(), userMap);
            String x  = "1";
            Scanner scan = new Scanner(System.in);
            while(!x.equals("0")){
                System.out.println("Pentru a iesi si a salva modificarile introdu 0.");
                System.out.println("1 - Administrare conturi");
                System.out.println("2 - Administrare catalog");
                x = scan.nextLine();
                if(x.equals("1"))
                    Servicii.administrareConturi(admin);
                else if(x.equals("2"))
                    Servicii.administrareCatalog(scoala);
                else if(!x.equals("0"))
                    System.out.println("Nu ati introdus o valoare corecta");
            }
            Servicii.afisareFisier(admin.getMap());
        }
        else if(userMap.get(nume).getStatut().equals("elev")){
            System.out.println("-----" + userMap.get(nume).getStatut());
            HashMap<String, Clasa> mapClase = scoala.getClase();
            LoginElev loginElev = new LoginElev();
            for (Clasa it : mapClase.values()) {
                HashMap<String, Elev> mapElevi = it.getElevi();
                for (Elev ite : mapElevi.values()){
                    if(ite.getCnp().equals(nume)){
                        loginElev = new LoginElev(userMap.get(nume).getNume(), userMap.get(nume).getparola(), userMap.get(nume).getStatut(), ite);
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
                    loginElev.schimbaParola();
                    userMap.get(loginElev.getNume()).setparola(loginElev.getparola());
                }
                else if (x.equals("2"))
                    loginElev.afiseazaCatalog();
                else if(x.equals("3")) {
                    System.out.print("Dati numele materiei la care doriti sa vedeti media: ");
                    loginElev.calculareMedie(scan.nextLine());
                }
                else if(x.equals("4")){
                    System.out.print("Dati numele materiei la care doriti sa vedeti numarul de absente: ");
                    loginElev.numarAbsente(scan.nextLine());
                }
                else if (!x.equals("0"))
                    System.out.println("Nu ati introdus o valoare corecta");
            }
            Servicii.afisareFisier(userMap);
        }
        else if(userMap.get(nume).getStatut().equals("profesor")){
            if(scoala.getClase().containsKey(nume)){
                String x  = "1";
                Scanner scan = new Scanner(System.in);
                Profesor profesor = new Profesor(userMap.get(nume).getNume(), userMap.get(nume).getparola(), userMap.get(nume).getStatut(), scoala.getClase().get(nume));
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
                    x = scan.nextLine();
                    if (x.equals("1")){
                        profesor.schimbaParola();
                        userMap.get(profesor.getNume()).setparola(profesor.getparola());
                    }
                    else if (x.equals("2"))
                        profesor.afiseazaCatalog();
                    else if(x.equals("3")) {
                        profesor.stergeNota();
                        scoala.getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("4")){
                        profesor.adaugaNota();
                        scoala.getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("5")) {
                        profesor.stergeAbsenta();
                        scoala.getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("6")){
                        profesor.adaugaAbsenta();
                        scoala.getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("7")) {
                        profesor.stergeMaterie();
                        scoala.getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if(x.equals("8")){
                        profesor.adaugaMaterie();
                        scoala.getClase().put(profesor.getNume(), profesor.getClasa());
                    }
                    else if (!x.equals("0"))
                        System.out.println("Nu ati introdus o valoare corecta");
                }
            }
            else
                System.out.println("Profesorul nu are nicio clasa momentan!");
            Servicii.afisareFisier(userMap);
        }
        Servicii.scriereCatalog(scoala);
    }
}
