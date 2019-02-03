package controllers;

import models.BoardList;
import models.Card;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import utils.other.ValidationErrorsHelper;
import utils.validators.CheckStringIsLongValidator;

import javax.inject.Inject;

public class CardController extends Controller {

    @Inject
    static FormFactory formFactory;

    public static Result add() {
        Form<NewCard> cardForm = formFactory.form(NewCard.class).bindFromRequest();

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
        CheckStringIsLongValidator checkStringIsLongValidator = new CheckStringIsLongValidator();
        String cardIdAsString = formFactory.form().bindFromRequest().get("cardId");
        if (!checkStringIsLongValidator.isValid(cardIdAsString)) {
            return badRequest();
        }

        Long cardId = Long.parseLong(cardIdAsString);
        Card card = Card.find.byId(cardId);

        if (card == null) {
            return badRequest();
        }

        card.delete();
        return redirect(controllers.routes.HomeController.index());
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
