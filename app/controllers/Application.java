package controllers;


import play.mvc.Result;
import views.html.pages.index;


@org.springframework.stereotype.Controller
public class Application {


    public Result index() {
        return play.mvc.Controller.ok(index.render());
    }

}