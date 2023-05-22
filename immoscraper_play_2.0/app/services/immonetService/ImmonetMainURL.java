/*
 Gets the searchParams and gives the output for this moment.

 URL has to be more flexibel : Wohnung, Haus, kaufen, mieten

*/
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

import services.MainService;



public class ImmonetMainURL{

    private int highestPageNumber;

    private String cityCode;

    private ImmonetGetHTML immonetGetHTML;


    public ImmonetMainURL(){

        immonetGetHTML = new ImmonetGetHTML();
    }
    
    public void useInitialURL(String cityName) throws ParseException {   

        System.out.println("ImmonetMainURL METHODE: useInitalURL");


        immonetGetHTML.getHTML(
        
                "https://www.immonet.de/immobiliensuche/sel.do?&sortby=0&suchart=1&objecttype=1&marketingtype=1&parentcat=1&locationname="+ cityName);

             highestPageNumber = new ImmonetCrawlHTML().getHighestPageNumber();

                      cityCode = new ImmonetCrawlHTML().getCityCode();

        try {
            // Need to fix this crap later.
            // doesnt shows the last page because of wrong operator. But == doesnt work as well...
            for (int pageCounter = 1 ; pageCounter < highestPageNumber; pageCounter++) {

                // only first page
                // for(int pageCounter = 1 ; pageCounter == highestPageNumber ; pageCounter++){

                // not only the specific pages. Shows also the "Randgebiete". To much overhead.
                // for(int pageCounter = 1 ; pageCounter < highestPageNumber +1 ;
                // pageCounter++){

                System.out.println("Seite: " + pageCounter);

                System.out.println("highestPageNumber: " + highestPageNumber);

                immonetGetHTML.getHTML(
                        "https://www.immonet.de/immobiliensuche/sel.do?parentcat=1&objecttype=1&pageoffset=1&listsize=26&suchart=1&sortby=0&city="
                                + cityCode + "&marketingtype=1&page=" + pageCounter);
            }

        } catch (Exception e) {
            System.out.println("catched ur ugly piece of crap!" + e);
        }
    }
}


// kaufen:
// https://www.immonet.de/immobiliensuche/sel.do?&sortby=0&suchart=1&objecttype=1&marketingtype=1&parentcat=1&city=88038&locationname=Bremen

// mieten:
// https://www.immonet.de/immobiliensuche/sel.do?&sortby=0&suchart=1&objecttype=1&marketingtype=2&parentcat=1&city=88038&locationname=Bremen