package app.View;

import app.App;
import app.DataBase.Klijent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PPrijavaNovihKlijenataKodPrijavljenogPsihoterapeuta extends VBox {
    //Mozda nije lepo napravljena foram treba pregledati opet tekst specifikacije
    private String jmbg;
    private Label lbNaslov;
    private ObservableList<Klijent> olNoviKlijenti;
    private TableView twNoviKlijenti;
    private Button btnBack;



    public PPrijavaNovihKlijenataKodPrijavljenogPsihoterapeuta(String jmbg){
        this.jmbg = jmbg;
        napraviElemente();
        dodajElemente();
        dodajAkcije();
        ucitajKlijente();
    }

    private void napraviElemente()
    {
        olNoviKlijenti = FXCollections.observableArrayList();
        twNoviKlijenti = new TableView<>();

        btnBack=new Button("Back");
        lbNaslov = new Label("Pregled novih klijenata");
        lbNaslov.setFont(new Font(22));
        twNoviKlijenti.setMinWidth(550);
        twNoviKlijenti = new TableView<>(olNoviKlijenti);


        TableColumn<Klijent, String> colJmbg = new TableColumn<>("JMBG");
        colJmbg.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJmbg()));

        TableColumn<Klijent, String> colIme = new TableColumn<>("Ime");
        colIme.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIme()));

        TableColumn<Klijent, String> colPrezime = new TableColumn<>("Prezime");
        colPrezime.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrezime()));

        TableColumn<Klijent, String> colDatumRodjenja = new TableColumn<>("Datum rođenja");
        colDatumRodjenja.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDatumRodjenja()));

        TableColumn<Klijent, String> colPol = new TableColumn<>("Pol");
        colPol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPol())));

        TableColumn<Klijent, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Klijent, String> colBrTelefona = new TableColumn<>("Broj telefona");
        colBrTelefona.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBrTelefona()));

        TableColumn<Klijent, String> colRanije = new TableColumn<>("Ranije posete");
        colRanije.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isRanijePoseteTerapeutima() ? "Da" : "Ne"));

        TableColumn<Klijent, String> colOpis = new TableColumn<>("Opis problema");
        colOpis.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUkratkoObjasnjenjeProblema()));

        twNoviKlijenti.getColumns().addAll(
                colJmbg, colIme, colPrezime, colDatumRodjenja, colPol,
                colEmail, colBrTelefona, colRanije, colOpis
        );


    }

    private void dodajElemente()
    {

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15,10,15,10));
        this.setSpacing(15);
        this.setMinWidth(650);
        this.setMinHeight(500);
        this.getChildren().addAll(lbNaslov, twNoviKlijenti, btnBack);
    }

    private void dodajAkcije()
    {
        btnBack.setOnAction(e->{
            Scene scene = new Scene(new IzborPregleda(jmbg));
            App.stage.setScene(scene);
        });
    }
    private void ucitajKlijente() {
        olNoviKlijenti.clear();
        try (Connection conn = DatabaseConnector.connect()) {
            String sql = """
                SELECT k.klijent_jmbg, k.ime, k.prezime, k.datum_rodjenja, k.pol, k.email, k.br_telefona, 
                       k.ranije_posete_psihoterapeutima, k.ukratko_objasnjenje_problema
                FROM Klijent k
                JOIN Seansa s ON k.klijent_jmbg = s.Klijent_klijent_jmbg
                WHERE s.br_seanse = 1
                  AND s.datum_seanse > CURRENT_DATE
                  AND s.Kandidat_jmbg = ?
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, jmbg);  // jmbg prijavljenog kandidata
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Klijent klijent = new Klijent(
                        rs.getString("klijent_jmbg"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getDate("datum_rodjenja").toString(),
                        rs.getString("pol").charAt(0),
                        rs.getString("email"),
                        rs.getString("br_telefona"),
                        rs.getBoolean("ranije_posete_psihoterapeutima"),
                        rs.getString("ukratko_objasnjenje_problema")
                );
                olNoviKlijenti.add(klijent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}
