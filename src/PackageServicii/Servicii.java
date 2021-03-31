package PackageServicii;
import PackageAdmin.Admin;
import PackageClasa.Clasa;
import PackageElev.Elev;
import PackageLoginElev.LoginElev;
import PackageMaterie.Materie;
import PackageProfesor.Profesor;
import PackageScoala.Scoala;
import PackageUser.User;

import java.io.*;
import java.util.*;
import java.lang.*;

public class Servicii {

    public static HashMap<String, User> citireFisier() {
        HashMap<String, User> map = new HashMap<>();
        try {
            File myObj = new File("src/logare.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                User newUser = new User();
                String data = myReader.nextLine();
                String[] vector = data.split(",");
                newUser.setNume(vector[0]);
                newUser.setparola(vector[1]);
                newUser.setStatut(vector[2]);
                map.put(newUser.getNume(), newUser);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return map;
    }

    public static Scoala citireCatalog(){
        Scoala scoala = new Scoala();
        try {
            File myObj = new File("src/catalog.txt");
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
                if(vector[0].equals("clasa")){
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

    public static String logare(HashMap<String, User> map) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Logarea se va face folosing CNPul ca username si parola asociata!");
        System.out.print("Nume: ");
        String nume = scan.nextLine();
        System.out.print("Parola: ");
        String parola = scan.nextLine();
        if(!map.containsKey(nume))
            return "gresit";
        else {
            if (!map.get(nume).getparola().equals(parola))
                return "gresit";
        }
        return nume;
    }

    public static void afisareFisier(HashMap<String, User> map){
        try {
            FileWriter myWriter = new FileWriter("src/logare.txt");
            for(User it : map.values()){
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
            FileWriter afisare = new FileWriter("src/catalog.txt");
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

    public static void administrareCatalog(Scoala scoala){
        String x = "1";
        Scanner scan = new Scanner(System.in);
        while(!x.equals("0")){
            System.out.println("Pentru a reveni in meniul anterior introdu valoarea 0.");
            System.out.println("1 - Vizualizare catalog");
            x = scan.nextLine();
            if(x.equals("1"))
                afisareCatalog(scoala);
            else if(!x.equals("0"))
                System.out.println("Nu ati introdus o valoare corecta");
        }
    }

    public static void administrareConturi(Admin admin){
        String x = "1";
        Scanner scan = new Scanner(System.in);
        while(!x.equals("0")){
            System.out.println("Pentru a reveni in meniul anterior introdu valoarea 0.");
            System.out.println("1 - Vizualizare conturi");
            System.out.println("2 - Creare cont nou");
            System.out.println("3 - Stergere cont");
            System.out.println("4 - Modificare cont");
            x = scan.nextLine();
            if(x.equals("1"))
                admin.afisareConturi();
            else if(x.equals("2"))
                admin.adaugareCont();
            else if(x.equals("3"))
                admin.stergereCont();
            else if(x.equals("4"))
                admin.modificareCont();
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

}
