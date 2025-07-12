package app.View;

import app.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class IzborPregleda extends VBox {
    //pregled profila
    private Label lblNaslov;
    private String jmbg;
    private Button btnPregledProfilaTerapeuta;
    private Button btnPregledNovihKlijenata;
    private Button btnPregledOdrzanihSeansi;
    private Button btnPregledBuducihSeansi;

    private Button btnBack;




    public IzborPregleda(String jmbg){
        this.jmbg = jmbg;
        napraviElemente();
        dodajElemente();
        dodajAkcije();
    }

    private void napraviElemente(){
        btnPregledNovihKlijenata=new Button("Pregled novih klijenata");
        btnPregledProfilaTerapeuta=new Button("Profil psihoterapeuta");
        btnPregledOdrzanihSeansi =  new Button("Pregled odrzanih seansi");
        btnPregledBuducihSeansi = new Button("Pregled buducih seansi");
        lblNaslov = new Label("Mozete odabrati neku od ponudjenih opcija");
        btnBack = new Button("Back");
    }
    private void dodajElemente(){
        this.getChildren().add(lblNaslov);
        this.getChildren().add(btnPregledNovihKlijenata);
        this.getChildren().add(btnPregledProfilaTerapeuta);
        this.getChildren().add(btnPregledOdrzanihSeansi);
        this.getChildren().add(btnPregledBuducihSeansi);
        this.getChildren().add(btnBack);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10,10,10,10));
        this.setSpacing(15);

    }
    private void dodajAkcije(){

        btnPregledNovihKlijenata.setOnAction(e -> {
            Scene scene = new Scene(new PPrijavaNovihKlijenataKodPrijavljenogPsihoterapeuta(jmbg));
            App.stage.setScene(scene);
        });
        btnPregledProfilaTerapeuta.setOnAction(e -> {
            Scene scene = new Scene(new PregledProfilaPsihoterapeuta(jmbg));
            App.stage.setScene(scene);
        });
        btnPregledOdrzanihSeansi.setOnAction(e -> {
            Scene scene = new Scene(new PregledOdrzanihSeansiPrijavljenogPsihoterapeuta(jmbg));
            App.stage.setScene(scene);
        });
        btnPregledBuducihSeansi.setOnAction(e -> {
            Scene scene = new Scene(new PregledTerminaBuducihSeansi(jmbg));
            App.stage.setScene(scene);
        });
        btnBack.setOnAction(e -> {
            Scene scene = new Scene(new PocetniEkran());
            App.stage.setScene(scene);
        });
    }
}
