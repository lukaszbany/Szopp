package pl.betweenthelines.szopp.exception;

public class ProductHasOrderException extends RuntimeException {

    public ProductHasOrderException() {
    }

    public ProductHasOrderException(String message) {
        super(message);
    }

}
