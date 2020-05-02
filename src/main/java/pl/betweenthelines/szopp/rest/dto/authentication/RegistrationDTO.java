package pl.betweenthelines.szopp.rest.dto.authentication;

import lombok.Getter;
import pl.betweenthelines.szopp.rest.dto.validation.PasswordsMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@PasswordsMatches(message = "{validation.password.confirmation.match}")
public class RegistrationDTO {

    @NotBlank(message = "{validation.username.blank}")
    @Size(min = 5, max = 50, message = "{validation.username.size}")
    private String username;

    @NotBlank(message = "{validation.password.blank}")
    @Size(min = 5, message = "{validation.password.size}")
    private String password;

    @NotBlank(message = "{validation.password.blank}")
    @Size(min = 5, message = "{validation.password.size}")
    private String confirmPassword;

}
