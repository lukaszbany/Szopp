package pl.betweenthelines.szopp.exception.handler.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pl.betweenthelines.szopp.exception.*;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum Exceptions {
    BAD_CREDENTIALS_EXCEPTION(BadCredentialsException.class, FORBIDDEN, "exception.message.BadCredentialsException"),
    CUSTOMER_DATA_INCOMPLETE_EXCEPTION(CustomerDataIncompleteException.class, BAD_REQUEST, "exception.message.CustomerDataIncompleteException"),
    EMPTY_ORDER_EXCEPTION(EmptyOrderException.class, BAD_REQUEST, "exception.message.EmptyOrderException"),
    FILE_PROCESSING_EXCEPTION(FileProcessingException.class, BAD_REQUEST, "exception.message.FileProcessingException"),
    INVALID_IMAGE_EXCEPTION(InvalidImageException.class, BAD_REQUEST, "exception.message.InvalidImageException"),
    INVALID_ORDER_STATUS_EXCEPTION(InvalidOrderStatusException.class, BAD_REQUEST, "exception.message.InvalidOrderStatusException"),
    INVALID_PARENT_CATEGORY_EXCEPTION(InvalidParentCategoryException.class, BAD_REQUEST, "exception.message.InvalidParentCategoryException"),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(MethodArgumentNotValidException.class, BAD_REQUEST, "exception.message.MethodArgumentNotValidException"),
    NO_SUCH_ORDER_ITEM_IN_CART_EXCEPTION(NoSuchOrderItemInCartException.class, BAD_REQUEST, "exception.message.NoSuchOrderItemInCartException"),
    NO_SUCH_USER_EXCEPTION(NoSuchUserException.class, BAD_REQUEST, "exception.message.NoSuchUserException"),
    NO_SUCH_PRODUCT_IN_CART_EXCEPTION(NoSuchProductInCartException.class, BAD_REQUEST, "exception.message.NoSuchProductInCartException"),
    NOT_FOUND_EXCEPTION(NotFoundException.class, NOT_FOUND, "exception.message.NotFoundException"),
    PRODUCT_IN_CART_NOT_AVAILABLE_ANYMORE_EXCEPTION(ProductInCartNotAvailableAnymoreException.class, BAD_REQUEST, "exception.message.ProductInCartNotAvailableAnymoreException"),
    PRODUCT_NOT_AVAILABLE_EXCEPTION(ProductNotAvailableException.class, BAD_REQUEST, "exception.message.ProductNotAvailableException"),
    SZOPP_EXCEPTION(SzoppException.class, BAD_REQUEST, "exception.message.SzoppException"),
    TOO_LARGE_IMAGE_EXCEPTION(TooLargeImageException.class, BAD_REQUEST, "exception.message.TooLargeImageException"),
    USER_ALREADY_EXISTS_EXCEPTION(UserAlreadyExistsException.class, BAD_REQUEST, "exception.message.UserAlreadyExistsException"),
    USER_HAS_SUBMITTED_ORDERS_EXCEPTION(UserHasSubmittedOrdersException.class, BAD_REQUEST, "exception.message.UserHasSubmittedOrdersException"),
    USER_NOT_LOGGED_EXCEPTION(UserNotLoggedException.class, UNAUTHORIZED, "exception.message.UserNotLoggedException");

    private Class<? extends Exception> aClass;
    private HttpStatus status;
    private String message;

    public static Exceptions get(Exception e) {
        Class<? extends Exception> actualClass = e.getClass();

        for (Exceptions exception : Exceptions.values()) {
            if (exception.getAClass().equals(actualClass)) {
                return exception;
            }
        }

        return SZOPP_EXCEPTION;
    }
}
