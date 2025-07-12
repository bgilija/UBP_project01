package app.View;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ObjavaPodatakaSeanse extends VBox {
    private String jmbgKandidata;
    private int seansaId;
    private Label lblNaslov;
    private ComboBox<String> cbKome;
    private TextArea tfSvrha;
   // private TextField tfKome;
    private Button btnObjavi;
    private Button btnNazad;
    public ObjavaPodatakaSeanse(String jmbgKandidata, int seansaId) {
        napraviElemente();
        dodajElemente();
        dodajAkcije();
        this.jmbgKandidata=jmbgKandidata;
        this.seansaId = seansaId;
        //tfKome.setPromptText("Kome je objavljeno");

        TextField tfSvrha = new TextField();
        tfSvrha.setPromptText("Svrha objave");








    }
    private void napraviElemente()
    {
        lblNaslov = new Label("Objava podataka o seansi");
        cbKome = new ComboBox<>();
        cbKome.getItems().add(0,"Policija");
        cbKome.getItems().add(1,"Sud");


        tfSvrha = new TextArea();
         tfSvrha.setPromptText("Svrha objave");

         btnObjavi = new Button("Objavi");
         btnNazad = new Button("Back");

    }

    private void dodajElemente()
    {
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(lblNaslov, cbKome, tfSvrha, btnObjavi, btnNazad);
    }
    private void dodajAkcije()
    {
        btnObjavi.setOnAction(e -> {
            String kome = cbKome.getSelectionModel().getSelectedItem();
            String svrha = tfSvrha.getText();

            if (kome == null || kome.isEmpty() || svrha.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Popunite sva polja.").show();
                return;
            }

            try (Connection conn = DatabaseConnector.connect()) {
                // 1. Proveri da li već postoji objava za ovu seansu
                String proveraSql = "SELECT COUNT(*) FROM Objava_podataka_seanse WHERE Seansa_seansa_id = ?";
                PreparedStatement proveraStmt = conn.prepareStatement(proveraSql);
                proveraStmt.setInt(1, seansaId);

                var rs = proveraStmt.executeQuery();
                rs.next();
                int brojPostojecih = rs.getInt(1);

                if (brojPostojecih > 0) {
                    new Alert(Alert.AlertType.WARNING, "Već postoji objava za ovu seansu.").show();
                    return;
                }

                // 2. Ako ne postoji, izvrši unos
                String sql = "INSERT INTO Objava_podataka_seanse (objava_podataka_seanse_id, datum_objave, kome_je_objavljena, svrha_objave, Seansa_seansa_id) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);

                int objavaId = (int) (Math.random() * 100000);
                stmt.setInt(1, objavaId);
                stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setString(3, kome);
                stmt.setString(4, svrha);
                stmt.setInt(5, seansaId);

                stmt.executeUpdate();
                new Alert(Alert.AlertType.INFORMATION, "Uspešno objavljeno.").show();

                Scene scena = new Scene(new PregledOdrzanihSeansiPrijavljenogPsihoterapeuta(jmbgKandidata));
                app.App.stage.setScene(scena);

            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Greška pri radu sa bazom.").show();
            }
        });
        btnNazad.setOnAction(e -> {
            Scene scena = new Scene(new PregledOdrzanihSeansiPrijavljenogPsihoterapeuta(jmbgKandidata));
            app.App.stage.setScene(scena);
        });
    }



}
