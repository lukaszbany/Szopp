package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException() {
    }

    public InvalidOrderStatusException(String message) {
        super(message);
    }

}
