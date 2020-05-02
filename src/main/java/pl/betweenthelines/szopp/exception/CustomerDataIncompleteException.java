package pl.betweenthelines.szopp.exception;

public class CustomerDataIncompleteException extends RuntimeException {

    public CustomerDataIncompleteException() {
    }

    public CustomerDataIncompleteException(String message) {
        super(message);
    }

}
