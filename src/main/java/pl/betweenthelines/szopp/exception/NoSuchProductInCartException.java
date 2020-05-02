package pl.betweenthelines.szopp.exception;

public class NoSuchProductInCartException extends RuntimeException {

    public NoSuchProductInCartException() {
    }

    public NoSuchProductInCartException(String message) {
        super(message);
    }

}
