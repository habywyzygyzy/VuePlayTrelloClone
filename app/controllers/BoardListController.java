package controllers;

import models.BoardList;
import models.Board;
import models.Card;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import io.ebean.*;

public class BoardListController extends Controller {

    public static Result add() {
        Form<NewList> boardForm = Form.form(NewList.class).bindFromRequest();

        NewList newList = boardForm.get();
        Board board = Board.find.byId(newList.boardID);
        BoardList list = BoardList.create(board, newList.name);

        return ok();
    }

    public static Result delete() {
        String ListIDString = Form.form().bindFromRequest().get("ListID");
        if (!checkStringIsLongValidator.isValid(ListIDString)) {
            return badRequest(Messages.get("page.badRequest"));
        }

        Long ListID = Long.parseLong(ListIDString);
        BoardList ListObj = BoardList.find.byId(ListID);

        if (ListObj == null) {
            return badRequest(Messages.get("page.board.notFound"));
        }

        ListObj.delete();
        return redirect(controllers.routes.Application.index());
    }
   
    public static class NewList {
        @Constraints.Required
        public Long boardID;

        @Constraints.Required
        @Constraints.MinLength(1)
        @Constraints.MaxLength(200)
        @Constraints.Pattern(value = "^[A-Za-z0-9- ]+$", message = "page.validation.onlyAlphanumericAndSpace")
        public String name;
    }
}
