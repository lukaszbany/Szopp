package pl.betweenthelines.szopp.rest.dto.validation;

import pl.betweenthelines.szopp.rest.dto.authentication.RegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchesValidator implements ConstraintValidator<PasswordsMatches, Object> {

    @Override
    public void initialize(PasswordsMatches constraint) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegistrationDTO registrationDTO = (RegistrationDTO) obj;
        String password = registrationDTO.getPassword();
        String passwordConfirmation = registrationDTO.getConfirmPassword();

        return password.equals(passwordConfirmation);
    }
}
