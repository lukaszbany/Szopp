package pl.betweenthelines.szopp.exception;

public class CategoryContainsProductException extends RuntimeException {

    public CategoryContainsProductException() {
    }

    public CategoryContainsProductException(String message) {
        super(message);
    }

}
