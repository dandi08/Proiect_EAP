package servicii;
//import admin.Admin;
import catalog.Clasa;
import catalog.Elev;
import catalog.Materie;
import catalog.Scoala;
import user.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.*;

public class Servicii {
    HashMap<String, User> userMap = new HashMap();

    public HashMap<String, User> getUserMap() {
        return userMap;
    }

    public void citireFisier() {
        try {
            File myObj = new File("src/useri.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                User newUser = new User();
                String data = myReader.nextLine();
                String[] vector = data.split(",");
                newUser.setNume(vector[0]);
                newUser.setparola(vector[1]);
                newUser.setStatut(vector[2]);
                userMap.put(newUser.getNume(), newUser);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Scoala citireCatalog(){
        Scoala scoala = new Scoala();
        try {
            File myObj = new File("src/catalog.csv");
            Scanner scan = new Scanner(myObj);
            HashMap<String, Materie> mapMaterii = new HashMap<>();
            HashMap<String, Elev> mapElevi = new HashMap<>();
            HashMap<String, Clasa> mapClasa = new HashMap<>();
            while(scan.hasNextLine()) {
                String data = scan.nextLine();
                String[] vector = data.split(",");
                if(vector[0].equals("materie")){
                    String[] vectorNote = vector[3].split((" "));
                    ArrayList<Float> note = new ArrayList<Float>();
                    for(int i = 0; i < vectorNote.length; i ++)
                        note.add(Float.parseFloat(vectorNote[i]));
                    Materie materie = new Materie(vector[1], vector[2], note);
                    mapMaterii.put(materie.getNumeMaterie(), materie);
                }
                if(vector[0].equals("elev")){
                    Elev elev = new Elev(vector[1], vector[2], mapMaterii);
                    mapElevi.put(elev.getCnp(), elev);
                    mapMaterii = new HashMap<>();
                }
                if(vector[0].equals("catalog")){
                    Clasa clasa = new Clasa(vector[1], vector[2], vector[3], mapElevi);
                    mapClasa.put(clasa.getCnpInvatator(), clasa);
                    mapElevi = new HashMap<>();
                }
                if(vector[0].equals("scoala")){
                    scoala = new Scoala(vector[1], vector[2], mapClasa);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return scoala;
    }

    public User logare(String nume, String parola) {
        User user = new User();
        user.setNume("gresit");
        if(!userMap.containsKey(nume))
            return user;
        else {
            if (!userMap.get(nume).getparola().equals(parola))
                return user;
        }
        return userMap.get(nume);
    }

    public void afisareFisier(HashMap<String, User> map){
        try {
            FileWriter myWriter = new FileWriter("src/useri.csv");
            for(User it : map.values()){
                myWriter.write(it.getNume() + "," + it.getparola() + "," + it.getStatut() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void afisareFisier(){
        try {
            FileWriter myWriter = new FileWriter("src/useri.csv");
            for(User it : userMap.values()){
                myWriter.write(it.getNume() + "," + it.getparola() + "," + it.getStatut() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void scriereCatalog(Scoala scoala){
        try {
            FileWriter afisare = new FileWriter("src/catalog.csv");
            HashMap<String, Clasa> mapClase = scoala.getClase();
            for (Clasa it : mapClase.values()) {
                HashMap<String, Elev> mapElevi = it.getElevi();
                for (Elev ite : mapElevi.values()){
                    HashMap<String, Materie> mapMaterii = ite.getMaterii();
                    for(Materie itm : mapMaterii.values()){
                        afisare.write("materie," + itm.getNumeMaterie() + "," + itm.getAbsente() + ",");
                        for(int iterator = 0; iterator < itm.getNote().size(); iterator++) {
                            if (iterator != itm.getNote().size() - 1)
                                afisare.write(itm.getNote().get(iterator) + " ");
                            else
                                afisare.write(itm.getNote().get(iterator).toString());
                        }
                        afisare.write("\n");
                    }
                    afisare.write("elev," + ite.getNumeElev() + "," + ite.getCnp() + "\n");
                }
                afisare.write("clasa," + it.getNumeClasa() + "," + it.getInvatator() + "," + it.getCnpInvatator() + "\n");
            }
            afisare.write("scoala," + scoala.getNume() + "," + scoala.getAdresa());
            afisare.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void administrareCatalog(Scoala scoala){
        String x = "1";
        Scanner scan = new Scanner(System.in);
        while(!x.equals("0")){
            System.out.println("Pentru a reveni in meniul anterior introdu valoarea 0.");
            System.out.println("1 - Vizualizare catalog");
            x = scan.nextLine();
            if(x.equals("1")){
                this.logs("Vizualizare catalog");
                afisareCatalog(scoala);
            }
            else if(!x.equals("0"))
                System.out.println("Nu ati introdus o valoare corecta");
        }
    }

    public void administrareConturi(Admin admin){
        String x = "1";
        Scanner scan = new Scanner(System.in);
        while(!x.equals("0")){
            System.out.println("Pentru a reveni in meniul anterior introdu valoarea 0.");
            System.out.println("1 - Vizualizare conturi");
            System.out.println("2 - Creare cont nou");
            System.out.println("3 - Stergere cont");
            System.out.println("4 - Modificare cont");
            x = scan.nextLine();
            if(x.equals("1")) {
                this.logs("Vizualizare conturi");
                admin.afisareConturi();
            }
            else if(x.equals("2")) {
                this.logs("Creare cont");
                admin.adaugareCont();
            }
            else if(x.equals("3")) {
                this.logs("Stergere cont");
                admin.stergereCont();
            }
            else if(x.equals("4")) {
                this.logs("Modificare cont");
                admin.modificareCont();
            }
            else if(!x.equals("0"))
                System.out.println("Nu ati introdus o valoare corecta");
        }
    }

    public static void afisareCatalog(Scoala scoala){
        HashMap<String, Clasa> mapClase = scoala.getClase();
        System.out.println("Denumirea: " + scoala.getNume() + " la adresa " + scoala.getAdresa() + " are clasele: ");
        for (Clasa it : mapClase.values()) {
            System.out.println("Numele: " + it.getNumeClasa() + ", Nume invatator = " + it.getInvatator());
            HashMap<String, Elev> mapElevi = it.getElevi();
            for (Elev ite : mapElevi.values()) {
                System.out.println("Numele elevului: " + ite.getNumeElev());
                HashMap<String, Materie> mapMaterii = ite.getMaterii();
                for (Materie itm : mapMaterii.values()) {
                    System.out.println("Numele materiei: " + itm.getNumeMaterie());
                    System.out.println("Notele: " + itm.getNote());
                    System.out.println("Absente: " + itm.getAbsente());
                }
            }
        }
    }
    public void logs(String actiune){
        try {
            FileWriter myWriter = new FileWriter("src/logs.csv", true);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime timeStamp = LocalDateTime.now();
            myWriter.write(actiune + dateFormat.format(timeStamp) + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}