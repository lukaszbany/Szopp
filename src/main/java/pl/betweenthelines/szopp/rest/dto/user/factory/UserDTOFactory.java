package pl.betweenthelines.szopp.rest.dto.user.factory;

import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.rest.dto.user.UserDTO;
import pl.betweenthelines.szopp.security.domain.User;

@Component
public class UserDTOFactory {

    public UserDTO buildUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
