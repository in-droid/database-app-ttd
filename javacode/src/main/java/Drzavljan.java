import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;

class DrzavljanPrimerjajPoImenu implements java.util.Comparator<Drzavljan> {

    @Override
    public int compare(Drzavljan o1, Drzavljan o2) {
        String ime1 = o1.getPriimek() + ", " + o1.getIme();
        ime1 = ime1.replaceAll("\\s+","");
        String ime2 = o2.getPriimek() + ", " + o2.getIme();
        ime2 = ime2.replaceAll("\\s+","");
        return ime1.compareToIgnoreCase(ime2);
    }
}

class DrzavljanPrimerjajPoEMSO implements java.util.Comparator<Drzavljan> {

    @Override
    public int compare(Drzavljan o1, Drzavljan o2) {
        String emso1 = o1.getEmso();
        String emso2 = o2.getEmso();
        return emso1.compareTo(emso2);
    }
}


public class Drzavljan implements Serializable {
    private static final String[] CEPIVA_VALUES = {"AstraZeneca", "Pfeizer", "Moderna", "Johnson"};
    private static final HashSet<String> CEPIVA = new HashSet<>(Arrays.asList(CEPIVA_VALUES));
    private static final String nameRegEx = "^\\p{L}+$";

    protected String ime;
    protected String priimek;
    protected String emso;
    protected int starost;
    protected String cepivo;

    public Drzavljan(String emso, String ime, String priimek, int starost, String cepivo) {
        if (!emso.matches("^[0-9]*$") || emso.length() != 13) {
            throw new InvalidDataException();
        }
        this.emso = emso;
        checkCorrectness(ime);
        checkCorrectness(priimek);
        this.ime = ime;
        this.priimek = priimek;
        if (starost < 0 || starost > 120) {
            throw new InvalidDataException();
        }
        this.starost = starost;
        if (!CEPIVA.contains(cepivo)) {
            throw new InvalidDataException();
        }
        this.cepivo = cepivo;
    }

    Drzavljan(String emso) {
        if (!emso.matches("^[0-9]*$") || emso.length() != 13) {
            throw new InvalidDataException();
        }
        this.emso = emso;
    }

    Drzavljan(String ime, String priimek) {
        checkCorrectness(ime);
        checkCorrectness(priimek);
        this.ime = ime;
        this.priimek = priimek;
    }

    public String getIme() {
        return ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public String getEmso() {
        return emso;
    }


    private void checkCorrectness(String string) {
        String[] splitString = string.split(" ");
        if (splitString.length > 2) {
            throw new InvalidDataException();
        }
        for (int i = 0; i < splitString.length; i++) {
            if (!splitString[i].matches(nameRegEx)) {
                throw new InvalidDataException();
            }
        }
    }
}
