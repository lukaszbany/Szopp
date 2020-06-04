package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.rest.dto.user.UserDTO;
import pl.betweenthelines.szopp.rest.dto.user.factory.UserDTOFactory;
import pl.betweenthelines.szopp.rest.success.SuccessResponse;
import pl.betweenthelines.szopp.rest.success.SuccessResponseFactory;
import pl.betweenthelines.szopp.security.domain.RoleName;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;
import pl.betweenthelines.szopp.service.user.UserService;

import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/users")
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOFactory userDTOFactory;

    @Autowired
    private SuccessResponseFactory successResponseFactory;

    @RequestMapping(method = GET)
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        return userDTOFactory.buildUserDTOs(users);
    }

    @RequestMapping(method = PUT, path = "/{userId}")
    public ResponseEntity<SuccessResponse> modifyUser(@PathVariable(name = "userId") Long userId,
                                                      @RequestBody @NotBlank String roleName) {

        RoleName newRoleName = RoleName.valueOf(roleName);
        userService.updateUserRole(userId, newRoleName);

        return successResponseFactory.buildSuccessResponseEntity("success.user.role.update");
    }

    @RequestMapping(method = DELETE, path = "/{userId}")
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable(name = "userId") Long userId) {
        userService.deleteUser(userId);

        return successResponseFactory.buildSuccessResponseEntity("success.user.delete");
    }

}
