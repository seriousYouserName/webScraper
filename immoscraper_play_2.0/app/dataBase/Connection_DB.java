package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection_DB {

    public static void connect() {

       Connection conn = null;
       
       String url = "jdbc:sqlite:app/dataBase/DataBase.db";

       String drop = "DROP TABLE expose";

       String create = "CREATE TABLE IF NOT EXISTS expose (\n"
       + "	id integer PRIMARY KEY,\n"
       + "	link text NOT NULL,\n"
       + "	price text NOT NULL,\n"
       + "	squareMeter text NOT NULL,\n"
       + "	rooms text NOT NULL\n"
       //+ "	pictureLink text NOT NULL\n"
       + ");";

       
       
       try {
       
           conn = DriverManager.getConnection(url);
           Statement stmt = conn.createStatement();
           
           stmt.execute(drop);
           stmt.execute(create);

           System.out.println("Datenbankverbindung laeuft.");
           
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       } finally {
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException ex) {
               System.out.println(ex.getMessage());
           }
       }
   }

}
