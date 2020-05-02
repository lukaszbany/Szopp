package pl.betweenthelines.szopp.exception;

public class EmptyOrderException extends RuntimeException {

    public EmptyOrderException() {
    }

    public EmptyOrderException(String message) {
        super(message);
    }

}
