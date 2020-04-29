package pl.betweenthelines.szopp.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class FileProcessingException extends RuntimeException {

    public FileProcessingException() {
    }

    public FileProcessingException(String message) {
        super(message);
    }

}
