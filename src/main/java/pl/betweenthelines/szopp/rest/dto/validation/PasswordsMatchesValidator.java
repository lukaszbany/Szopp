package pl.betweenthelines.szopp.rest.dto.validation;

import pl.betweenthelines.szopp.rest.dto.authentication.RegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchesValidator implements ConstraintValidator<PasswordsMatches, Object> {

    private String message;

    @Override
    public void initialize(PasswordsMatches constraint) {
        message = constraint.message();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegistrationDTO registrationDTO = (RegistrationDTO) obj;
        String password = registrationDTO.getPassword();
        String passwordConfirmation = registrationDTO.getConfirmPassword();

        boolean valid = password != null && password.equals(passwordConfirmation);

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
