package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.rest.dto.user.UserDTO;
import pl.betweenthelines.szopp.rest.dto.user.factory.UserDTOFactory;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTOFactory userDTOFactory;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        return userDTOFactory.buildUserDTOs(users);
    }

}
