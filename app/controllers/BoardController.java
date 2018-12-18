package controllers;

import models.Board;
import models.HistoryItem;
import models.HistoryItem.Action;
import models.HistoryItem.Element;
import models.Team;
import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.board;

public class BoardController extends Controller {

    @Security.Authenticated(LoggedInAuthenticator.class)
    public static Result add() {
        Form<NewBoard> boardForm = Form.form(NewBoard.class).bindFromRequest();
        Team team = null;

        if(boardForm.get().teamId != null){
            team = Team.find.byId(boardForm.get().teamId);
        }

        Board board = Board.create(User.loggedInUser(), boardForm.get().name, boardForm.get().typeCode, team);
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
        String boardIDString = Form.form().bindFromRequest().get("boardID");

        Long boardId = Long.parseLong(boardIDString);
        Board boardObj = Board.find.byId(boardId);

        if (boardObj == null) {
            return badRequest(Messages.get("page.board.notFound"));
        }

        boardObj.delete();
        return redirect(controllers.routes.Application.index());
    }

    public static class NewBoard {
        @Constraints.Required
        @Constraints.MinLength(1)
        @Constraints.MaxLength(200)
        @Constraints.Pattern(value = "^[A-Za-z0-9- ]+$", message = "page.validation.onlyAlphanumericAndSpace")
        public String name;
    }
}
