package app.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class SeansaPrikaz {
    private int seansaId;
    private LocalDate datum;
    private LocalTime vreme;
    private int trajanje;
    private int broj;
    private String beleska;
    private int jmbgKlijenta;
    private String jmbgKandidata;
    private String imeKandidata;
    private String prezimeKandidata;
    private String imeKlijenta;
    private String prezimeKlijenta;
    private int testId;
    private String naziv;
    private String oblast;
    private int cena;
    private String rezultat;


    // Konstruktor bez parametara

    public SeansaPrikaz() {
    }
    // Konstruktor sa svim poljima

    public SeansaPrikaz(int seansaId, LocalDate datum, LocalTime vreme, int trajanje,
                        int broj, String beleska, int jmbgKlijenta, String jmbgKandidata,
                        String imeKandidata, String prezimeKandidata) {
        this.seansaId = seansaId;
        this.datum = datum;
        this.vreme = vreme;
        this.trajanje = trajanje;
        this.broj = broj;
        this.beleska = beleska;
        this.jmbgKlijenta = jmbgKlijenta;
        this.jmbgKandidata = jmbgKandidata;
        this.imeKandidata = imeKandidata;
        this.prezimeKandidata = prezimeKandidata;
    }
    // Getteri i setteri


    public int getSeansaId() {
        return seansaId;
    }

    public void setSeansaId(int seansaId) {
        this.seansaId = seansaId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getVreme() {
        return vreme;
    }

    public void setVreme(LocalTime vreme) {
        this.vreme = vreme;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public String getBeleska() {
        return beleska;
    }

    public void setBeleska(String beleska) {
        this.beleska = beleska;
    }

    public int getJmbgKlijenta() {
        return jmbgKlijenta;
    }

    public void setJmbgKlijenta(int jmbgKlijenta) {
        this.jmbgKlijenta = jmbgKlijenta;
    }

    public String getJmbgKandidata() {
        return jmbgKandidata;
    }

    public void setJmbgKandidata(String jmbgKandidata) {
        this.jmbgKandidata = jmbgKandidata;
    }

    public String getImeKandidata() {
        return imeKandidata;
    }

    public void setImeKandidata(String imeKandidata) {
        this.imeKandidata = imeKandidata;
    }

    public String getPrezimeKandidata() {
        return prezimeKandidata;
    }
    public void setPrezimeKandidata(String prezimeKandidata) {
        this.prezimeKandidata = prezimeKandidata;
    }

    public String getImeKlijenta() {
        return imeKlijenta;
    }

    public void setImeKlijenta(String imeKlijenta) {
        this.imeKlijenta = imeKlijenta;
    }

    public String getPrezimeKlijenta() {
        return prezimeKlijenta;
    }

    public void setPrezimeKlijenta(String prezimeKlijenta) {
        this.prezimeKlijenta = prezimeKlijenta;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }
}
