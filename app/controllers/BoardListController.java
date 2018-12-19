package controllers;

import models.Board;
import models.BoardList;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import utils.validators.CheckStringIsLongValidator;

import javax.inject.Inject;

public class BoardListController extends Controller {

    @Inject
    static FormFactory formFactory;

    public static Result add() {
        Form<NewList> boardForm = formFactory.form(NewList.class).bindFromRequest();

        NewList newList = boardForm.get();
        Board board = Board.find.byId(newList.boardID);
        BoardList list = BoardList.create(board, newList.name);

        return ok();
    }

    public static Result delete() {
        CheckStringIsLongValidator validator = new CheckStringIsLongValidator();
        String ListIDString = formFactory.form().bindFromRequest().get("ListID");
        if (!validator.isValid(ListIDString)) {
            return badRequest();
        }

        Long ListID = Long.parseLong(ListIDString);
        BoardList ListObj = BoardList.find.byId(ListID);

        if (ListObj == null) {
            return badRequest();
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
