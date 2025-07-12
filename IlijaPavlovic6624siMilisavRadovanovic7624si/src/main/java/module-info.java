module artikli {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    opens app.Model to javafx.base;
    opens app.DataBase to javafx.base; //da bi radila tabela sa klijentima
    exports app;
}