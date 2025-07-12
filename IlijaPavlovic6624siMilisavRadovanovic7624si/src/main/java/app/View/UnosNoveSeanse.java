package app.View;

import app.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class UnosNoveSeanse extends VBox {

    public UnosNoveSeanse() {
        setSpacing(10);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Polja za klijenta
        TextField tfJmbg = new TextField();
        TextField tfIme = new TextField();
        TextField tfPrezime = new TextField();
        DatePicker dpDatumRodjenja = new DatePicker();
        TextField tfPol = new TextField();
        TextField tfEmail = new TextField();
        TextField tfTelefon = new TextField();
        TextField tfProblem = new TextField();
        CheckBox cbRanije = new CheckBox("Ranije posećivao terapeute?");

        // Seansa
        DatePicker dpDatumSeanse = new DatePicker();
        TextField tfDan = new TextField();
        TextField tfVreme = new TextField();
        TextField tfTrajanje = new TextField();
        TextField tfBroj = new TextField();
        TextField tfBeleska = new TextField();
        TextField tfJmbgKandidata = new TextField();

        // Cena
        TextField tfCena = new TextField();

        // Dugme
        Button btnSacuvaj = new Button("Sačuvaj seansu");

        // Dodavanje na grid
        grid.add(new Label("JMBG Klijenta:"), 0, 0); grid.add(tfJmbg, 1, 0);
        grid.add(new Label("Ime:"), 0, 1); grid.add(tfIme, 1, 1);
        grid.add(new Label("Prezime:"), 0, 2); grid.add(tfPrezime, 1, 2);
        grid.add(new Label("Datum rođenja:"), 0, 3); grid.add(dpDatumRodjenja, 1, 3);
        grid.add(new Label("Pol (M/Z):"), 0, 4); grid.add(tfPol, 1, 4);
        grid.add(new Label("Email:"), 0, 5); grid.add(tfEmail, 1, 5);
        grid.add(new Label("Telefon:"), 0, 6); grid.add(tfTelefon, 1, 6);
        grid.add(new Label("Opis problema:"), 0, 7); grid.add(tfProblem, 1, 7);
        grid.add(cbRanije, 1, 8);

        grid.add(new Label("Datum seanse:"), 0, 9); grid.add(dpDatumSeanse, 1, 9);
        grid.add(new Label("Dan seanse:"), 0, 10); grid.add(tfDan, 1, 10);
        grid.add(new Label("Vreme (HH:mm):"), 0, 11); grid.add(tfVreme, 1, 11);
        grid.add(new Label("Trajanje (min):"), 0, 12); grid.add(tfTrajanje, 1, 12);
        grid.add(new Label("Broj seanse:"), 0, 13); grid.add(tfBroj, 1, 13);
        grid.add(new Label("Beleška:"), 0, 14); grid.add(tfBeleska, 1, 14);
        grid.add(new Label("JMBG kandidata:"), 0, 15); grid.add(tfJmbgKandidata, 1, 15);
        grid.add(new Label("Cena u dinarima:"), 0, 16); grid.add(tfCena, 1, 16);

        getChildren().addAll(grid, btnSacuvaj);

        btnSacuvaj.setOnAction(e -> {
            try (Connection conn = DatabaseConnector.connect()) {
                int klijentJmbg = Integer.parseInt(tfJmbg.getText());
                PreparedStatement check = conn.prepareStatement("SELECT * FROM Klijent WHERE klijent_jmbg = ?");
                check.setInt(1, klijentJmbg);
                ResultSet rs = check.executeQuery();

                if (!rs.next()) {
                    PreparedStatement insertKlijent = conn.prepareStatement("INSERT INTO Klijent VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    insertKlijent.setInt(1, klijentJmbg);
                    insertKlijent.setString(2, tfIme.getText());
                    insertKlijent.setString(3, tfPrezime.getText());
                    insertKlijent.setDate(4, Date.valueOf(dpDatumRodjenja.getValue()));
                    insertKlijent.setString(5, tfPol.getText());
                    insertKlijent.setString(6, tfEmail.getText());
                    insertKlijent.setString(7, tfTelefon.getText());
                    insertKlijent.setBoolean(8, cbRanije.isSelected());
                    insertKlijent.setString(9, tfProblem.getText());
                    insertKlijent.executeUpdate();
                }

                PreparedStatement maxIdStmt = conn.prepareStatement("SELECT MAX(seansa_id) + 1 FROM Seansa");
                ResultSet maxIdRs = maxIdStmt.executeQuery();
                maxIdRs.next();
                int noviId = maxIdRs.getInt(1);

                PreparedStatement insertSeansa = conn.prepareStatement("INSERT INTO Seansa VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                insertSeansa.setInt(1, noviId);
                insertSeansa.setDate(2, Date.valueOf(dpDatumSeanse.getValue()));
                insertSeansa.setString(3, tfDan.getText());
                insertSeansa.setTime(4, Time.valueOf(tfVreme.getText() + ":00"));
                insertSeansa.setInt(5, Integer.parseInt(tfTrajanje.getText()));
                insertSeansa.setInt(6, Integer.parseInt(tfBroj.getText()));
                insertSeansa.setString(7, tfBeleska.getText());
                insertSeansa.setInt(8, klijentJmbg);
                insertSeansa.setString(9, tfJmbgKandidata.getText());
                insertSeansa.executeUpdate();

                PreparedStatement insertCena = conn.prepareStatement("INSERT INTO Cena_po_satu VALUES (?, ?, ?)");
                insertCena.setInt(1, noviId);
                insertCena.setInt(2, Integer.parseInt(tfCena.getText()));
                insertCena.setInt(3, noviId);
                insertCena.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uspeh");
                alert.setHeaderText(null);
                alert.setContentText("Seansa uspešno dodata!");
                alert.showAndWait();

                App.stage.setScene(new Scene(new PregledSeansi()));

            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Greška pri unosu: " + ex.getMessage());
                alert.showAndWait();
            }
        });
    }
}
