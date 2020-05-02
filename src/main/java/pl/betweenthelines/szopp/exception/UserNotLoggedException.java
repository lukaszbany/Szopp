package pl.betweenthelines.szopp.exception;

public class UserNotLoggedException extends RuntimeException {

    public UserNotLoggedException() {
    }

    public UserNotLoggedException(String message) {
        super(message);
    }

}
