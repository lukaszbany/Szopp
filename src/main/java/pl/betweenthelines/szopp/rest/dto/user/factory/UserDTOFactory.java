package pl.betweenthelines.szopp.rest.dto.user.factory;

import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.rest.dto.user.UserDTO;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.RoleName;
import pl.betweenthelines.szopp.security.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOFactory {

    public List<UserDTO> buildUserDTOs(List<User> users) {
        return users.stream()
                .map(this::buildUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO buildUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roleNames(getRolenames(user))
                .build();
    }

    private List<RoleName> getRolenames(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
