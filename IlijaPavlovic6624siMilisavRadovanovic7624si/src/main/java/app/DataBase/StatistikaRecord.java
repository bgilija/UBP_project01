package app.DataBase;


public class StatistikaRecord {
    private String jmbg;
    private String ime;
    private int brojSeansi;
    private double prosecnaCena;
    private String najcescaValuta;

    public StatistikaRecord(String jmbg, String ime, int brojSeansi, double prosecnaCena, String najcescaValuta) {
        this.jmbg = jmbg;
        this.ime = ime;
        this.brojSeansi = brojSeansi;
        this.prosecnaCena = prosecnaCena;
        this.najcescaValuta = najcescaValuta;
    }

    public String getJmbg() {
        return jmbg;
    }

    public String getIme() {
        return ime;
    }

    public int getBrojSeansi() {
        return brojSeansi;
    }

    public double getProsecnaCena() {
        return prosecnaCena;
    }

    public String getNajcescaValuta() {
        return najcescaValuta;
    }
}
