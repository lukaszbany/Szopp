package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class NoSuchOrderItemInCartException extends RuntimeException {

    public NoSuchOrderItemInCartException() {
    }

    public NoSuchOrderItemInCartException(String message) {
        super(message);
    }

}
