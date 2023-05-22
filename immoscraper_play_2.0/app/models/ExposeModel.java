package models;

import java.util.ArrayList;
import java.io.Serializable;


// Klassen nicht auf public setzen
public class ExposeModel{

// private getter to implement
 /* public String headLine;

    public String area;

    public String shortDiscription;  

    public float averagePrice;
    */
    public String link;

    public String price;

    public String squareMeter;
    
    public String rooms;

    //public String pictureLink;

    //public String pictureLink_2;

    // SICHER ??
    //private static ArrayList<ExposeModel> exposes;


    public ExposeModel( /* String headLine, String area, String shortDiscription, float averagePrice */ String link, String price , String squareMeter , String rooms/* , String pictureLink*/ ){
        
        this.price = price;
        this.squareMeter = squareMeter; 
        this.link = link;
        this.rooms = rooms;
        
        //this.pictureLink = pictureLink;


        //this.pictureLink_2 = pictureLink_2;
        /*
        this.rooms = rooms;
        this.headLine = headLine;
        this.area = area;
        this.shortDiscription = shortDiscription;
        this.averagePrice = averagePrice;
        */
    }

    // Getter //
    public String getPrice(){

        return price;
    }

    public String getLivingSpace(){
       
        return squareMeter;
    }

    public String getLink(){
        
        return link;
    }

    public String getRooms(){
        
        return rooms;
    }

/*
    public String getPictureLink(){
        
        return pictureLink;
    }


public String getPictureLink_2(){
        
        return pictureLink_2;
    }
    
    public String getHeadLine(){
        return headLine;
    }

    public String getArea(){
        return area;
        }

    public String getShortDiscription(){
        return shortDiscription;
    }

    public double getRent(){
        return rent;
    }



    public float getAveragePrice(){
        return averagePrice;
    }
*/
}
