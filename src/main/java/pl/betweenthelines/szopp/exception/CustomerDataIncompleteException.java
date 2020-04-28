package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class CustomerDataIncompleteException extends RuntimeException {

    public CustomerDataIncompleteException() {
    }

    public CustomerDataIncompleteException(String message) {
        super(message);
    }

}
