package app.View;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;

public class PregledUplataIDugovanjaKlijenata extends HBox {
    private Label lbNaslov;
    private ObservableList<String> olUplateDugovi;
    private TableView twUplatedDugovi;

    public PregledUplataIDugovanjaKlijenata(){
        napraviElemente();
        dodajElemente();
        dodajAkcije();
    }
    private void napraviElemente()
    {
        lbNaslov = new Label("Pregled uplata i dugovanja klijenata");
        twUplatedDugovi.setMinWidth(500);
        twUplatedDugovi = new TableView<>(olUplateDugovi);

         /*
        TableColumn<**, String> col1 = new TableColumn<>("nazivkolone");
        TableColumn<** , String> col2 = new TableColumn<>("nazivkolone");
        TableColumn<**, String> col3 = new TableColumn<>("nazivkolone");

        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("kontinent"));
        col3.setCellValueFactory(new PropertyValueFactory<>("drzava"));

        twNoviKlijenti.getColumns().addAll(col1, col2, col3);

     */
    }

    private void dodajElemente()
    {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15,10,15,10));
        this.setSpacing(30);
        this.getChildren().addAll(lbNaslov, twUplatedDugovi);

    }

    private void dodajAkcije()
    {

    }

}
