package pl.betweenthelines.szopp.rest.dto.authentication;

import lombok.Getter;
import pl.betweenthelines.szopp.rest.dto.validation.PasswordsMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@PasswordsMatches
public class RegistrationDTO {

    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank
    @Size(min = 5)
    private String password;

    @NotBlank
    @Size(min = 5)
    private String confirmPassword;

}
