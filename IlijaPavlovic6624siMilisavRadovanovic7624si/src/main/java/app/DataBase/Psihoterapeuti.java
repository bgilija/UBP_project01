package app.DataBase;

import java.time.LocalDate;

public class Psihoterapeuti {
    private String jmbg;
    private String ime;
    private String prezime;
    private LocalDate datumRodjenja;
    private String prebivaliste;
    private String brTelefona;
    private String email;
    private String stepenStudija;

    private boolean psiholog;
    private LocalDate datumSertifikacije;
    private  String oblastSertifikata;
    private String nazivFakulteta;

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
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

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getPrebivaliste() {
        return prebivaliste;
    }

    public void setPrebivaliste(String prebivaliste) {
        this.prebivaliste = prebivaliste;
    }

    public String getBrTelefona() {
        return brTelefona;
    }

    public void setBrTelefona(String brTelefona) {
        this.brTelefona = brTelefona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStepenStudija() {
        return stepenStudija;
    }

    public void setStepenStudija(String stepenStudija) {
        this.stepenStudija = stepenStudija;
    }

    public boolean isPsiholog() {
        return psiholog;
    }

    public void setPsiholog(boolean psiholog) {
        this.psiholog = psiholog;
    }

    public LocalDate getDatumSertifikacije() {
        return datumSertifikacije;
    }

    public void setDatumSertifikacije(LocalDate datumSertifikacije) {
        this.datumSertifikacije = datumSertifikacije;
    }

    public String getOblastSertifikata() {
        return oblastSertifikata;
    }

    public void setOblastSertifikata(String oblastSertifikata) {
        this.oblastSertifikata = oblastSertifikata;
    }

    public String getNazivFakulteta() {
        return nazivFakulteta;
    }

    public void setNazivFakulteta(String nazivFakulteta) {
        this.nazivFakulteta = nazivFakulteta;
    }
}
