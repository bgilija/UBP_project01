package app.View;

import app.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//todo dodati dugme za pregled baze
public class PocetniEkran extends VBox {
    private Stage stage;
    private Label lblnaslov;
 //   private Label lblSignIn;
   // private Label lblLogIn;
    private Button btnLogIn;
    private Button btnSignIn;
    private Button btnInformacije;


    public PocetniEkran(){
        napraviElemente();
        dodajElemente();
        dodajAkcije();
    }
    private void napraviElemente()
    {
        lblnaslov=new Label("Novi Pocetak");
        btnLogIn=new Button("Login");
        btnSignIn = new Button("Sign in");
        btnInformacije = new Button("Informacije o bazi");
    }

    private void dodajElemente()
    {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15,10,15,10));
        this.setSpacing(10);
        this.getChildren().addAll(lblnaslov, btnLogIn, btnSignIn,btnInformacije);
        this.setMinHeight(200);
        this.setMinWidth(300);
    }
    private void dodajAkcije()
    {
        btnLogIn.setOnAction(e -> {
            Scene scene = new Scene(new LogIn());
            App.stage.setScene(scene);
            //stage.setScene(scene);
           // stage.show();
        });

        btnSignIn.setOnAction(e ->{
            Scene scene = new Scene(new SignUp());
            App.stage.setScene(scene);
        });
        btnInformacije.setOnAction(e -> {
            Scene scene=new Scene(new Informacije());
            App.stage.setScene(scene);
        });
    }



}

