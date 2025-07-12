package app.View;


import app.App;
import app.DataBase.StatistikaRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatistikaForma extends VBox {

    private TableView<StatistikaRecord> table;
    ObservableList<StatistikaRecord> podaci;
    private Button btnBack;

    public StatistikaForma() {

        napraviElemente();
        dodajElemente();
        dodajAkcije();
        ucitajPlacanja();



    }
    private void ucitajPlacanja(){
        podaci = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnector.connect()) {
            String sql = """
                        SELECT
                                  k.jmbg,
                                  k.ime,
                                  COUNT(s.seansa_id) AS broj_seansi,
                                  ROUND(AVG(cp.cena_u_dinarima * s.trajanje_u_minutima / 60), 2) AS prosecna_cena,
                                  pv.najcesca_valuta
                              FROM Kandidat k
                              JOIN Seansa s ON k.jmbg = s.Kandidat_jmbg
                              LEFT JOIN Cena_po_satu cp ON s.seansa_id = cp.Seansa_seansa_id
                              LEFT JOIN (
                                  SELECT jmbg_kandidata, Valuta_sifra_valute AS najcesca_valuta
                                  FROM (
                                      SELECT\s
                                          k2.jmbg AS jmbg_kandidata,
                                          p.Valuta_sifra_valute,
                                          ROW_NUMBER() OVER (
                                              PARTITION BY k2.jmbg
                                              ORDER BY COUNT(*) DESC
                                          ) AS rn
                                      FROM Seansa s2
                                      JOIN Kandidat k2 ON s2.Kandidat_jmbg = k2.jmbg
                                      JOIN Placanje p ON p.Seansa_seansa_id = s2.seansa_id
                                      GROUP BY k2.jmbg, p.Valuta_sifra_valute
                                  ) pod
                                  WHERE rn = 1
                              ) pv ON pv.jmbg_kandidata = k.jmbg
                              GROUP BY k.jmbg, k.ime, pv.najcesca_valuta
                              ORDER BY broj_seansi DESC;
                              
""";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                podaci.add(new StatistikaRecord(
                        rs.getString("jmbg"),
                        rs.getString("ime"),
                        rs.getInt("broj_seansi"),
                        rs.getDouble("prosecna_cena"),
                        rs.getString("najcesca_valuta")
                ));
            }

            table.setItems(podaci);

        } catch (Exception e) {
            e.printStackTrace();
            this.getChildren().add(new Label("Došlo je do greške prilikom učitavanja podataka."));
        }


    }

    private void napraviElemente()
    {
        table = new TableView<>();

        btnBack = new Button("Back");
        TableColumn<StatistikaRecord, String> colJmbg = new TableColumn<>("JMBG");
        colJmbg.setCellValueFactory(new PropertyValueFactory<>("jmbg"));

        TableColumn<StatistikaRecord, String> colIme = new TableColumn<>("Ime");
        colIme.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<StatistikaRecord, Integer> colBroj = new TableColumn<>("Broj seansi");
        colBroj.setCellValueFactory(new PropertyValueFactory<>("brojSeansi"));

        TableColumn<StatistikaRecord, Double> colProsecna = new TableColumn<>("Prosečna cena (RSD)");
        colProsecna.setCellValueFactory(new PropertyValueFactory<>("prosecnaCena"));

        TableColumn<StatistikaRecord, String> colValuta = new TableColumn<>("Najčešća valuta");
        colValuta.setCellValueFactory(new PropertyValueFactory<>("najcescaValuta"));

        table.getColumns().addAll(colJmbg, colIme, colBroj, colProsecna, colValuta);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    private void dodajElemente()
    {
        this.getChildren().addAll(table,btnBack);
        this.setSpacing(10);
        this.setPadding(new Insets(15));
    }



    private void dodajAkcije()
    {btnBack.setOnAction(e->{
        Scene scene=new Scene(new Informacije());
        App.stage.setScene(scene);
    });}
}
