package servicii;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import catalog.*;

public class SqlConnection {
    private String path = "jdbc:mysql://localhost:3306/catalog";
    private String username = "root";
    private String password = "qwertyqaz";
    HashMap <Integer, Scoala> scoli = new HashMap<Integer, Scoala>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Scoala citireTabel(){
        try{
            HashMap <Integer, String> materii = new HashMap<Integer, String>();
            HashMap <String, Elev> elevi = new HashMap<String, Elev>();
            Connection con=DriverManager.getConnection(path, username, password);
            Statement stmt=con.createStatement();
            ArrayList<Clasa> clase = new ArrayList<>();
            ResultSet rsElev=stmt.executeQuery("select * from Elevi");
            while(rsElev.next()) {
                Elev elevNou = new Elev();
                elevNou.setNumeClasa(rsElev.getString(3));
                elevNou.setNumeElev(rsElev.getString(2));
                elevNou.setCnp(rsElev.getString(1));
                elevi.put(elevNou.getCnp(), elevNou);
            }
            ResultSet rsMaterie=stmt.executeQuery("select * from Materii");
            while(rsMaterie.next()) {
                materii.put(rsMaterie.getInt(2),rsMaterie.getString(1));
            }
            ResultSet rsEleviMaterii=stmt.executeQuery("select * from EleviMaterii");
            while(rsEleviMaterii.next()) {
                Materie materieNoua = new Materie();
                materieNoua.setAbsente(rsEleviMaterii.getString(2));
                String[] vectorNote = rsEleviMaterii.getString(1).split((" "));
                ArrayList<Float> note = new ArrayList<Float>();
                for(int i = 0; i < vectorNote.length; i ++)
                    note.add(Float.parseFloat(vectorNote[i]));
                materieNoua.setNote(note);
                materieNoua.setNumeMaterie(materii.get(rsEleviMaterii.getInt(3)));
                materieNoua.setIdMaterie(rsEleviMaterii.getInt(3));
                elevi.get(rsEleviMaterii.getString(4)).addMaterieMap(materieNoua);
            }
            ResultSet rsClasa=stmt.executeQuery("select * from Clasa");
            while(rsClasa.next()) {
                Clasa clasa = new Clasa();
                clasa.setIdScoala(rsClasa.getInt(4));
                clasa.setNumeClasa(rsClasa.getString(1));
                clasa.setInvatator(rsClasa.getString(2));
                clasa.setCnpInvatator(rsClasa.getString(3));
                for(Elev it : elevi.values()){
                    if(it.getNumeClasa().equals(clasa.getNumeClasa()))
                        clasa.addElevMap(it);
                }
                clase.add(clasa);
            }
            ResultSet rsScoala=stmt.executeQuery("select * from scoala");
            Collections.sort(clase, new SortareLista());
            Scoala scoala = new Scoala();
            while(rsScoala.next()) {
                scoala = new Scoala();
                scoala.setAdresa(rsScoala.getString(3));
                scoala.setNume(rsScoala.getString(2));
                scoala.setId(rsScoala.getInt(1));
                for (int i=0; i<clase.size(); i++){
                    if(scoala.getId() == clase.get(i).getIdScoala())
                        scoala.addScoalaMap(clase.get(i));
                }
            }
            con.close();
            return scoala;
        }catch(Exception e){ System.out.println(e);}
        Scoala scoala = new Scoala();
        return scoala;
    }



    public static void main(String[] args){
        SqlConnection sql = new SqlConnection();
        sql.citireTabel();
    }

}
