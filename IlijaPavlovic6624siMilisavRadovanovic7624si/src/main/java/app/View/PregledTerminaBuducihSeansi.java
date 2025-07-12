package app.View;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class PregledTerminaBuducihSeansi extends  VBox{

        private String jmbg;
        private Label lbNaslov;

        private Button btnBack;
        private ObservableList<app.Model.SeansaPrikaz> olSenase;
        private TableView twSeanse;


        public PregledTerminaBuducihSeansi(String jmbg){
            this.jmbg = jmbg;
            napraviElemente();
            dodajElemente();
            dodajAkcije();
            ucitajSeanse();
        }

        private void napraviElemente()
        {
            olSenase = FXCollections.observableArrayList();
            lbNaslov = new Label("Pregled buducih seansi");
            lbNaslov.setFont(Font.font(20));
            btnBack = new Button("Back");

            twSeanse = new TableView<>(olSenase);
            twSeanse.setMinWidth(500);


            TableColumn<app.Model.SeansaPrikaz, Integer> colId = new TableColumn<>("ID Seanse");
            colId.setCellValueFactory(new PropertyValueFactory<>("seansaId"));

            TableColumn<app.Model.SeansaPrikaz, LocalDate> colDatum = new TableColumn<>("Datum");
            colDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));

            TableColumn<app.Model.SeansaPrikaz, LocalTime> colVreme = new TableColumn<>("Vreme");
            colVreme.setCellValueFactory(new PropertyValueFactory<>("vreme"));

            TableColumn<app.Model.SeansaPrikaz, Integer> colTrajanje = new TableColumn<>("Trajanje (min)");
            colTrajanje.setCellValueFactory(new PropertyValueFactory<>("trajanje"));

            TableColumn<app.Model.SeansaPrikaz, String> colIme = new TableColumn<>("Ime klijenta");
            colIme.setCellValueFactory(new PropertyValueFactory<>("imeKlijenta"));

            TableColumn<app.Model.SeansaPrikaz, String> colPrezime = new TableColumn<>("Prezime klijenta");
            colPrezime.setCellValueFactory(new PropertyValueFactory<>("prezimeKlijenta"));

            TableColumn<app.Model.SeansaPrikaz, String> colBeleske = new TableColumn<>("Beleske");
            colBeleske.setCellValueFactory(new PropertyValueFactory<>("beleska"));

            TableColumn<app.Model.SeansaPrikaz, String> colBrSeanse = new TableColumn<>("Broj seanse");
            colBrSeanse.setCellValueFactory(new PropertyValueFactory<>("broj"));

            twSeanse.getColumns().addAll(colId, colDatum, colVreme, colTrajanje, colIme, colPrezime, colBeleske, colBrSeanse);

        }

        private void dodajElemente()
        {
            this.setMinWidth(650);
            this.setMinHeight(500);
            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(15,10,15,10));
            this.setSpacing(15);
            this.getChildren().addAll(lbNaslov, twSeanse,btnBack);
        }

        private void dodajAkcije()
        {
            btnBack.setOnAction(e -> {
                Scene scene = new Scene(new IzborPregleda(jmbg));
                App.stage.setScene(scene);
            });

        }
        private void ucitajSeanse() {
            ObservableList<app.Model.SeansaPrikaz> lista = FXCollections.observableArrayList();

            String upit = "SELECT s.seansa_id, s.datum_seanse, s.vreme_seanse, " +
                    "s.trajanje_u_minutima, s.br_seanse, s.beleska, " +
                    "k.ime AS imeKlijenta, k.prezime AS prezimeKlijenta " +
                    "FROM Seansa s " +
                    "JOIN Klijent k ON s.Klijent_klijent_jmbg = k.klijent_jmbg " +
                    "WHERE s.Kandidat_jmbg = ? AND s.datum_seanse > CURRENT_DATE";

            try (Connection conn = DatabaseConnector.connect();
                 PreparedStatement stmt = conn.prepareStatement(upit)) {

                stmt.setString(1, this.jmbg); // jmbg ulogovanog kandidata
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    app.Model.SeansaPrikaz sp = new app.Model.SeansaPrikaz();
                    sp.setSeansaId(rs.getInt("seansa_id"));
                    sp.setDatum(rs.getDate("datum_seanse").toLocalDate());
                    sp.setVreme(rs.getTime("vreme_seanse").toLocalTime());
                    sp.setTrajanje(rs.getInt("trajanje_u_minutima"));
                    sp.setBroj(rs.getInt("br_seanse"));
                    sp.setBeleska(rs.getString("beleska"));

                    sp.setImeKlijenta(rs.getString("imeKlijenta"));
                    sp.setPrezimeKlijenta(rs.getString("prezimeKlijenta"));

                    lista.add(sp);
                }

                twSeanse.setItems(lista);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }





}
