package services;

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

import services.immonetService.ImmonetMainURL;
import models.ExposeModel;
import dataBase.Connection_DB;
import dataBase.Select_DB;

public class MainService{

    public MainService(){}

        public String searchRealEstateWithCityName(String cityName) throws Exception{

            System.out.println("MainService erreicht");

            new Connection_DB().connect();

            new ImmonetMainURL().useInitialURL(cityName);

            return cityName;
    }
}
