package controllers;

import models.*;
import models.BoardList;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import utils.other.ValidationErrorsHelper;

public class CardController extends Controller {

    public static Result add() {
        Form<NewCard> cardForm = Form.form(NewCard.class).bindFromRequest();

        if (cardForm.hasErrors()) {
            return badRequest(
                    new ValidationErrorsHelper("page.board.card.add.label.", cardForm).getWithNLAsBR());

        } else {

            NewCard newCard = cardForm.get();
            BoardList bList = BoardList.find.byId(newCard.listId);
            Card card = Card.create(bList, newCard.name);
        }

        return ok();
    }

    public static Result delete() {
        String cardIdAsString = Form.form().bindFromRequest().get("cardId");
        if (!checkStringIsLongValidator.isValid(cardIdAsString)) {
            return badRequest(Messages.get("page.badRequest"));
        }

        Long cardId = Long.parseLong(cardIdAsString);
        Card card = Card.find.byId(cardId);

        if (card == null) {
            return badRequest(Messages.get("page.board.notFound"));
        }

        card.delete();
        return redirect(controllers.routes.Application.index());
    }


    public static class NewCard {
        @Constraints.Required
        public Long listId;

        @Constraints.Required
        @Constraints.MinLength(1)
        @Constraints.MaxLength(100)
        public String name;
    }
}
