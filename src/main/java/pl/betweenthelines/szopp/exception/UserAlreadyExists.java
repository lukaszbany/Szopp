package pl.betweenthelines.szopp.exception;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@NoArgsConstructor
@ResponseStatus(BAD_REQUEST)
public class UserAlreadyExists extends SzoppException {

    public UserAlreadyExists(String message) {
        super(message);
    }

}
