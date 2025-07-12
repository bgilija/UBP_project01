package app.View;

import app.App;
import app.DataBase.Psihoterapeuti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalDate;

public class PPPshihoterapeuti extends VBox {
    private Label labela;
    private TableView tvPsihoterapeuti;
    private Button btnPocetna;

    private ObservableList<Psihoterapeuti> olPsihoterapeuti;
    private HBox hBox;

    public PPPshihoterapeuti() {
        napraviElemente();
        ucitajPodatke();
        dodajElemente();
        dodajAkcije();
        System.out.println("Broj redova: " + olPsihoterapeuti.size());
    }

    private void napraviElemente() {
        labela = new Label("Pregled podataka o psihoterapeutima Savetovališta");

        btnPocetna = new Button("Back");
        olPsihoterapeuti = FXCollections.observableArrayList();
        tvPsihoterapeuti = new TableView<>(olPsihoterapeuti);
        tvPsihoterapeuti.setMinWidth(900);

        TableColumn<Psihoterapeuti, String> colIme = new TableColumn<>("Ime");
        colIme.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Psihoterapeuti, String> colPrezime = new TableColumn<>("Prezime");
        colPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        TableColumn<Psihoterapeuti, LocalDate> colDatumRodj = new TableColumn<>("Datum rođenja");
        colDatumRodj.setCellValueFactory(new PropertyValueFactory<>("datumRodjenja"));

        TableColumn<Psihoterapeuti, String> colPrebivaliste = new TableColumn<>("Prebivalište");
        colPrebivaliste.setCellValueFactory(new PropertyValueFactory<>("prebivaliste"));

        TableColumn<Psihoterapeuti, String> colTelefon = new TableColumn<>("Telefon");
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("brTelefona"));

        TableColumn<Psihoterapeuti, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Psihoterapeuti, String> colStepen = new TableColumn<>("Stepen studija");
        colStepen.setCellValueFactory(new PropertyValueFactory<>("stepenStudija"));

        TableColumn<Psihoterapeuti, Boolean> colPsiholog = new TableColumn<>("Psiholog");
        colPsiholog.setCellValueFactory(new PropertyValueFactory<>("psiholog"));

        TableColumn<Psihoterapeuti, LocalDate> colDatumSert = new TableColumn<>("Datum sertifikacije");
        colDatumSert.setCellValueFactory(new PropertyValueFactory<>("datumSertifikacije"));

        TableColumn<Psihoterapeuti, String> colOblast = new TableColumn<>("Oblast sertifikata");
        colOblast.setCellValueFactory(new PropertyValueFactory<>("oblastSertifikata"));

        tvPsihoterapeuti.getColumns().addAll(
                colIme, colPrezime, colDatumRodj, colPrebivaliste, colTelefon, colEmail,
                colStepen, colPsiholog, colDatumSert, colOblast
        );

        hBox = new HBox(5);
        hBox.setPadding(new Insets(15, 10, 10, 15));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btnPocetna);
    }

    private void ucitajPodatke() {
        String upit = "SELECT k.jmbg, k.ime, k.prezime, k.datum_rodjenja, k.prebivaliste, k.br_telefona, " +
                "k.email, k.stepen_studija, k.psiholog, s.datum_sertifikacije, s.oblast_psihoterapije " +
                "FROM Kandidat k " +
                "JOIN Sertifikat s ON k.jmbg = s.Kandidat_jmbg";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(upit);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Psihoterapeuti p = new Psihoterapeuti();
                p.setJmbg(rs.getString("jmbg"));
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                p.setDatumRodjenja(rs.getDate("datum_rodjenja").toLocalDate());
                p.setPrebivaliste(rs.getString("prebivaliste"));
                p.setBrTelefona(rs.getString("br_telefona"));
                p.setEmail(rs.getString("email"));
                p.setStepenStudija(rs.getString("stepen_studija"));
                p.setPsiholog(rs.getBoolean("psiholog"));
                p.setDatumSertifikacije(rs.getDate("datum_sertifikacije").toLocalDate());
                p.setOblastSertifikata(rs.getString("oblast_psihoterapije"));

                olPsihoterapeuti.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dodajElemente() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15, 10, 10, 15));
        this.getChildren().addAll(labela, tvPsihoterapeuti, hBox);
    }

    private void dodajAkcije() {
//        btnSort.setOnAction(e -> {
//            olPsihoterapeuti.sort((p1, p2) -> p1.getPrezime().compareToIgnoreCase(p2.getPrezime()));
//        });

        btnPocetna.setOnAction(e -> {
            Scene scene = new Scene(new Informacije());
            App.stage.setScene(scene);
        });
    }
}
