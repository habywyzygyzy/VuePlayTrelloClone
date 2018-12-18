package controllers;

import com.avaje.ebean.Ebean;
import models.BList;
import models.Card;
import models.HistoryItem;
import models.HistoryItem.Action;
import models.HistoryItem.Element;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.authenticators.LoggedInAuthenticator;
import utils.other.ValidationErrorsHelper;
import utils.validators.CheckBListOwnerValidator;
import utils.validators.CheckCardOwnerValidator;
import utils.validators.CheckStringIsLongValidator;

public class CardController extends Controller {

    public static Result add() {
        Form<NewCard> cardForm = Form.form(NewCard.class).bindFromRequest();

        if (cardForm.hasErrors()) {
            return badRequest(
                    new ValidationErrorsHelper("page.board.card.add.label.", cardForm).getWithNLAsBR());

        } else {

            NewCard newCard = cardForm.get();
            BList bList = BList.find.byId(newCard.listId);
            Card card = Card.create(bList, newCard.name);

            HistoryItem.create(card.list.board, Element.CARD, Action.CREATED, card.name, bList.name);
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
        if (!checkCardOwnerValidator.isValid(card.id)) {
            return unauthorized(Messages.get("page.unauthorized"));
        }

        HistoryItem.create(card.list.board, Element.CARD, Action.DELETED, card.name, null);

        card.delete();
        return redirect(controllers.routes.Application.index());
    }


    public static class NewCard {
        @Constraints.Required
        @Constraints.ValidateWith(CheckBListOwnerValidator.class)
        public Long listId;

        @Constraints.Required
        @Constraints.MinLength(1)
        @Constraints.MaxLength(100)
        public String name;
    }
}
