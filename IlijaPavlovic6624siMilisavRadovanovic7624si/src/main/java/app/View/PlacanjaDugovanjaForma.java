package app.View;
import app.App;
import app.DataBase.PlacanjeDugovanje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlacanjaDugovanjaForma extends VBox {

    private TableView<PlacanjeDugovanje> tabela;
    private Button backButton;
    ObservableList<PlacanjeDugovanje> podaci;

    public PlacanjaDugovanjaForma() {
        napraviElemente();
        dodajElemente();
        dodajAkcije();
        ucitajPlacanja();
    }
    private void napraviElemente()
    {
        tabela = new TableView<>();
        backButton = new Button("Back");
        // Kolone
        TableColumn<PlacanjeDugovanje, Integer> jmbgCol = new TableColumn<>("JMBG");
        jmbgCol.setCellValueFactory(new PropertyValueFactory<>("klijentJmbg"));

        TableColumn<PlacanjeDugovanje, String> imeCol = new TableColumn<>("Ime");
        imeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<PlacanjeDugovanje, String> prezimeCol = new TableColumn<>("Prezime");
        prezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        TableColumn<PlacanjeDugovanje, Integer> seansaIdCol = new TableColumn<>("Seansa ID");
        seansaIdCol.setCellValueFactory(new PropertyValueFactory<>("seansaId"));

        TableColumn<PlacanjeDugovanje, String> datumCol = new TableColumn<>("Datum seanse");
        datumCol.setCellValueFactory(new PropertyValueFactory<>("datumSeanse"));

        TableColumn<PlacanjeDugovanje, Integer> cenaSeanseCol = new TableColumn<>("Cena seanse");
        cenaSeanseCol.setCellValueFactory(new PropertyValueFactory<>("cenaSeanse"));

        TableColumn<PlacanjeDugovanje, Integer> cenaTestovaCol = new TableColumn<>("Cena testova");
        cenaTestovaCol.setCellValueFactory(new PropertyValueFactory<>("cenaTestova"));

        TableColumn<PlacanjeDugovanje, Double> ukupnoCol = new TableColumn<>("Ukupno za platiti");
        ukupnoCol.setCellValueFactory(new PropertyValueFactory<>("ukupnoZaPlatiti"));

        TableColumn<PlacanjeDugovanje, Double> placenoCol = new TableColumn<>("Ukupno plaćeno");
        placenoCol.setCellValueFactory(new PropertyValueFactory<>("ukupnoPlaceno"));

        TableColumn<PlacanjeDugovanje, Double> dugCol = new TableColumn<>("Dugovanje");
        dugCol.setCellValueFactory(new PropertyValueFactory<>("dugovanje"));
        tabela.getColumns().addAll(
                jmbgCol, imeCol, prezimeCol, seansaIdCol, datumCol,
                cenaSeanseCol, cenaTestovaCol, ukupnoCol, placenoCol, dugCol
        );
    }
    private void dodajElemente()
    {
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getChildren().addAll(tabela, backButton);
        this.setMinWidth(900);
    }
    private void dodajAkcije()
    {

        backButton.setOnAction(e -> {
            Scene scene=new Scene(new Informacije());
            App.stage.setScene(scene);
        });
    }

    public TableView<PlacanjeDugovanje> getTabela() {
        return tabela;
    }

    public Button getBackButton() {
        return backButton;
    }
    public void ucitajPlacanja() {
        podaci = FXCollections.observableArrayList();

        String upit = """
       SELECT\s
                                        k.klijent_jmbg,
                                        k.ime,
                                        k.prezime,
                                        s.seansa_id,
                                        s.datum_seanse,
                                        IFNULL(cps.cena_u_dinarima, 0) AS cena_seanse,
                                        IFNULL(pt.cena_testova, 0) AS cena_testova,
                                        IFNULL(ROUND(cps.cena_u_dinarima * s.trajanje_u_minutima / 60, 2), 0) + IFNULL(pt.cena_testova, 0) AS ukupno_za_platiti,
                                        IFNULL(p.ukupno_placeno, 0) AS ukupno_placeno,
                                        (IFNULL(ROUND(cps.cena_u_dinarima * s.trajanje_u_minutima / 60, 2), 0) + IFNULL(pt.cena_testova, 0)) - IFNULL(p.ukupno_placeno, 0) AS dugovanje
                                    FROM Klijent k
                                    JOIN Seansa s ON k.klijent_jmbg = s.Klijent_klijent_jmbg
                                    LEFT JOIN Cena_po_satu cps ON s.seansa_id = cps.Seansa_seansa_id
                                    
                                    -- Cena testova po seansi
                                    LEFT JOIN (
                                        SELECT\s
                                            Seansa_seansa_id,\s
                                            SUM(cena) AS cena_testova
                                        FROM Psiholoski_test
                                        GROUP BY Seansa_seansa_id
                                    ) pt ON s.seansa_id = pt.Seansa_seansa_id
                                    
                                    -- Ukupno plaćeno uz proviziju i kurs
                                    LEFT JOIN (
                                        SELECT\s
                                            p.Seansa_seansa_id,
                                            p.Klijent_klijent_jmbg,
                                            SUM(
                                                CAST(p.iznos AS DECIMAL(10,2))\s
                                                * CASE WHEN v.provizija = TRUE THEN 1.05 ELSE 1 END
                                                * (
                                                    SELECT kv.kurs
                                                    FROM Kurs_valute kv
                                                    WHERE kv.Valuta_sifra_valute = p.Valuta_sifra_valute
                                                      AND kv.datum <= p.datum_placanja
                                                    ORDER BY kv.datum DESC
                                                    LIMIT 1
                                                )
                                            ) AS ukupno_placeno
                                        FROM Placanje p
                                        JOIN Valuta v ON p.Valuta_sifra_valute = v.sifra_valute
                                        GROUP BY p.Seansa_seansa_id, p.Klijent_klijent_jmbg
                                    ) p ON s.seansa_id = p.Seansa_seansa_id AND k.klijent_jmbg = p.Klijent_klijent_jmbg
                                    
                                    ORDER BY s.datum_seanse DESC;
                                    
       
    """;




        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(upit);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PlacanjeDugovanje pd = new PlacanjeDugovanje(
                        rs.getInt("klijent_jmbg"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getInt("seansa_id"),
                        rs.getDate("datum_seanse").toString(),
                        rs.getInt("cena_seanse"),
                        rs.getInt("cena_testova"),
                        rs.getDouble("ukupno_za_platiti"),
                        rs.getDouble("ukupno_placeno"),
                        rs.getDouble("dugovanje")
                );
                podaci.add(pd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        tabela.setItems(podaci);
    }

}
