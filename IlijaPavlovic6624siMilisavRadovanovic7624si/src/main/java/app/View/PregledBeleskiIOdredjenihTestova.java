package app.View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
//todo u er dijagram dodati polje beleska ili novi entitet
public class PregledBeleskiIOdredjenihTestova extends VBox {

    private Label lblNaslov;
    private Label lblIdPsihoterapeuta;
    private Label lblImePrezimePsihoterapeuta;
    private Label lblIdKandidata;
    private Label lblImePrezimeKandidata;
    private Label lblBrojSeanse;
    private TextArea txtBeleska;
    private Button btnIzmeniBelesku;

    public PregledBeleskiIOdredjenihTestova(
            int idPsihoterapeuta,
            String imePsihoterapeuta,
            String prezimePsihoterapeuta,
            int idKandidata,
            String imeKandidata,
            String prezimeKandidata,
            int brojSeanse,
            String beleska
    ) {
        lblNaslov = new Label("Pregled beleški sa testiranja");
        lblIdPsihoterapeuta = new Label("ID psihoterapeuta: " + idPsihoterapeuta);
        lblImePrezimePsihoterapeuta = new Label("Psihoterapeut: " + imePsihoterapeuta + " " + prezimePsihoterapeuta);
        lblIdKandidata = new Label("ID kandidata: " + idKandidata);
        lblImePrezimeKandidata = new Label("Kandidat: " + imeKandidata + " " + prezimeKandidata);
        lblBrojSeanse = new Label("Broj seanse: " + brojSeanse);

        txtBeleska = new TextArea(beleska);
        txtBeleska.setWrapText(true);
        txtBeleska.setEditable(false);
        txtBeleska.setPrefHeight(200);

        btnIzmeniBelesku = new Button("Izmeni belešku");
//        btnIzmeniBelesku.setOnAction(e -> {
//            boolean trenutnoStanje = txtBeleska.isEditable();
//            txtBeleska.setEditable(!trenutnoStanje);
//            if (txtBeleska.isEditable()) {
//                btnIzmeniBelesku.setText("Završi izmenu");
//            } else {
//                btnIzmeniBelesku.setText("Izmeni belešku");
//                // Ovdje možeš dodati logiku za čuvanje izmenjene beleške u bazu
//            }
//        });

        this.setSpacing(10);
        this.getChildren().addAll(lblNaslov,lblIdPsihoterapeuta,lblImePrezimePsihoterapeuta,lblIdKandidata,lblImePrezimeKandidata,lblBrojSeanse,
                new Label("Beleška:"),txtBeleska,btnIzmeniBelesku
        );
    }
}
