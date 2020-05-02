package pl.betweenthelines.szopp.exception;

public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException() {
    }

    public ProductNotAvailableException(String message) {
        super(message);
    }

}
