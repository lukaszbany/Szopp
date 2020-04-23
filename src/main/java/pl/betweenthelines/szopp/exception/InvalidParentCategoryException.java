package pl.betweenthelines.szopp.exception;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@NoArgsConstructor
@ResponseStatus(BAD_REQUEST)
public class InvalidParentCategoryException extends SzoppException {

    public InvalidParentCategoryException(String message) {
        super(message);
    }

}
