package pl.betweenthelines.szopp.exception;

public class CannotSetParentCategoryException extends RuntimeException {

    public CannotSetParentCategoryException() {
    }

    public CannotSetParentCategoryException(String message) {
        super(message);
    }

}
