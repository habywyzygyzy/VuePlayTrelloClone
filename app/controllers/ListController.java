package controllers;

import com.avaje.ebean.Ebean;
import models.List;
import models.Board;
import models.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.stream.Stream;

public class ListController extends Controller {

    public static Result add() {
        Form<NewList> boardForm = Form.form(NewList.class).bindFromRequest();

        NewList newList = boardForm.get();
        Board board = Board.find.byId(newList.boardID);
        List List = List.create(board, newList.name);

        return ok();
    }

    public static Result delete() {
        String ListIDString = Form.form().bindFromRequest().get("ListID");
        if (!checkStringIsLongValidator.isValid(ListIDString)) {
            return badRequest(Messages.get("page.badRequest"));
        }

        Long ListID = Long.parseLong(ListIDString);
        List ListObj = List.find.byId(ListID);

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
