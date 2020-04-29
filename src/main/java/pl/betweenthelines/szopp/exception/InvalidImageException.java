package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InvalidImageException extends RuntimeException {

    public InvalidImageException() {
    }

    public InvalidImageException(String message) {
        super(message);
    }

}
