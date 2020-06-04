package pl.betweenthelines.szopp.rest.success;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.betweenthelines.szopp.security.domain.RoleName;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class LoginSuccessResponse extends SuccessResponse {

    private String login;

    private List<RoleName> userRoles;

    @Builder(builderMethodName = "loginSuccessBuilder")
    public LoginSuccessResponse(int status, LocalDateTime timestamp, String message, String login, List<RoleName> userRoles) {
        super(status, timestamp, message);
        this.login = login;
        this.userRoles = userRoles;
    }
}
