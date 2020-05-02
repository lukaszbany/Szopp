package pl.betweenthelines.szopp.exception.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.betweenthelines.szopp.exception.handler.factory.ApiErrorFactory;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private ApiErrorFactory apiErrorFactory;

    @ExceptionHandler
    public ResponseEntity<ApiError> handleException(HttpServletRequest request, Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            return buildResponseEntityForValidationError((MethodArgumentNotValidException) e);
        }

        return buildResponseEntity(e);
    }

    private ResponseEntity<ApiError> buildResponseEntityForValidationError(MethodArgumentNotValidException e) {
        ApiError apiError = apiErrorFactory.buildApiValidationError(e);

        return ResponseEntity
                .status(HttpStatus.valueOf(apiError.getStatus()))
                .body(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(Exception e) {
        ApiError apiError = apiErrorFactory.buildApiError(e);

        return ResponseEntity
                .status(HttpStatus.valueOf(apiError.getStatus()))
                .body(apiError);
    }
}
