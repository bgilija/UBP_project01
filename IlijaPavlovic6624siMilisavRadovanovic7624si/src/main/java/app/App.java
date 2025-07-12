package app;

import app.View.PocetniEkran;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage stage; // zajednički stage ako želiš da ga koristiš globalno

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage; // postavlja se globalni stage

        Scene scene = new Scene(new PocetniEkran()); // PocetniEkran je VBox/HBox/Pane
        stage.setScene(scene);
        stage.setTitle("Savetovaliste Novi Pocetak");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // POZIVAS OVDE, ne u start()
    }
}
