package pl.betweenthelines.szopp.exception;

public class NoSuchImageException extends RuntimeException {

    public NoSuchImageException() {
    }

    public NoSuchImageException(String message) {
        super(message);
    }

}
