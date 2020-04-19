package pl.betweenthelines.szopp.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEndpoint {

    private Logger log = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/all2", method = RequestMethod.GET)
    public List<User> findAll2() {
        return userRepository.findAll();
    }

}
