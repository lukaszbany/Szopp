package pl.betweenthelines.szopp.exception.handler.factory;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pl.betweenthelines.szopp.exception.handler.ValidationError;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationErrorFactory {

    public List<ValidationError> buildValidationErrors(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();

        return errors.stream()
                .map(this::buildValidationError)
                .collect(Collectors.toList());
    }

    private ValidationError buildValidationError(ObjectError error) {
        if (error instanceof FieldError) {
            return buildFieldErrorData(error);
        }

        return buildStandardErrorData(error);
    }

    private ValidationError buildFieldErrorData(ObjectError error) {
        FieldError fieldError = (FieldError) error;

        return ValidationError.builder()
                .field(fieldError.getField())
                .message(error.getDefaultMessage())
                .rejectedValue((fieldError).getRejectedValue())
                .build();
    }

    private ValidationError buildStandardErrorData(ObjectError error) {
        return ValidationError.builder()
                .message(error.getDefaultMessage())
                .build();
    }
}
