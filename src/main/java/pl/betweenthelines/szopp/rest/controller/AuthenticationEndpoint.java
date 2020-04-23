package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.exception.SzoppException;
import pl.betweenthelines.szopp.rest.dto.authentication.LoginDTO;
import pl.betweenthelines.szopp.rest.dto.authentication.RegistrationDTO;
import pl.betweenthelines.szopp.security.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/auth")
public class AuthenticationEndpoint {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/login", method = POST)
    public String login(HttpServletRequest request,
                        @RequestBody @Valid LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        try {
            authenticationService.login(request, username, password);

        } catch (ServletException e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return "OK";
    }

    @RequestMapping(value = "/register", method = POST)
    public String register(@RequestBody @Valid RegistrationDTO registrationDTO) {
        String username = registrationDTO.getUsername();
        String password = registrationDTO.getPassword();

        authenticationService.registerUser(username, password);

        return "OK";
    }

    @RequestMapping(value = "/logout", method = POST)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new SzoppException();
        }

        new SecurityContextLogoutHandler().logout(request, response, auth);
        return "OK";
    }
}
