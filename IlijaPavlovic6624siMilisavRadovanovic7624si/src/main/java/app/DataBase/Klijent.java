package app.DataBase;

import javafx.scene.chart.PieChart;

import java.util.Date;

public class Klijent {
    private String jmbg;
    private String ime;
    private String prezime;
    private String datumRodjenja;
    private char pol;
    private String email;
    private String brTelefona;
    private boolean ranijePoseteTerapeutima;
    private String ukratkoObjasnjenjeProblema;

    public Klijent(String jmbg, String ime, String prezime, String datumRodjenja, char pol, String email, String brTelefona, boolean ranijePoseteTerapeutima, String ukratkoObjasnjenjeProblema) {
        this.jmbg = jmbg;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.pol = pol;
        this.email = email;
        this.brTelefona = brTelefona;
        this.ranijePoseteTerapeutima = ranijePoseteTerapeutima;
        this.ukratkoObjasnjenjeProblema = ukratkoObjasnjenjeProblema;
    }

    public String getIme() {
        return ime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public char getPol() {
        return pol;
    }

    public void setPol(char pol) {
        this.pol = pol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrTelefona() {
        return brTelefona;
    }

    public void setBrTelefona(String brTelefona) {
        this.brTelefona = brTelefona;
    }

    public boolean isRanijePoseteTerapeutima() {
        return ranijePoseteTerapeutima;
    }

    public void setRanijePoseteTerapeutima(boolean ranijePoseteTerapeutima) {
        this.ranijePoseteTerapeutima = ranijePoseteTerapeutima;
    }

    public String getUkratkoObjasnjenjeProblema() {
        return ukratkoObjasnjenjeProblema;
    }

    public void setUkratkoObjasnjenjeProblema(String ukratkoObjasnjenjeProblema) {
        this.ukratkoObjasnjenjeProblema = ukratkoObjasnjenjeProblema;
    }
}
