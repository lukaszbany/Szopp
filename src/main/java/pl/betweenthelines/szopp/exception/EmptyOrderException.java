package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class EmptyOrderException extends RuntimeException {

    public EmptyOrderException() {
    }

    public EmptyOrderException(String message) {
        super(message);
    }

}
