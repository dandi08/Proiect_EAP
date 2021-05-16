package user;

import catalog.Clasa;
import catalog.Elev;
import catalog.Materie;

import java.util.*;

public class Profesor extends User{
    private Clasa clasa;

    public Profesor(String nume, String parola, String statut, Clasa clasa) {
        super(nume, parola, statut);
        this.clasa = clasa;
    }

    public Profesor(Clasa clasa) {
        this.clasa = clasa;
    }

    public Clasa getClasa() {
        return clasa;
    }

    public void setClasa(Clasa clasa) {
        this.clasa = clasa;
    }

    public void schimbaParola(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati noua parola: ");
        String parola = scan.nextLine();
        this.parola = parola;
    }

    public void afiseazaCatalog(){
        System.out.println(clasa.getNumeClasa() + " Invatator: " + clasa.getInvatator());
        for(Elev it : clasa.getElevi().values()){
            LoginElev e = new LoginElev(it);
            e.afiseazaCatalog();
        }
    }

    public void stergeNota(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati CNPul elevului: ");
        String nume = scan.nextLine();
        if(!clasa.getElevi().containsKey(nume)){
            System.out.println("Elevul dat nu a fost gasit!");
            return;
        }
        System.out.print("Dati numele materiei: ");
        String materie = scan.nextLine();
        if(!clasa.getElevi().get(nume).getMaterii().containsKey(materie)){
            System.out.println("Materia data nu a fost gasita!");
            return;
        }
        System.out.print("Dati nota pe care doriti sa o stergeti: ");
        float nota = Float.parseFloat(scan.nextLine());
        int index = clasa.getElevi().get(nume).getMaterii().get(materie).getNote().lastIndexOf(nota);
        clasa.getElevi().get(nume).getMaterii().get(materie).getNote().remove(index);
        System.out.println("Nota a fost stearsa cu succes!");
    }

    public void adaugaNota(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati CNPul elevului: ");
        String nume = scan.nextLine();
        if(!clasa.getElevi().containsKey(nume)){
            System.out.println("Elevul dat nu a fost gasit!");
            return;
        }
        System.out.print("Dati numele materiei: ");
        String materie = scan.nextLine();
        if(!clasa.getElevi().get(nume).getMaterii().containsKey(materie)){
            System.out.println("Materia data nu a fost gasita!");
            return;
        }
        System.out.print("Dati nota pe care doriti sa o adaugati: ");
        float nota = Float.parseFloat(scan.nextLine());
        clasa.getElevi().get(nume).getMaterii().get(materie).getNote().add(nota);
        System.out.println("Nota a fost adaugata cu succes!");
    }

    public void stergeAbsenta(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati CNPul elevului: ");
        String nume = scan.nextLine();
        if(!clasa.getElevi().containsKey(nume)){
            System.out.println("Elevul dat nu a fost gasit!");
            return;
        }
        System.out.print("Dati numele materiei: ");
        String materie = scan.nextLine();
        if(!clasa.getElevi().get(nume).getMaterii().containsKey(materie)){
            System.out.println("Materia data nu a fost gasita!");
            return;
        }
        System.out.print("Dati indexul absentei pe care doriti sa o stergeti: ");
        int index = Integer.parseInt(scan.nextLine());
        String[] s = clasa.getElevi().get(nume).getMaterii().get(materie).getAbsente().split(" ");
        List<String> list = new ArrayList<String>(Arrays.asList(s));
        list.remove(s[index]);
        s = list.toArray(new String[0]);
        String cuvant = String.join(" ",s);
        clasa.getElevi().get(nume).getMaterii().get(materie).setAbsente(cuvant);
        System.out.println("Absenta a fost stearsa cu succes!");
    }

    public void adaugaAbsenta(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati CNPul elevului: ");
        String nume = scan.nextLine();
        if(!clasa.getElevi().containsKey(nume)){
            System.out.println("Elevul dat nu a fost gasit!");
            return;
        }
        System.out.print("Dati numele materiei: ");
        String materie = scan.nextLine();
        if(!clasa.getElevi().get(nume).getMaterii().containsKey(materie)){
            System.out.println("Materia data nu a fost gasita!");
            return;
        }
        System.out.print("Dati absenta pe care doriti sa o adaugati: ");
        String absenta = scan.nextLine();
        if(clasa.getElevi().get(nume).getMaterii().get(materie).getAbsente() != null)
            clasa.getElevi().get(nume).getMaterii().get(materie).setAbsente(clasa.getElevi().get(nume).getMaterii().get(materie).getAbsente().concat(" " + absenta));
        else
            clasa.getElevi().get(nume).getMaterii().get(materie).setAbsente(absenta);
        System.out.println("Absenta a fost adaugata cu succes!");
    }

    public void stergeMaterie(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati CNPul elevului: ");
        String nume = scan.nextLine();
        if(!clasa.getElevi().containsKey(nume)){
            System.out.println("Elevul dat nu a fost gasit!");
            return;
        }
        System.out.print("Dati numele materiei: ");
        String materie = scan.nextLine();
        if(!clasa.getElevi().get(nume).getMaterii().containsKey(materie)){
            System.out.println("Materia data nu a fost gasita!");
            return;
        }
        clasa.getElevi().get(nume).getMaterii().remove(materie);

        System.out.println("Materia a fost stearsa cu succes!");
    }

    public void adaugaMaterie(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Dati CNPul elevului: ");
        String nume = scan.nextLine();
        if(!clasa.getElevi().containsKey(nume)){
            System.out.println("Elevul dat nu a fost gasit!");
            return;
        }
        System.out.print("Dati numele materiei: ");
        String materie = scan.nextLine();
        Materie mat = new Materie();
        mat.setNumeMaterie(materie);
        clasa.getElevi().get(nume).getMaterii().put(materie, mat);
        System.out.print("Dati nota pe care doriti sa o adaugati: ");
        float nota = Float.parseFloat(scan.nextLine());
        clasa.getElevi().get(nume).getMaterii().get(materie).getNote().add(nota);
        clasa.getElevi().get(nume).getMaterii().get(materie).setAbsente("");
        System.out.println("Materia a fost adaugata cu succes!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clasa clasa = (Clasa) o;
        return Objects.equals(nume, clasa.getCnpInvatator());
    }
}