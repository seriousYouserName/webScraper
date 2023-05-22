package controllers;

import play.mvc.*;

import play.*;
import play.data.Form;

import models.ExposeModel;
import services.MainService;
import services.immonetService.ImmonetCrawlHTML;
import dataBase.Select_DB;

import views.html.exposeOutPut;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.ArrayList;


@Singleton
public class ExposeController extends Controller{

    private final MainService mainService;
    private Select_DB select_DB;
    //private ArrayList<ExposeModel> exposes;


    @Inject
    private ImmonetCrawlHTML immonetCrawlHTML;
   
    @Inject
    public ExposeController(){
        
        this.mainService = new MainService();
        this.select_DB = new Select_DB();
    }

    public Result searchParamsGet(String cityName)throws Exception {

        System.out.println("ExposeController erreicht");
        
        mainService.searchRealEstateWithCityName(cityName);   
        return redirect(routes.ExposeController.show());
    }

    public Result show(){

        select_DB.select();    

        ArrayList<ExposeModel> exposes = select_DB.getExposeList();
        return ok(exposeOutPut.render(exposes));
    }
}