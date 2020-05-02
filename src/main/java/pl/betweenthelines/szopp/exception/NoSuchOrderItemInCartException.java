package pl.betweenthelines.szopp.exception;

public class NoSuchOrderItemInCartException extends RuntimeException {

    public NoSuchOrderItemInCartException() {
    }

    public NoSuchOrderItemInCartException(String message) {
        super(message);
    }

}
