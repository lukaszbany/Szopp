package pl.betweenthelines.szopp.exception.handler.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pl.betweenthelines.szopp.exception.handler.ApiError;
import pl.betweenthelines.szopp.exception.handler.ValidationError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Component
public class ApiErrorFactory {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    public ApiError buildApiError(Exception e) {
        Exceptions exception = Exceptions.get(e);

        return ApiError.builder()
                .status(exception.getStatus().value())
                .message(getMessage(exception))
                .timestamp(LocalDateTime.now())
                .build();
    }

    private String getMessage(Exceptions exception) {
        return messageSource.getMessage(exception.getMessage(), null, Locale.getDefault());
    }

    public ApiError buildApiValidationError(MethodArgumentNotValidException e) {
        ApiError apiError = buildApiError(e);
        apiError.setValidationErrors(getValidationErrors(e));

        return apiError;
    }

    private List<ValidationError> getValidationErrors(MethodArgumentNotValidException e) {
        return validationErrorFactory.buildValidationErrors(e);
    }
}
