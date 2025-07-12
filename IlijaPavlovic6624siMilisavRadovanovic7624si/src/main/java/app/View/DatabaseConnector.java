package app.View;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/savetovaliste_novi_pocetak";
    private static final String USER = "root"; // ili drugo korisničko ime
    private static final String PASSWORD = "2004Ilija";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}