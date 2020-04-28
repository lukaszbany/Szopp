package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException() {
    }

    public ProductNotAvailableException(String message) {
        super(message);
    }

}
