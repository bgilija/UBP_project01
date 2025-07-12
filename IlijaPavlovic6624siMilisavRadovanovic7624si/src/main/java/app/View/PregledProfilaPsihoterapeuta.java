package app.View;

import app.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

//ime, prezime, id, brojTelefona,fakultet, uze usmerenje, bool psiholog,datum rodjenja, prebivaliste.
public class PregledProfilaPsihoterapeuta extends VBox {
    private Label lblNaslov;
    private String jmbg;
    private Label lbljmbg;
    private Label lblIme;
    private Label lblPrezime;
    private Label lblBrTelefona;
    private Label lblFakultet;
    private Label lblDatumSertifikacije;
    private Label lblOblasSertifikacije;
    private Label lblDatumRodjenja;
    private Label lblEmail;
    private Label lblPrebivaliste;
    private Label lblStepenStudija;
    private Label lblSifra;
    private Label lblPsiholog;
    private Button btnBack;
    public PregledProfilaPsihoterapeuta(String jmbg) {
        //todo poziv iz baze za datog psihoterapeuta
        this.jmbg=jmbg;

        napraviElemente();
        dodajElemente();
        dodajAkcije();
    }

    private void napraviElemente(){
        lblIme=new Label("Ime: ");
        lblPrezime= new Label("Prezime: ");
        lblNaslov=new Label("Informacije o psihoterapeutu: ");
        lbljmbg=new Label("jmbg: "+ this.jmbg);
        lblBrTelefona=new Label("Broj telefona: ");
        lblFakultet=new Label("Fakultet: ");
        lblOblasSertifikacije=new Label("Oblast sertifikacije: ");
        lblDatumSertifikacije=new Label("Datum sertifikacije: ");
        lblDatumRodjenja=new Label("Datum rodjenja: ");
        lblPrebivaliste=new Label("Prebivaliste: ");
        lblEmail=new Label("Email: ");
        lblStepenStudija=new Label("Stepen studija: ");
        lblSifra=new Label("Sifra: ");
        lblPsiholog = new Label("Psiholog: ");
        btnBack = new Button("Back");
        try (Connection conn = DatabaseConnector.connect()) {
            String sql = "SELECT * FROM Kandidat k " +
                    "LEFT JOIN Sertifikat s ON k.jmbg = s.Kandidat_jmbg " +
                    "LEFT JOIN Fakultet f ON k.Fakultet_fakultet_id = f.fakultet_id " +
                    "WHERE k.jmbg = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, jmbg);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                lblIme.setText(lblIme.getText() + rs.getString("ime"));
                lblPrezime.setText(lblPrezime.getText() + rs.getString("prezime"));
                lblDatumRodjenja.setText(lblDatumRodjenja.getText() + rs.getDate("datum_rodjenja").toString());
                lblEmail.setText(lblEmail.getText() + rs.getString("email"));
                lblSifra.setText(lblSifra.getText() + rs.getString("sifra_naloga"));
                lblBrTelefona.setText(lblBrTelefona.getText() + rs.getString("br_telefona"));
                lblStepenStudija.setText(lblStepenStudija.getText() + rs.getString("stepen_studija"));
                lblPrebivaliste.setText(lblPrebivaliste.getText() + rs.getString("prebivaliste"));

                boolean psiholog = rs.getBoolean("psiholog");
                lblPsiholog.setText(lblPsiholog.getText() + (psiholog ? "Da" : "Ne"));

                // Fakultet naziv
                lblFakultet.setText(lblFakultet.getText() + rs.getString("naziv"));

                // Sertifikat
                Date datumSertifikacije = rs.getDate("datum_sertifikacije");
                String oblast = rs.getString("oblast_psihoterapije");

                if (datumSertifikacije != null) {
                    lblDatumSertifikacije.setText(lblDatumSertifikacije.getText() + datumSertifikacije.toString());
                }

                if (oblast != null) {
                    lblOblasSertifikacije.setText(lblOblasSertifikacije.getText() + oblast);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Greška", "Kandidat nije pronađen.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Greška", "Došlo je do greške pri čitanju podataka iz baze.");
        }


    }
    private void dodajElemente(){
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,15,15,10));
        this.setSpacing(15);
        this.getChildren().addAll(lblNaslov,lblIme, lblPrezime, lbljmbg, lblDatumRodjenja, lblEmail, lblSifra , lblBrTelefona, lblFakultet, lblStepenStudija, lblPrebivaliste, lblPsiholog, lblOblasSertifikacije, lblDatumSertifikacije, btnBack);
        this.setMinWidth(400);
        this.setMinHeight(600);
    }
    private void dodajAkcije(){
        btnBack.setOnAction(e -> {
            Scene scene = new Scene(new IzborPregleda(jmbg));
            App.stage.setScene(scene);
        });
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
