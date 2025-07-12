package app.View;


import app.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SignUp  extends VBox {
    //psihoterapeut ima: psihoterapeut id, ime, prezime, mail ,da li je psiholog,
    // koji sertifikat ima* , broj telefona, datum rodjenja
//map<korisnicko ime, sifra
    private GridPane gridpane;
    private Label lblSignUp;
    private Label lbPrezime;
    private Label lbbrTelefona;

    private Label lbIme;
    private Label lbMail;
    private Label lbDatumRodjenja;
    private Label lbSifra;
    private  Label lbFakultet;
    private Label lbPsiholog;
    private Label lbDatumSertifikacije;
    private  Label lbOblast;
    private Label lbStepenStudija;
    private Label lbPrebivaliste;
    private Label lblJMBG;
    private TextField tfPrezime;
    private TextField tfbrTelefona;
    private  TextField tfIme;
    private TextField tfMail;
    private TextField tfDatumRodjenja;

    private PasswordField tfSifra;
    private Button btnSignUp;
    private TextField tfJMBG;
    private ComboBox<String> cbStepenStudija;

    private ComboBox<String> cbfakultet;
    private TextField tfOblast;
    private TextField tfDatumsertifikacije;
    private  TextField tfPrebivaliste;
    private CheckBox cbPsiholog;


    public SignUp(){
        napraviElemente();
        dodajElemente();
        dodajAkcije();
    }

    private void napraviElemente()
    {
        lblSignUp=new Label("Sign up");
        lbbrTelefona=new Label("Broj telefona: ");
        lbSifra=new Label("Sifra:");
        lbDatumRodjenja=new Label("Datum rodjenja:");
        lbIme=new Label("Ime:");
        lbPrezime  = new Label("Prezime:");
        lbMail = new Label("E-mail:");
        tfMail=new TextField();
        tfDatumRodjenja=new TextField();
        tfIme = new TextField();
        tfPrezime = new TextField();
        tfbrTelefona = new TextField();
        tfSifra = new PasswordField();
        btnSignUp = new Button("Sign up");
        lblJMBG = new Label("JBMG:");
        lbDatumSertifikacije = new Label("Datum sertifikacije:");
        lbFakultet = new Label("Fakultet:");
        lbStepenStudija = new Label("Stepen studija:");
        lbPsiholog = new Label("Psiholog:");
        lbOblast = new Label("Oblast:");
        cbfakultet = new ComboBox<>();
        tfJMBG = new TextField();
        tfOblast = new TextField();
        cbStepenStudija = new ComboBox<>();
        cbStepenStudija.getItems().add(0, "Osnovne");
        cbStepenStudija.getItems().add(1, "Master");
        cbStepenStudija.getItems().add(2, "Doktorske");
        tfDatumsertifikacije = new TextField();
        tfDatumsertifikacije.setPromptText("YYYY-MM-DD");
        tfDatumRodjenja.setPromptText("YYYY-MM-DD");
        cbPsiholog = new CheckBox();
        lbPrebivaliste = new Label("Prebivaliste:");
        tfPrebivaliste = new TextField();


        gridpane = new GridPane();


    }

    private void dodajElemente()
    {
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        gridpane.addColumn(0,lbIme, lbPrezime, lbbrTelefona, lbMail, lbDatumRodjenja, lblJMBG, lbFakultet, lbStepenStudija,lbDatumSertifikacije, lbOblast, lbPsiholog, lbPrebivaliste, lbSifra);
        gridpane.addColumn(1,tfIme, tfPrezime, tfbrTelefona, tfMail, tfDatumRodjenja, tfJMBG, cbfakultet, cbStepenStudija, tfDatumsertifikacije, tfOblast, cbPsiholog, tfPrebivaliste, tfSifra);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15,10,15,10));
        this.setSpacing(10);
        this.getChildren().addAll(lblSignUp,gridpane,btnSignUp);
        popuniComboBoxFakultetima();
    }
    private void dodajAkcije()
    {

        btnSignUp.setOnAction(e -> {
            try (Connection conn = DatabaseConnector.connect()) {

                // INSERT u tabelu Kandidat
                String insertKandidatSQL = "INSERT INTO Kandidat (jmbg, ime, prezime, datum_rodjenja, prebivaliste, br_telefona, email, stepen_studija, sifra_naloga, psiholog, Fakultet_fakultet_id, Centar_za_obuku_centar_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement kandidatStmt = conn.prepareStatement(insertKandidatSQL);

                kandidatStmt.setString(1, tfJMBG.getText());
                kandidatStmt.setString(2, tfIme.getText());
                kandidatStmt.setString(3, tfPrezime.getText());
                kandidatStmt.setDate(4, java.sql.Date.valueOf(tfDatumRodjenja.getText())); // format: yyyy-mm-dd
                kandidatStmt.setString(5, tfPrebivaliste.getText());
                kandidatStmt.setString(6, tfbrTelefona.getText());
                kandidatStmt.setString(7, tfMail.getText());
                kandidatStmt.setString(8, cbStepenStudija.getValue());
                kandidatStmt.setString(9, tfSifra.getText());
                kandidatStmt.setBoolean(10, cbPsiholog.isSelected());

                // Preuzimamo ID fakulteta iz naziva
                int fakultetId = getFakultetIdByNaziv(cbfakultet.getValue(), conn);
                kandidatStmt.setInt(11, fakultetId);

                // Privremeno postavi centar obuke ID na 1
                kandidatStmt.setInt(12, 1);

                kandidatStmt.executeUpdate();

                // INSERT u tabelu Sertifikat
                String insertSertifikatSQL = "INSERT INTO Sertifikat (datum_sertifikacije, oblast_psihoterapije, Kandidat_jmbg) VALUES (?, ?, ?)";
                PreparedStatement sertifikatStmt = conn.prepareStatement(insertSertifikatSQL);
                sertifikatStmt.setDate(1, java.sql.Date.valueOf(tfDatumsertifikacije.getText())); // format: yyyy-mm-dd
                sertifikatStmt.setString(2, tfOblast.getText());
                sertifikatStmt.setString(3, tfJMBG.getText());

                sertifikatStmt.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Uspeh", "Uspešno ste se registrovali!");
                clearForm();

            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Greška", "Došlo je do greške pri registraciji.");
            }

            Scene scene = new Scene(new PocetniEkran());
            App.stage.setScene(scene);
        });


    }



    private void popuniComboBoxFakultetima() {
        List<String> fakulteti = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT naziv FROM fakultet");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                fakulteti.add(rs.getString("naziv"));
            }

            cbfakultet.getItems().addAll(fakulteti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clearForm() {
        tfIme.clear();
        tfPrezime.clear();
        tfbrTelefona.clear();
        tfMail.clear();
        tfDatumRodjenja.clear();
        tfJMBG.clear();
        tfSifra.clear();
        tfDatumsertifikacije.clear();
        tfOblast.clear();
        tfPrebivaliste.clear();
        cbPsiholog.setSelected(false);
        cbfakultet.getSelectionModel().clearSelection();
        cbStepenStudija.getSelectionModel().clearSelection();
    }
    private int getFakultetIdByNaziv(String naziv, Connection conn) throws Exception {
        String sql = "SELECT fakultet_id FROM fakultet WHERE naziv = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, naziv);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("fakultet_id");
            } else {
                throw new Exception("Fakultet nije pronađen u bazi.");
            }
        }
    }



}
