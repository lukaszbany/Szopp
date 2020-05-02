package pl.betweenthelines.szopp.rest.dto.authentication;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class LoginDTO {

    @NotBlank(message = "{validation.username.blank}")
    @Size(min = 5, max = 50, message = "{validation.username.size}")
    private String username;

    @NotBlank(message = "{validation.password.blank}")
    @Size(min = 5, message = "{validation.password.size}")
    private String password;

}
