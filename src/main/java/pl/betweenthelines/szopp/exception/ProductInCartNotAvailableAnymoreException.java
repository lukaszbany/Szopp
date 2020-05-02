package pl.betweenthelines.szopp.exception;

public class ProductInCartNotAvailableAnymoreException extends RuntimeException {

    public ProductInCartNotAvailableAnymoreException() {
    }

    public ProductInCartNotAvailableAnymoreException(String message) {
        super(message);
    }

}
