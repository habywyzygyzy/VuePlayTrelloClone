package utils.validators;

import models.Board;
import models.BoardList;
import models.User;
import play.data.validation.Constraints;
import play.libs.F;

import java.util.Objects;

public class CheckBoardListOwnerValidator extends Constraints.Validator<Long> {

    @Override
    public boolean isValid(Long boardListID) {
        return boardListID == null ||
                (User.isLoggedIn() &&
                        (Objects.equals(BoardList.find.byId(boardListID).board.owner.id, User.loggedInUser().id))||
                        (BoardList.find.byId(boardListID).board.owner == User.loggedInUser()));

    }

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<>("page.validation.unauthorized", new Object[0]);
    }
}
