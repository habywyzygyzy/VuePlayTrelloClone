package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        String userID = session().getOrDefault("user.id", "null");
        return ok(index.render("You're logged in!" + userID));
    }

}
