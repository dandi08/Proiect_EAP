package servicii;
//import admin.Admin;
import catalog.Clasa;
import catalog.Elev;
import catalog.Materie;
import catalog.Scoala;
import user.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.*;

public class Servicii {
    private HashMap<String, User> userMap = new HashMap();
    private Scoala scoala = new Scoala();
    public HashMap<String, User> getUserMap() {
        return userMap;
    }
    boolean bd = false;

    public boolean isBd() {
        return bd;
    }

    public void setBd(boolean bd) {
        this.bd = bd;
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

    public void citireCatalog(){

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
                    String[] vectorNote = vector[4].split((" "));
                    ArrayList<Float> note = new ArrayList<Float>();
                    for(int i = 0; i < vectorNote.length; i ++)
                        note.add(Float.parseFloat(vectorNote[i]));
                    Materie materie = new Materie(Integer.parseInt(vector[1]), vector[2], vector[3], note);
                    mapMaterii.put(materie.getNumeMaterie(), materie);
                }
                if(vector[0].equals("elev")){
                    Elev elev = new Elev(vector[1], vector[2], vector[3], mapMaterii);
                    mapElevi.put(elev.getCnp(), elev);
                    mapMaterii = new HashMap<>();
                }
                if(vector[0].equals("clasa")){
                    Clasa clasa = new Clasa(Integer.parseInt(vector[1]), vector[2], vector[3], vector[4], mapElevi);
                    mapClasa.put(clasa.getCnpInvatator(), clasa);
                    mapElevi = new HashMap<>();
                }
                if(vector[0].equals("scoala")){
                    this.scoala = new Scoala(Integer.parseInt(vector[1]), vector[2], vector[3], mapClasa);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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

    public void scriereCatalog(){
        try {
            FileWriter afisare = new FileWriter("src/catalog.csv");
            HashMap<String, Clasa> mapClase = scoala.getClase();
            for (Clasa it : mapClase.values()) {
                HashMap<String, Elev> mapElevi = it.getElevi();
                for (Elev ite : mapElevi.values()){
                    HashMap<String, Materie> mapMaterii = ite.getMaterii();
                    for(Materie itm : mapMaterii.values()){
                        afisare.write("materie," + itm.getIdMaterie() + "," + itm.getNumeMaterie() + "," + itm.getAbsente() + ",");
                        for(int iterator = 0; iterator < itm.getNote().size(); iterator++) {
                            if (iterator != itm.getNote().size() - 1)
                                afisare.write(itm.getNote().get(iterator) + " ");
                            else
                                afisare.write(itm.getNote().get(iterator).toString());
                        }
                        afisare.write("\n");
                    }
                    afisare.write("elev," + ite.getNumeClasa() + "," + ite.getNumeElev() + "," + ite.getCnp() + "\n");
                }
                afisare.write("clasa," + it.getIdScoala() + "," + it.getNumeClasa() + "," + it.getInvatator() + "," + it.getCnpInvatator() + "\n");
            }
            afisare.write("scoala," + scoala.getId() + "," + scoala.getNume() + "," + scoala.getAdresa());
            afisare.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void administrareCatalog(){
        String x = "1";
        Scanner scan = new Scanner(System.in);
        while(!x.equals("0")){
            System.out.println("Pentru a reveni in meniul anterior introdu valoarea 0.");
            System.out.println("1 - Vizualizare catalog");
            System.out.println("2 - Adaugare elev");
            System.out.println("3 - Sterge elev");
            System.out.println("4 - Modifica nume elev");
            System.out.println("5 - Adaugare clasa");
            System.out.println("6 - Sterge clasa");
            System.out.println("7 - Modifica nume clasa");
            System.out.println("8 - Modifica nume scoala");
            System.out.println("9 - Modifica adresa scoala");
            x = scan.nextLine();
            if(x.equals("1")){
                this.logs("Vizualizare catalog");
                afisareCatalog();
            }
            else if(x.equals("2")) {
                this.logs("Adaugare elev");
                adaugaElev();
            }
            else if(x.equals("3")) {
                this.logs("Stergere elev");
                stergeElev();
            }
            else if(x.equals("4")) {
                this.logs("Modificare nume elev");
               modificaNumeElev();
            }
            else if(x.equals("5")) {
                this.logs("Adaugare clasa");
                adaugaClasa();
            }
            else if(x.equals("6")) {
                this.logs("Stergere clasa");
                stergeClasa();
            }
            else if(x.equals("7")) {
                this.logs("Modificare nume profesor");
                modificaClasa();
            }
            else if(x.equals("8")) {
                this.logs("Modificare nume scoala");
                modificareScoala();
            }
            else if(x.equals("9")) {
                this.logs("Modificare adresa scoala");
                modificareAdresaScoala();
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

    public void afisareCatalog(){
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
            myWriter.write(actiune + " " + dateFormat.format(timeStamp) + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Scoala getScoala() {
        return scoala;
    }

    public void setScoala(Scoala scoala) {
        this.scoala = scoala;
    }

    public void adaugaElev(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele clasei: ");
        String numeClasa = scan.nextLine();
        int app = 0;
        String cnpInvatator = "";
        for( Clasa it : scoala.getClase().values())
            if(it.getNumeClasa().equals(numeClasa)){
                app++;
                cnpInvatator = it.getCnpInvatator();
            }
        if(app == 0){
            System.out.println("Clasa data nu a fost gasita!");
            return;
        }
        System.out.print("Dati numele elevului: ");
        String numeElev = scan.nextLine();
        System.out.print("Dati CNPul elevului: ");
        String cnp = scan.nextLine();
        Elev elev = new Elev();
        elev.setNumeElev(numeElev);
        elev.setCnp(cnp);
        elev.setNumeClasa(numeClasa);
        scoala.getClase().get(cnpInvatator).addElevMap(elev);
        if(this.bd == true) {
            SqlConnection sql = new SqlConnection();
            try {
                Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
                Statement stmt = con.createStatement();
                stmt.executeUpdate("Insert into elevi values ( \"" + cnp + "\",\"" + numeElev + "\", \"" + numeClasa + "\")");
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Elevul a fost adaugat cu succes!");
        }
    }

    public void stergeElev(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele clasei: ");
        String numeClasa = scan.nextLine();
        int app = 0;
        String cnpInvatator = "";
        for( Clasa it : scoala.getClase().values())
            if(it.getNumeClasa().equals(numeClasa)){
                app++;
                cnpInvatator = it.getCnpInvatator();
            }
        if(app == 0){
            System.out.println("Clasa data nu a fost gasita!");
            return;
        }
        System.out.print("Dati CNPul elevului: ");
        String cnp = scan.nextLine();
        if(!scoala.getClase().get(cnpInvatator).getElevi().containsKey(cnp)){
            System.out.println("Elevul nu a fost gasit!");
            return;
        }
        if(this.bd == true) {
            SqlConnection sql = new SqlConnection();
            try {
                Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE from Elevi where CNP = \"" + cnp + "\"");
                con.close();
                scoala.getClase().get(cnpInvatator).getElevi().remove(cnp);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void modificaNumeElev(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele clasei: ");
        String numeClasa = scan.nextLine();
        int app = 0;
        String cnpInvatator = "";
        for( Clasa it : scoala.getClase().values())
            if(it.getNumeClasa().equals(numeClasa)){
                app++;
                cnpInvatator = it.getCnpInvatator();
            }
        if(app == 0){
            System.out.println("Clasa data nu a fost gasita!");
            return;
        }
        System.out.print("Dati CNPul elevului: ");
        String cnp = scan.nextLine();
        if(!scoala.getClase().get(cnpInvatator).getElevi().containsKey(cnp)){
            System.out.println("Elevul nu a fost gasit!");
            return;
        }
        System.out.print("Dati numele elevului: ");
        String numeNou = scan.nextLine();
        scoala.getClase().get(cnpInvatator).getElevi().get(cnp).setNumeElev(numeNou);
        System.out.println(scoala.getClase().get(cnpInvatator).getElevi().get(cnp).getNumeElev());
        if(this.bd == true) {
            SqlConnection sql = new SqlConnection();
            try {
                Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE elevi set Nume_elev = \"" + numeNou + "\" where CNP = \"" + cnp + "\"");
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void adaugaClasa(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele clasei: ");
        String numeClasa = scan.nextLine();
        System.out.print("Dati numele invatatorului: ");
        String numeInvatator = scan.nextLine();
        System.out.print("Dati cnpul invatatorului: ");
        String cnpInvatator = scan.nextLine();
        Clasa clasaNoua = new Clasa();
        clasaNoua.setNumeClasa(numeClasa);
        clasaNoua.setInvatator(numeInvatator);
        clasaNoua.setIdScoala(1);
        clasaNoua.setCnpInvatator(cnpInvatator);
        scoala.getClase().put(cnpInvatator, clasaNoua);
        if(this.bd == true) {
            SqlConnection sql = new SqlConnection();
            try {
                Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
                Statement stmt = con.createStatement();
                System.out.println("Insert into clasa values ( \"" + numeClasa + "\",\"" + numeInvatator + "\", \"" + cnpInvatator + "\"," + 1 + ")");
                stmt.executeUpdate("Insert into clasa values ( \"" + numeClasa + "\",\"" + numeInvatator + "\", \"" + cnpInvatator + "\"," + 1 + ")");
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Elevul a fost adaugat cu succes!");
    }

    public void stergeClasa(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele clasei: ");
        String numeClasa = scan.nextLine();
        int app = 0;
        String cnpInvatator = "";
        for( Clasa it : scoala.getClase().values())
            if(it.getNumeClasa().equals(numeClasa)){
                app++;
                cnpInvatator = it.getCnpInvatator();
            }
        if(app == 0){
            System.out.println("Clasa data nu a fost gasita!");
            return;
        }
        scoala.getClase().remove(cnpInvatator);
        if(this.bd == true) {
            SqlConnection sql = new SqlConnection();
            try {
                Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
                Statement stmt = con.createStatement();
                stmt.executeUpdate("DELETE from clasa where Nume_clasa = \"" + numeClasa + "\"");
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void modificaClasa(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele clasei: ");
        String numeClasa = scan.nextLine();
        int app = 0;
        String cnpInvatator = "";
        for( Clasa it : scoala.getClase().values())
            if(it.getNumeClasa().equals(numeClasa)){
                app++;
                cnpInvatator = it.getCnpInvatator();
            }
        if(app == 0){
            System.out.println("Clasa data nu a fost gasita!");
            return;
        }
        System.out.print("Dati numele nou al invatatorului: ");
        String numeInvatator = scan.nextLine();
        scoala.getClase().get(cnpInvatator).setInvatator(numeInvatator);
        SqlConnection sql = new SqlConnection();
        try {
            Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE clasa set Invatator = \"" + numeInvatator + "\" where Nume_clasa = \"" + numeClasa + "\"");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public void modificareScoala(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati numele nou al scolii: ");
        String numeNouScoala = scan.nextLine();
        scoala.setNume(numeNouScoala);
        if(this.bd == true) {
            SqlConnection sql = new SqlConnection();
            try {
                Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE scoala set Nume_scoala = \"" + numeNouScoala + "\" where Id_scoala = " + 1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void modificareAdresaScoala(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati aresa noua a scolii: ");
        String adresaNouaScoala = scan.nextLine();
        scoala.setAdresa(adresaNouaScoala);
        if(this.bd == true) {
            SqlConnection sql = new SqlConnection();
            try {
                Connection con = DriverManager.getConnection(sql.getPath(), sql.getUsername(), sql.getPassword());
                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE scoala set Adresa = \"" + adresaNouaScoala + "\" where Id_scoala = " + 1);
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
