package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;

import models.ExposeModel;

import java.util.ArrayList;

public class Select_DB{

    protected ArrayList<ExposeModel> exposeList;

    public Select_DB(){

        exposeList = new ArrayList<ExposeModel>(); 
    }
    
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



    public void select() {
    
        try{
        Statement stmt = connect().createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM expose;");

        while(resultSet.next()){
/*
            System.out.println( resultSet.getString("link") );
            System.out.println( resultSet.getString("price") );
            System.out.println( resultSet.getString("squareMeter") );
            System.out.println( " " );
            String link = resultSet.getString("link");
            String price = resultSet.getString("price");
            String squareMeter = resultSet.getString("squareMeter");
            
            System.out.println(link);
*/

            //exposeList.add( new ExposeModel(link , price , squareMeter));

           exposeList.add( new ExposeModel(resultSet.getString("link") , resultSet.getString("price") , resultSet.getString("squareMeter") , resultSet.getString("rooms")/* , resultSet.getString("pictureLink")*/));
            }
        
        resultSet.close();
        connect().close();
        

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList getExposeList(){
        return exposeList;
    }

}
