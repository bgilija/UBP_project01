package app.View;

import app.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogIn extends VBox {
    private Label lblKorisnickoIme;

    private GridPane gp;
    private Label lblSifra;
    private TextField tfKorisnickoIme;
    private TextField tfSifra;
    private Button btnLogIn;
    private Label lblLogIn;
    public LogIn()
        {
            napraviElemente();
            dodajElemente();
            dodajAkcije();
        }

        private void napraviElemente()
        {
            gp=new GridPane();
            lblLogIn=new Label("Log in:");
            lblKorisnickoIme=new Label("E-mail:");
            lblSifra = new Label("Sifra:");
            tfSifra = new TextField();
            tfKorisnickoIme = new TextField();
            btnLogIn=new Button("log in");
        }

        private void dodajElemente()
        {
            gp.setVgap(10);
            gp.setHgap(10);
            gp.setAlignment(Pos.CENTER);
            gp.setPadding(new Insets(15,10,15,10));
            gp.addColumn(0, lblKorisnickoIme,lblSifra);
            gp.addColumn(1, tfKorisnickoIme, tfSifra);
            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(15,10,15,10));
            this.setMinWidth(400);
            this.setMinHeight(500);
            this.getChildren().addAll(lblLogIn, gp, btnLogIn);

        }

    private void dodajAkcije() {
        btnLogIn.setOnAction(e -> {
            String email = tfKorisnickoIme.getText();
            String sifra = tfSifra.getText();

            try (Connection conn = DatabaseConnector.connect()) {
                String sql = "SELECT jmbg, sifra_naloga FROM Kandidat WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String jmbg = rs.getString("jmbg");
                    String sifraIzBaze = rs.getString("sifra_naloga");

                    if (sifra.equals(sifraIzBaze)) {
                        // Uspešno logovanje
                        Scene scene = new Scene(new IzborPregleda(jmbg));
                        App.stage.setScene(scene);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Greška", "Pogrešna šifra.");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Greška", "Korisnik sa datim emailom ne postoji.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Greška", "Došlo je do greške pri logovanju.");
            }
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
