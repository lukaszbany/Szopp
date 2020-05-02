package pl.betweenthelines.szopp.exception;

public class TooLargeImageException extends RuntimeException {

    public TooLargeImageException() {
    }

    public TooLargeImageException(String message) {
        super(message);
    }

}
