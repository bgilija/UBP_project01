package app.View;


import java.sql.*;

public class Main {
        public static void main(String[] args) throws SQLException {
          Connection connector = DatabaseConnector.connect();
          Statement st=connector.createStatement();
            //ResultSet rs = st.executeQuery("DESC kandidat");
          //  while (rs.next()) {
           //     System.out.println("Kolona: " + rs.getString("Field") + ", Tip: " + rs.getString("Type"));
           // }
            /*String sql = "delete from klijent where klijent_jmbg=123456789";
            PreparedStatement pstmt = connector.prepareStatement(sql);
            pstmt.executeUpdate();




//insert:
String sql = "INSERT INTO klijent (klijent_jmbg, ime, prezime, datum_rodjenja, pol, email, br_telefona, ranije_posete_psihoterapeutima, ukratko_objasnjenje_problema) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connector.prepareStatement(sql);
            pstmt.setInt(1, 0);
            pstmt.setString(2, "Milan");
            pstmt.setString(3, "Jovanović");
            pstmt.setDate(4, Date.valueOf("1990-01-01"));
            pstmt.setString(5, "M");
            pstmt.setString(6, "milan@example.com");
            pstmt.setString(7, "0601234567");
            pstmt.setBoolean(8, true);
            pstmt.setString(9, "Osećam anksioznost u poslednje vreme.");
            pstmt.executeUpdate();
             */
            String query = "SELECT * FROM klijent";
            try (
                 ResultSet rs = st.executeQuery(query)) {
                while (rs.next()) {
                    System.out.println(rs.getInt("klijent_jmbg") + ", " +
                            rs.getString("ime") + ", " +
                            rs.getString("prezime") + ", " +
                            rs.getDate("datum_rodjenja") + ", " +
                            rs.getString("pol") + ", " +
                            rs.getString("email") + ", " +
                            rs.getString("br_telefona") + ", " +
                            rs.getBoolean("ranije_posete_psihoterapeutima") + ", " +
                            rs.getString("ukratko_objasnjenje_problema"));
                }
            }

        }
    }

