package pl.betweenthelines.szopp.exception;

public class UserHasSubmittedOrdersException extends RuntimeException {

    public UserHasSubmittedOrdersException() {
    }

    public UserHasSubmittedOrdersException(String message) {
        super(message);
    }

}
