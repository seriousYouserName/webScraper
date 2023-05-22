package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert_DB {

    public Insert_DB(){}


    public Connection connect() {
 
        String url = "jdbc:sqlite:app/dataBase/DataBase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void insert(String link, String price, String squareMeter, String rooms/*, String pictureLink*/) {
        //String sql = "INSERT INTO expose(link,price,squareMeter,rooms,pictureLink) VALUES(?,?,?,?,?)";
        String sql = "INSERT INTO expose(link,price,squareMeter,rooms) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, link);
            pstmt.setString(2, price);
            pstmt.setString(3, squareMeter);
            pstmt.setString(4, rooms);
            //pstmt.setString(5, pictureLink);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}


