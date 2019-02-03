package controllers;

import models.Board;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import static play.data.validation.Constraints.*;

public class BoardController extends Controller {

    @Inject
    static FormFactory formFactory;

    public static Result add() {
        Form<NewBoard> boardForm = formFactory.form(NewBoard.class).bindFromRequest();

        Board board = Board.create(User.loggedInUser(), boardForm.get().name);
        return ok();
    }

    public static Result show(Long boardId) {
        Board foundBoard = Board.find.byId(boardId);

        if (foundBoard == null) {
            return badRequest();
        }

        return ok();
    }

    public static Result delete() {
        String boardIDString = formFactory.form().bindFromRequest().get("boardID");

        Long boardId = Long.parseLong(boardIDString);
        Board boardObj = Board.find.byId(boardId);

        if (boardObj == null) {
            return badRequest();
        }

        boardObj.delete();
        return redirect(controllers.routes.HomeController.index());
    }

    public static class NewBoard {
        @Required
        @MinLength(1)
        @MaxLength(200)
        @Pattern(value = "^[A-Za-z0-9- ]+$", message = "page.validation.onlyAlphanumericAndSpace")
        public String name;
    }
}
