package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class UserNotLoggedException extends RuntimeException {

    public UserNotLoggedException() {
    }

    public UserNotLoggedException(String message) {
        super(message);
    }

}
