package controllers;

import play.mvc.*;

import play.*;
import play.data.Form;

import models.ExposeModel;
import services.MainService;
import services.immonetService.ImmonetCrawlHTML;

import views.html.*;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.ArrayList;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */

@Singleton
public class HomeController extends Controller {

   // private final MainService mainService;

    @Inject
    public HomeController(){
        
   //     this.mainService = new MainService();
    }


    public Result index() {
 //
        return ok(index.render());
    }
}
