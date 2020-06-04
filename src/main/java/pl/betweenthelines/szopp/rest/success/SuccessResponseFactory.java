package pl.betweenthelines.szopp.rest.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.RoleName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SuccessResponseFactory {

    @Autowired
    private MessageSource messageSource;

    public ResponseEntity<SuccessResponse> buildSuccessResponseEntity(String message) {
        SuccessResponse successResponse = buildSuccessResponse(message);

        return ResponseEntity.ok(successResponse);
    }

    private SuccessResponse buildSuccessResponse(String message) {
        return SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(getMessage(message))
                .build();
    }

    private String getMessage(String message) {
        return messageSource.getMessage(message, null, Locale.getDefault());
    }

    public ResponseEntity<LoginSuccessResponse> buildLoginSuccessResponseEntity(String message, String login, Set<Role> userRoles) {
        LoginSuccessResponse successResponse = buildLoginSuccessResponse(message, login, userRoles);

        return ResponseEntity.ok(successResponse);
    }

    private LoginSuccessResponse buildLoginSuccessResponse(String message, String login, Set<Role> userRoles) {
        return LoginSuccessResponse.loginSuccessBuilder()
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .message(getMessage(message))
                .login(login)
                .userRoles(getRoles(userRoles))
                .build();
    }

    private List<RoleName> getRoles(Set<Role> userRoles) {
        return userRoles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
