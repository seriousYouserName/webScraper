package services.immonetService;

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

import services.immonetService.*;
import services.immonetService.ImmonetCrawlHTML;

public class ImmonetGetHTML{

    public ImmonetGetHTML(){}

    public void getHTML(String URL) throws ParseException {

        try {

            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.println("");
            System.out.println("");
            System.out.println("Jsoup bekommt den Inhalt von der Seite: " + URL);
            System.out.println("");

            Document doc = Jsoup.connect(URL).get();

            BufferedWriter bw = null;

            try {

                /*
                Möglich ein Objekt auszulesen anstatt einer Datei????
                Wäre performanter denke ich
                 */
                System.out.println("");
                System.out.println("Schreibt in File");
                System.out.println("");

                bw = new BufferedWriter(new FileWriter("/home/dennis/Dennis/CODE/immoscraper_play_2.0/immoscraper_play_2.0/Crawler_PageContent_HTML.txt"));

                bw.write(doc.toString());

                bw.close();

            } catch (IOException e1) { 
                e1.printStackTrace();
            }
        } catch (IOException e1) { 
            e1.printStackTrace();
        }
        new ImmonetCrawlHTML().getPageLinks();
    }
}