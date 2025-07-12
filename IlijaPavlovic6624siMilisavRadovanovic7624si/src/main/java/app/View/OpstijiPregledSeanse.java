package app.View;

import app.App;
import app.Model.SeansaPrikaz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

public class OpstijiPregledSeanse extends VBox {
    private int seansaId;
    private Button btnBack;
    private Label lblNaslov;
    private TableView<SeansaPrikaz> tv;
    private ObservableList<SeansaPrikaz> ol;

    public OpstijiPregledSeanse(int seansaID) {
        this.seansaId = seansaID;
        napraviElemente();
        dodajElemente();
        dodajAkcije();
        ucitajSeansu();
    }

    private void napraviElemente() {
        btnBack = new Button("Back");
        btnBack.setFont(new Font(15));
        ol = FXCollections.observableArrayList();
        tv = new TableView<>(ol);
        tv.setMinWidth(700);

        TableColumn<SeansaPrikaz, String> colNaziv = new TableColumn<>("Naziv testa");
        colNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));

        TableColumn<SeansaPrikaz, String> colOblast = new TableColumn<>("Oblast");
        colOblast.setCellValueFactory(new PropertyValueFactory<>("oblast"));

        TableColumn<SeansaPrikaz, Integer> colCena = new TableColumn<>("Cena");
        colCena.setCellValueFactory(new PropertyValueFactory<>("cena"));

        TableColumn<SeansaPrikaz, String> colRez = new TableColumn<>("Rezultati");
        colRez.setCellValueFactory(new PropertyValueFactory<>("rezultat"));

        tv.getColumns().addAll(colNaziv, colOblast, colCena, colRez);

        lblNaslov = new Label("Opštiji pregled seanse");
        lblNaslov.setFont(new Font(18));
    }

    private void dodajElemente() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15));
        this.setSpacing(20);
        this.setMinWidth(800);
        this.setMinHeight(500);
        this.getChildren().addAll(lblNaslov, tv, btnBack);
    }

    private void dodajAkcije() {
        btnBack.setOnAction(e -> {
            Scene scene = new Scene(new PregledSeansi());
            App.stage.setScene(scene);
        });
    }

    private void ucitajSeansu() {
        try (Connection conn = DatabaseConnector.connect()) {

            String upit = """
                    SELECT s.seansa_id, s.datum_seanse, s.vreme_seanse, s.trajanje_u_minutima, 
                           s.br_seanse, s.beleska, 
                           kl.klijent_jmbg, kl.ime AS ime_klijenta, kl.prezime AS prezime_klijenta,
                           ka.jmbg AS jmbg_kandidata, ka.ime AS ime_kandidata, ka.prezime AS prezime_kandidata,
                           pt.psiholoski_test_id, pt.naziv, pt.oblast, pt.cena, pt.rezultati_testa
                    FROM Seansa s
                    JOIN Klijent kl ON s.Klijent_klijent_jmbg = kl.klijent_jmbg
                    JOIN Kandidat ka ON s.Kandidat_jmbg = ka.jmbg
                    LEFT JOIN Psiholoski_test pt ON s.seansa_id = pt.Seansa_seansa_id
                    WHERE s.seansa_id = ?
                    """;

            try (PreparedStatement stmt = conn.prepareStatement(upit)) {
                stmt.setInt(1, seansaId);
                ResultSet rs = stmt.executeQuery();

                boolean first = true;
                while (rs.next()) {
                    SeansaPrikaz sp = new SeansaPrikaz();
                    sp.setSeansaId(rs.getInt("seansa_id"));
                    sp.setDatum(rs.getDate("datum_seanse").toLocalDate());
                    sp.setVreme(rs.getTime("vreme_seanse").toLocalTime());
                    sp.setTrajanje(rs.getInt("trajanje_u_minutima"));
                    sp.setBroj(rs.getInt("br_seanse"));
                    sp.setBeleska(rs.getString("beleska"));
                    sp.setJmbgKlijenta(rs.getInt("klijent_jmbg"));
                    sp.setImeKlijenta(rs.getString("ime_klijenta"));
                    sp.setPrezimeKlijenta(rs.getString("prezime_klijenta"));
                    sp.setJmbgKandidata(rs.getString("jmbg_kandidata"));
                    sp.setImeKandidata(rs.getString("ime_kandidata"));
                    sp.setPrezimeKandidata(rs.getString("prezime_kandidata"));

                    if (first) {
                        lblNaslov.setText(
                                "Seansa #" + seansaId +
                                        "\n Klijent: " + sp.getImeKlijenta() + " " + sp.getPrezimeKlijenta() +
                                        "\n Terapeut: " + sp.getImeKandidata() + " " + sp.getPrezimeKandidata() +
                                        "\n Datum: " + sp.getDatum() +
                                        "\n Vreme: " + sp.getVreme() +
                                        "\n Trajanje: " + sp.getTrajanje() + " min"+
                                        "\n Beleska: " + sp.getBeleska()
                        );
                        first = false;
                    }

                    if (rs.getString("naziv") != null) { // ako postoji test
                        sp.setTestId(rs.getInt("psiholoski_test_id"));
                        sp.setNaziv(rs.getString("naziv"));
                        sp.setOblast(rs.getString("oblast"));
                        sp.setCena(rs.getInt("cena"));
                        sp.setRezultat(rs.getString("rezultati_testa"));
                        ol.add(sp);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
