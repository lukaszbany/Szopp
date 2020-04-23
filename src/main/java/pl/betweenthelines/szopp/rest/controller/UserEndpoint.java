package pl.betweenthelines.szopp.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserEndpoint {

    private Logger log = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
