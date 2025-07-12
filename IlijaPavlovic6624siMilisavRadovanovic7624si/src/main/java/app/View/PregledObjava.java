package app.View;

import app.App;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

import java.sql.*;

public class PregledObjava extends VBox {

    private int seansaId;
    private Label lblKandidat;
    private Label lblKlijent;
    private Label lblDatum;
    private Label lblVreme;
    private Label lblObjava;
    private Label lblKome;
    private Label lblDatumObjave;
    private Button btnBack;

    public PregledObjava(int seansaId) {
        this.seansaId = seansaId;
        napraviElemente();
        dodajElemente();
        dodajAkciju();
        ucitajPodatkeIzBaze();
    }

    private void napraviElemente() {
        lblDatumObjave=new Label();
        lblKome = new Label();
        lblKandidat = new Label();
        lblKlijent = new Label();
        lblDatum = new Label();
        lblVreme = new Label();
        lblObjava = new Label();
        btnBack=new Button("Back");
        Font f = new Font(16);
        lblKandidat.setFont(f);
        lblKlijent.setFont(f);
        lblDatum.setFont(f);
        lblVreme.setFont(f);
        lblObjava.setFont(f);
        lblKome.setFont(f);
        lblDatumObjave.setFont(f);

        this.setSpacing(10);
        this.setPadding(new Insets(20));
    }

    private void dodajElemente() {
        this.getChildren().addAll(lblKandidat, lblKlijent, lblDatum, lblVreme, lblObjava,lblDatumObjave,lblKome, btnBack);
    }

    private void dodajAkciju()
    {
        btnBack.setOnAction(e -> {
            Scene scene = new Scene(new PregledSeansi());
            App.stage.setScene(scene);
        });
    }

    private void ucitajPodatkeIzBaze() {
        String query = "SELECT s.datum_seanse, s.vreme_seanse, " +
                "k.ime AS imeKlijenta, k.prezime AS prezimeKlijenta, " +
                "ka.ime AS imeKandidata, ka.prezime AS prezimeKandidata, " +
                "o.svrha_objave, o.datum_objave, o.kome_je_objavljena " +
                "FROM Seansa s " +
                "JOIN Klijent k ON s.Klijent_klijent_jmbg = k.klijent_jmbg " +
                "JOIN Kandidat ka ON s.Kandidat_jmbg = ka.jmbg " +
                "LEFT JOIN Objava_podataka_seanse o ON s.seansa_id = o.Seansa_seansa_id " +
                "WHERE s.seansa_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, seansaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               // System.out.println("PregledObjava otvoren za seansa_id: " + seansaId);

                lblDatum.setText("Datum seanse: " + rs.getDate("datum_seanse"));
                lblVreme.setText("Vreme seanse: " + rs.getTime("vreme_seanse"));
                lblKlijent.setText("Klijent: " + rs.getString("imeKlijenta") + " " + rs.getString("prezimeKlijenta"));
                lblKandidat.setText("Psihoterapeut: " + rs.getString("imeKandidata") + " " + rs.getString("prezimeKandidata"));


                String opis = rs.getString("svrha_objave");
                String kome = rs.getString("kome_je_objavljena");
                String kada = rs.getString("datum_objave");
                if (opis != null) {
                    lblObjava.setText("Svrha objave: " + opis);
                    lblDatumObjave.setText("Datum objave: "+kada);
                    lblKome.setText("Kome: "+kome);
                } else {
                    lblObjava.setText("Objava ne postoji.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
