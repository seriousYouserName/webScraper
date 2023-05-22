package services.immonetService;

import play.*;

import javax.inject.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Scanner;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import services.immonetService.ImmonetREGEX;
import models.ExposeModel;
import dataBase.Insert_DB;
import controllers.ExposeController;



public class ImmonetCrawlHTML{

    protected ArrayList<String> objectIdList;

    // protected HashSet<String> hrefList;

    // to find highest pagenumber. Otherwise i will get more pages than normaly are
    // avaiable for one destrict or city
    protected ArrayList<String> pageNumber;

    // protected String pageNumber;

    protected String immoNet_regex_objectId;

    protected String immoNet_regex_cityCode;

    protected String immoNet_regex_pageNumber;
    
    protected String immoNet_regex_price;
    
    protected String immoNet_regex_Auf_Anfrage;
    
    protected String immoNet_regex_squareMeter;

    protected String immoNet_regex_selPrice;

    protected String immoNet_regex_selRooms;

    protected String immoNet_regex_rooms;

    protected String immoNet_regex_pictureLink;

    protected String immoNet_regex_pictureLink_2;

    protected String immoNet_regex_headLine;



    boolean matchFound_pictureLink_2;

    boolean matchFound_pictureLink;
    

// URL-Stuff
    protected static String cityName;

    protected static String cityCode;

    private static int highestPageNumber;



// Expose-objects
    private ExposeModel exposeModel;

    private ArrayList<ExposeModel> exposesList;
    
    private ArrayList<String> selAreaIdList;
    
    private ArrayList<String> priceList;

    private ArrayList<String> selPriceIdList;

    private ArrayList<String> selRoomsIdList;

    private ArrayList<String> livingSpaceList;

    private ArrayList<String> linkList;

    private ArrayList<String> roomsList;

    private ArrayList<String> headLineList;

    private ArrayList<Double> average_price_per_squareMeter;

    private ArrayList<String> pages;

    private ArrayList<Double> priceWithoutPointList;

    private ArrayList<String> pictureList;



    public ImmonetCrawlHTML(){

        immoNet_regex_objectId = new ImmonetREGEX().getREGEXobjectId();

        immoNet_regex_cityCode = new ImmonetREGEX().getREGEXcityCode();

        immoNet_regex_pageNumber = new ImmonetREGEX().getREGEXmaxPage();
 
        immoNet_regex_price = new ImmonetREGEX().getREGEXprice();

        immoNet_regex_Auf_Anfrage = new ImmonetREGEX().getREGEXanfrage();

        immoNet_regex_squareMeter = new ImmonetREGEX().getREGEXsquareMeter();

        immoNet_regex_selPrice = new ImmonetREGEX().getREGEXselPrice();

        immoNet_regex_rooms = new ImmonetREGEX().getREGEXrooms();

        immoNet_regex_selRooms = new ImmonetREGEX().getREGEXselRooms();

        immoNet_regex_pictureLink = new ImmonetREGEX().getREGEXpictureLink();

        immoNet_regex_pictureLink_2 = new ImmonetREGEX().getREGEXpictureLink_2();

        immoNet_regex_headLine = new ImmonetREGEX().getREGEXheadLine();
        

        selAreaIdList = new ArrayList<String>();

        selPriceIdList = new ArrayList<String>();

        selRoomsIdList = new ArrayList<String>();
       
        objectIdList = new ArrayList<String>();
        
        pageNumber = new ArrayList<String>();

        linkList = new ArrayList<String>();

        priceList = new ArrayList<String>();

        livingSpaceList = new ArrayList<String>();

        roomsList = new ArrayList<String>();

        pictureList = new ArrayList<String>();

        average_price_per_squareMeter = new ArrayList<Double>();

        priceWithoutPointList = new ArrayList<Double>();

        pages = new ArrayList<String>();

        exposesList = new ArrayList<ExposeModel>();

    }

    public int getHighestPageNumber(){
        return highestPageNumber;
    }

    public String getCityCode(){
        return cityCode;
    }

    // remove
    public ExposeModel getExposeModelObject(){
        return exposeModel;
    }

    // timeout(6000) -->
    public void getPageLinks() throws ParseException {

        try {

            // ******************************** READ *********************************************/

              String fileLocation = "/home/dennis/Dennis/CODE/immoscraper_play_2.0/immoscraper_play_2.0/Crawler_PageContent_HTML.txt";

            BufferedReader br = null;

            try {

                br = new BufferedReader(new FileReader(fileLocation));
                String line = null;
                while ((line = br.readLine()) != null) {

                    getHighestPagenumber(line);
                }

            } catch (FileNotFoundException e) {
                
                e.printStackTrace();
            }
            
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }


            try {

                br = new BufferedReader(new FileReader(fileLocation));
                String line = null;
                while ((line = br.readLine()) != null) {

                    highestPageNumber();
        
                    getLinks(line);

                    getCityNumber(line);

                    getPrice(line);

                    getAufAnfrage(line);

                    getRooms(line);

                    getSquareMeter(line);
                    
                    getHeadLine(line);

                    // getPictureLink(line);


/* 
                    if(getPictureLink_1(line) == true){
                        continue;
                    }
                    else
                    {
                        getPictureLink_2(line);
                    }
*/                    


                }

            } catch (FileNotFoundException e) {
                
                e.printStackTrace();
            }
            
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }


            for (int id = 0; id < objectIdList.size(); id++) {

                linkList.add("https://www.immonet.de/angebot/" + objectIdList.get(id) + "?drop=sel&related=false&product=immobooster");
            }

//###############################################  selRooms-ID  ####################################################################################

/*
Wenn 2 WohnObjekte auf einer Seite keine Zimmer angegeben haben :

- Das erste WohnObjekt wird erkannt und richtig deklariert mit !keine Zimmer!

- Das zweite WohnObjekt wird vllt auch erkannt, allerdings nicht richtig deklariert, sondern die ZimmerAnzahl
  des darauf folgenden WohnObjektes eingetragen.
  --> Somit verkürzt sich die Liste um eins. Folge : "Exit out of bounds" weil alle anderen Listen um eins laenger sind. 

*/
    
                try {

                    br = new BufferedReader(new FileReader(fileLocation));
                    String line = null;
                    while ((line = br.readLine()) != null) {

                    
                    // alle selPrice-ID's in eine Liste
                    // Danach diese Liste mit objectIdList vergleichen
                    // Listen sind sortiert. Index der fehlenden selPrice-ID geben bzw wo ein "String" eingetragen werden soll.
                    for(int id = 0 ; id < objectIdList.size() ; id++){
                             
                        if ( line.indexOf("selRooms_" + objectIdList.get(id))  != -1 ){

                            selRoomsIdList.add(objectIdList.get(id)); 
                        }
                    }

                    for(int id = 0 ; id < objectIdList.size() ; id++){
                        
                        if ( line.indexOf("selArea_" + objectIdList.get(id))  != -1 ){
                    
                             selAreaIdList.add(objectIdList.get(id)); 
                        }
                    }   

                    for(int id = 0 ; id < objectIdList.size() ; id++){

                        if ( line.indexOf("selPrice_" + objectIdList.get(id))  != -1 ){   
                        
                             selPriceIdList.add(objectIdList.get(id)); 
                        }
                    }
                }
            
                } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }
    
                

//################################################  selPrice-ID  #####################################################################################
          
//################################################  selArea-ID  ######################################################################################

                            
                for(int id = 0 ; id < objectIdList.size() ; id++){
                    
                    if( objectIdList.get(id) == selRoomsIdList.get(id)) continue;
                    
                        roomsList.add( id , " !! keine Zimmeranzahl angegeben !!" );
                        
                        selRoomsIdList.add( id ,objectIdList.get(id)); 
                }
                

                for(int id = 0 ; id < objectIdList.size() ; id++){
                        
                    if( objectIdList.get(id) == selPriceIdList.get(id)) continue;
  
                        priceList.add( id , " !! kein Preis angegeben !!" );
                        
                        selPriceIdList.add( id ,objectIdList.get(id));
                        
                }
  
                
                for(int id = 0 ; id < objectIdList.size() ; id++){
                    
                    if( objectIdList.get(id) == selAreaIdList.get(id)) continue;
                        
                        livingSpaceList.add( id , " !! keine Quadratmeter angegeben !!" );
                    
                        selAreaIdList.add( id , objectIdList.get(id));
                }

//###################################################################################################################################

            Insert_DB insert_DB =new Insert_DB();
                
                System.out.println("Link : " + linkList);
                System.out.println("Link : " + linkList.size());
                System.out.println("");
                
                System.out.println("Price : " + priceList);
                System.out.println("Price : " + priceList.size());
                System.out.println("");
                
                System.out.println("Rooms : " + roomsList);
                System.out.println("Rooms : " + roomsList.size());
                System.out.println("");
                
                System.out.println("LivingSpace : " + livingSpaceList);
                System.out.println("LivingSpace : " + livingSpaceList.size());
                System.out.println("");


                System.out.println("headLine : " + headLineList);
                System.out.println("headLine : " + headLineList.size());
                System.out.println("");
                
/*
                System.out.println("Picture : " + pictureList);
                System.out.println("Picture : " + pictureList.size());
                System.out.println("");
*/

            for( int characteristics = 0 ; characteristics < linkList.size() ; characteristics++ ){
                insert_DB.insert(   linkList.get(characteristics) , priceList.get(characteristics) , livingSpaceList.get(characteristics) , roomsList.get(characteristics) /*, pictureList.get(characteristics)*/  );
            }          

        } catch (IOException e) {
            // TODO: handle exception 
   }       
}

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

                private void getHighestPagenumber(String line){

                    Pattern pattern_pageNumber = Pattern.compile(immoNet_regex_pageNumber);
                    Matcher matcher_pageNumber = pattern_pageNumber.matcher(line);
                    boolean matchFound_pageNumber = matcher_pageNumber.find();

                    if (matchFound_pageNumber) {

                        pageNumber.add(matcher_pageNumber.group(1));
                    }
                }

                
                private void highestPageNumber(){ 
                    
                    String pageNum = pageNumber.get(pageNumber.size() - 2);
                    highestPageNumber = Integer.parseInt(pageNum);
                    }
   
                // ObejctId for links (URL)
                private void getLinks(String line){

                    Pattern pattern_objectId = Pattern.compile(immoNet_regex_objectId);
                    Matcher matcher_objectId = pattern_objectId.matcher(line);
                    boolean matchFound_objectId = matcher_objectId.find();
                    
                    if (matchFound_objectId) {

                        objectIdList.add(matcher_objectId.group(1));
                    }
                }

                    
                // CityCode for pages (href - URL)
                private void getCityNumber(String line){

                    Pattern pattern_cityCode = Pattern.compile(immoNet_regex_cityCode);
                    Matcher matcher_cityCode = pattern_cityCode.matcher(line);
                    boolean matchFound_cityCode = matcher_cityCode.find();

                    if (matchFound_cityCode) {

                        cityCode = matcher_cityCode.group(1);
                    }
                }


                private void getPrice(String line){
                
                    Pattern pattern_price = Pattern.compile(immoNet_regex_price);
                    Matcher matcher_price = pattern_price.matcher(line);
                    boolean matchFound_price = matcher_price.find();
                    
                    if (matchFound_price) {

                        priceList.add(matcher_price.group(1));
                    }
                }

                
                private void getAufAnfrage(String line){

                    Pattern pattern_price_Auf_Anfrage = Pattern.compile(immoNet_regex_Auf_Anfrage);
                    Matcher matcher_price_Auf_Anfrage = pattern_price_Auf_Anfrage.matcher(line);
                    boolean matchFound_price_Auf_Anfrage = matcher_price_Auf_Anfrage.find();
                    
                    if (matchFound_price_Auf_Anfrage) {

                        priceList.add(" Auf Anfrage ");    
                    }
                }
                    

                private void getRooms(String line){

                    Pattern pattern_rooms = Pattern.compile(immoNet_regex_rooms);
                    Matcher matcher_rooms = pattern_rooms.matcher(line);
                    boolean matchFound_rooms = matcher_rooms.find();

                    if (matchFound_rooms) {

                        roomsList.add(matcher_rooms.group(1));
                    }
                }


                private void getSquareMeter(String line){

                    Pattern pattern_squareMeter = Pattern.compile(immoNet_regex_squareMeter);
                    Matcher matcher_squareMeter = pattern_squareMeter.matcher(line);
                    boolean matchFound_squareMeter = matcher_squareMeter.find();

                    if (matchFound_squareMeter) {

                        livingSpaceList.add(matcher_squareMeter.group(1));
                    }
                }      

                
                private void getHeadLine(String line){
                        
                    try  {
    
                        Pattern pattern_headLine = Pattern.compile(immoNet_regex_headLine);
                        Matcher matcher_headLine = pattern_headLine.matcher(line);
                        boolean matchFound_headLine = matcher_headLine.find();
    
                        if (matchFound_headLine) {
    
                            headLineList.add(matcher_headLine.group(1));
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        headLineList.add("no headLine");
                    }
                }

                /*
                private boolean getPictureLink_1(String line){

                    Pattern pattern_pictureLink = Pattern.compile(immoNet_regex_pictureLink);
                    Matcher matcher_pictureLink = pattern_pictureLink.matcher(line);
                    matchFound_pictureLink = matcher_pictureLink.find();
                    
                    if (this.matchFound_pictureLink) {

                        System.out.println("pictureLink_1");
                        
                        pictureList.add("https://i.immonet.de/"+matcher_pictureLink.group(1)+"/"+matcher_pictureLink.group(2)+"/"+matcher_pictureLink.group(3)+"/"+matcher_pictureLink.group(4)+"_"+matcher_pictureLink.group(5)+"x"+matcher_pictureLink.group(6)+".jpg");
                    }
                    return true;private void getHeadLine(String line){
    
                        Pattern pattern_headLine = Pattern.compile(immoNet_regex_headLine);
                        Matcher matcher_headLine = pattern_headLine.matcher(line);
                        boolean matchFound_headLine = matcher_headLine.find();
    
                        if (matchFound_headLine) {
    
                            headLineList.add(matcher_headLine.group(1));
                        }
                        else{
                            headLineList.add("no headLine");
                        }
                }
                
                private void getPictureLink_2(String line){
                    
                    Pattern pattern_pictureLink_2 = Pattern.compile(immoNet_regex_pictureLink_2);
                    Matcher matcher_pictureLink_2 = pattern_pictureLink_2.matcher(line);
                    matchFound_pictureLink_2 = matcher_pictureLink_2.find();
                    
                    if (this.matchFound_pictureLink) {

                        System.out.println("pictureLink_2");
                        
                        pictureList.add("https://i.immonet.de/"+matcher_pictureLink_2.group(1)+"/"+matcher_pictureLink_2.group(2)+"/"+matcher_pictureLink_2.group(3)+"/"+matcher_pictureLink_2.group(4)+"_"+matcher_pictureLink_2.group(5)+"x"+matcher_pictureLink_2.group(6)+".jpg");
                    }
                    */


                    // Kernimplementierung for photos! Doesnt work correct beacuse of getting all pictures of one Inserat. 
/*                
                private void getPictureLink(String line){

                    Pattern pattern_pictureLink = Pattern.compile(immoNet_regex_pictureLink);
                    Matcher matcher_pictureLink = pattern_pictureLink.matcher(line);
                    matchFound_pictureLink = matcher_pictureLink.find();

                    Pattern pattern_pictureLink_2 = Pattern.compile(immoNet_regex_pictureLink_2);
                    Matcher matcher_pictureLink_2 = pattern_pictureLink_2.matcher(line);
                    matchFound_pictureLink_2 = matcher_pictureLink_2.find();

                    if (this.matchFound_pictureLink) {
                        
                        System.out.println("pictureLink_1");
                        
                        pictureList.add("https://i.immonet.de/"+matcher_pictureLink.group(1)+"/"+matcher_pictureLink.group(2)+"/"+matcher_pictureLink.group(3)+"/"+matcher_pictureLink.group(4)+"_"+matcher_pictureLink.group(5)+"x"+matcher_pictureLink.group(6)+".jpg");
                        
                    }
                    else
                    {    
                        if (this.matchFound_pictureLink_2) {
                            
                            System.out.println("pictureLink_2");

                            pictureList.add("https://i.immonet.de/"+matcher_pictureLink_2.group(1)+"/"+matcher_pictureLink_2.group(2)+"/"+matcher_pictureLink_2.group(3)+"/"+matcher_pictureLink_2.group(4)+"_"+matcher_pictureLink_2.group(5)+"x"+matcher_pictureLink_2.group(6)+".jpg");
                            
                        }

*/


                        /*
                        else
                        {
                            pictureList.add("keine Bilder hinterlegt");
                        }
                        */
                    }



