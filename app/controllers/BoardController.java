package controllers;

import models.Board;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

import static play.data.validation.Constraints.*;

public class BoardController extends Controller {

    public static Result add() {
        FormFactory formFactory = null;
        Form<NewBoard> boardForm = formFactory.form(NewBoard.class).bindFromRequest();

        Board board = Board.create(User.loggedInUser(), boardForm.get().name);
        return ok();
    }

    public static Result show(Long boardId) {
        Board foundBoard = Board.find.byId(boardId);

        if (foundBoard == null) {
            return badRequest(Messages.get("page.board.notFound"));
        }

        return ok(board.render(foundBoard));
    }

    public static Result delete() {
        FormFactory formFactory = null;
        String boardIDString = formFactory.form().bindFromRequest().get("boardID");

        Long boardId = Long.parseLong(boardIDString);
        Board boardObj = Board.find.byId(boardId);

        if (boardObj == null) {
            return badRequest(Messages.get("page.board.notFound"));
        }

        boardObj.delete();
        return redirect(controllers.routes.Application.index());
    }

    public static class NewBoard {
        @Required
        @MinLength(1)
        @MaxLength(200)
        @Pattern(value = "^[A-Za-z0-9- ]+$", message = "page.validation.onlyAlphanumericAndSpace")
        public String name;
    }
}
