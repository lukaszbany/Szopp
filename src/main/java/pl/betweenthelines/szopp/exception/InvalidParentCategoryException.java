package pl.betweenthelines.szopp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidParentCategoryException extends SzoppException {

    public InvalidParentCategoryException(String message) {
        super(message);
    }

}
