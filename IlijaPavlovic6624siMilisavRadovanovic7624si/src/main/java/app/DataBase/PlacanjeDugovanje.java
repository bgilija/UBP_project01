package app.DataBase;

public class PlacanjeDugovanje {
    private int klijentJmbg;
    private String ime;
    private String prezime;
    private int seansaId;
    private String datumSeanse;
    private int cenaSeanse;
    private int cenaTestova;
    private double ukupnoZaPlatiti;
    private double ukupnoPlaceno;
    private double dugovanje;

    public PlacanjeDugovanje(int klijentJmbg, String ime, String prezime, int seansaId, String datumSeanse,
                             int cenaSeanse, int cenaTestova, double ukupnoZaPlatiti,
                             double ukupnoPlaceno, double dugovanje) {
        this.klijentJmbg = klijentJmbg;
        this.ime = ime;
        this.prezime = prezime;
        this.seansaId = seansaId;
        this.datumSeanse = datumSeanse;
        this.cenaSeanse = cenaSeanse;
        this.cenaTestova = cenaTestova;
        this.ukupnoZaPlatiti = ukupnoZaPlatiti;
        this.ukupnoPlaceno = ukupnoPlaceno;
        this.dugovanje = dugovanje;
    }

    // Getteri i setteri

    public int getKlijentJmbg() { return klijentJmbg; }
    public String getIme() { return ime; }
    public String getPrezime() { return prezime; }
    public int getSeansaId() { return seansaId; }
    public String getDatumSeanse() { return datumSeanse; }
    public int getCenaSeanse() { return cenaSeanse; }
    public int getCenaTestova() { return cenaTestova; }
    public double getUkupnoZaPlatiti() { return ukupnoZaPlatiti; }
    public double getUkupnoPlaceno() { return ukupnoPlaceno; }
    public double getDugovanje() { return dugovanje; }
}
