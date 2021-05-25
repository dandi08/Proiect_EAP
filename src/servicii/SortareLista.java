package servicii;
import catalog.*;
import java.util.*;

class SortareLista implements Comparator<Clasa> {
    public int compare(Clasa a, Clasa b) {
        return a.getNumeClasa().compareTo(b.getNumeClasa());
    }
}
