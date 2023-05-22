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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ImmonetREGEX{

    private String immoNet_regex_objectId = String.format("\"selObject_(\\d{1,})\"", Pattern.CASE_INSENSITIVE);

    private String immoNet_regex_cityCode = String.format("<input type=\"hidden\" name=\"city\" value=\"(\\d{1,})\">",
            Pattern.CASE_INSENSITIVE);

    private String immoNet_regex_pageNumber = String.format("marketingtype=1&amp;page=(\\d{1,})\"",
            Pattern.CASE_INSENSITIVE);
/*
    // Picture
    Private String immoNet_regex_picture = String.format("img src=\"data:image/png;base64,([a-zA-Z0-9])*=\" width=\"280\" height=\"157\"",Pattern.CASE_INSENSITIVE);
 */

    // price
    private String immoNet_regex_price = String.format("<span class=\"text-250 text-strong text-nowrap\">(([0-9]+[.])?[0-9]+[.][0-9]+)",Pattern.CASE_INSENSITIVE);

    // squareMeter
    private String immoNet_regex_squareMeter = String.format("<p class=\"text-250 text-strong text-nowrap\"> ([0-9]*[.]?[0-9]*) <span class=\"text-50\">m²</span> </p>",Pattern.CASE_INSENSITIVE);

    // for not having a selPrice
    //private String immoNet_regex_selPrice = String.format("<div id=\"selPrice_(\\d[0-9]*)\" class=\"flex-grow-1 box-25\">", Pattern.CASE_INSENSITIVE);
    private String immoNet_regex_selPrice = String.format("<div id=\"selPrice_([0-9]*)\" class=\"flex-grow-1 box-25\">", Pattern.CASE_INSENSITIVE);


    // searchparameter for not having a price
    // String immoNet_regex_Auf_Anfrage = String.format("Auf Anfrage");
    private String immoNet_regex_Auf_Anfrage = String.format("<p class=\"text-200 text-strong text-nowrap\">Auf Anfrage<",Pattern.CASE_INSENSITIVE);




    // numberOfRooms
    private String immoNet_regex_rooms = String.format("<p class=\"text-250 text-strong text-nowrap\"> ([0-9]*[.]?[0-9]*) <span class=\"text-50\">&nbsp;</span> </p>",Pattern.CASE_INSENSITIVE);

    //private String immoNet_regex_rooms = String.format("id=\"selRooms_([0-9]*)\"class=\"flex-grow-1 box-25\"><p class=\"text-25 text-gray-dark\">Zi.</p><p class=\"text-250 text-strong text-nowrap\">([0-9]*[.]?[0-9]*)<span class=\"text-50\">&nbsp;</span></p></div>",Pattern.CASE_INSENSITIVE);

    // for not having a selRoom
    private String immoNet_regex_selRooms = String.format("<div id=\"selRooms_([0-9]*)\" class=\"flex-grow-1 box-25\">",Pattern.CASE_INSENSITIVE);
    //private String immoNet_regex_selRooms = String.format("<div id=\"selRooms_(\\d[0-9]*)\" class=\"flex-grow-1 box-25\">",Pattern.CASE_INSENSITIVE);




    // first picture in picturelist because of the bracket [ at the beginning of the list
    private String immoNet_regex_pictureLink = String.format("\\[&quot;https://i.immonet.de/([0-9]*)/([0-9]*)/([0-9]*)/([0-9]*)_([0-9]*)x([0-9]*).jpg",Pattern.CASE_INSENSITIVE);


    private String immoNet_regex_pictureLink_2 = String.format("data-original=\"https://i.immonet.de/([0-9]*)/([0-9]*)/([0-9]*)/([0-9]*)_([0-9]*)x([0-9]*).jpg",Pattern.CASE_INSENSITIVE);
    //private String immoNet_regex_pictureLink_2 = String.format("<img src=\"https://i.immonet.de/([0-9]*)/([0-9]*)/([0-9]*)/([0-9]*)_([0-9]*)x([0-9]*).jpg",Pattern.CASE_INSENSITIVE);
    //<img src="https://i.immonet.de/95/09/11/780950911_280x187.jpg"


    // headLine
    private String immoNet_regex_headLine = String.format("return false;\" title=\"([a-zA-Z0-9\\W]*)\">",Pattern.CASE_INSENSITIVE);


    public ImmonetREGEX(){}

        public String getREGEXselPrice(){

            return immoNet_regex_selPrice;
        }

        public String getREGEXselRooms(){
            
            return immoNet_regex_selRooms;
        }

        public String getREGEXobjectId(){

            return immoNet_regex_objectId;
        }

        public String getREGEXcityCode(){

            return immoNet_regex_cityCode;
        }

        public String getREGEXmaxPage(){

            return immoNet_regex_pageNumber;
        }

        public String getREGEXprice(){

            return immoNet_regex_price;
        }

        public String getREGEXanfrage(){

            return immoNet_regex_Auf_Anfrage;
        }

        public String getREGEXsquareMeter(){

            return immoNet_regex_squareMeter;
        }

        public String getREGEXrooms(){

            return immoNet_regex_rooms;
        }

        public String getREGEXpictureLink(){

            return immoNet_regex_pictureLink;
        }

        public String getREGEXpictureLink_2(){

            return immoNet_regex_pictureLink_2;
        }

        public String getREGEXheadLine(){
                
            return immoNet_regex_headLine;
        } 

}
