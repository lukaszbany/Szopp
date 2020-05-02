package pl.betweenthelines.szopp.exception;

public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException() {
    }

    public InvalidOrderStatusException(String message) {
        super(message);
    }

}
