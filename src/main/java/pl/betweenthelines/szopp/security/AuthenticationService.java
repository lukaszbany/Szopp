package pl.betweenthelines.szopp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.exception.SzoppException;
import pl.betweenthelines.szopp.exception.UserAlreadyExistsException;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.RoleName;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.RoleRepository;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;
import pl.betweenthelines.szopp.service.customer.CustomerInSessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

import static pl.betweenthelines.szopp.security.domain.RoleName.REGISTERED_USER;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerInSessionService customerInSessionService;

    @Transactional
    public void registerUser(String username, String password) {
        validateNewUsername(username);
        saveUser(username, password);
    }

    private void validateNewUsername(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        existingUser.ifPresent(this::throwUserAlreadyExist);
    }

    private void throwUserAlreadyExist(User user) {
        throw new UserAlreadyExistsException();
    }

    private void saveUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role role = findRole(REGISTERED_USER);
        Customer customer = customerInSessionService.getFromSessionOrCreate();
        if (customer.getUser() != null) {
            customer = customerInSessionService.createCustomer();
        }

        User user = createUser(username, encodedPassword, role, customer);
        customer.setUser(user);
    }

    private User createUser(String username, String encodedPassword, Role role, Customer customer) {
        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .role(role)
                .customer(customer)
                .build();

        return userRepository.save(user);
    }

    private Role findRole(RoleName name) {
        Optional<Role> role = roleRepository.findByName(name);

        return role.orElseThrow(SzoppException::new);
    }


    private User tryToGetLoggedUser() {
        return findLoggedUser()
                .orElseThrow(SzoppException::new);
    }

    public Optional<User> findLoggedUser() {
        UserDetails userDetails = getUserDetails();
        String username = userDetails.getUsername();

        return userRepository.findByUsername(username);
    }

    private UserDetails getUserDetails() {
        Authentication auth = tryToGetAuthentication();
        return (UserDetails) auth.getPrincipal();
    }

    private Authentication tryToGetAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new SzoppException();
        }

        return auth;
    }

    public void login(HttpServletRequest request, String username, String password) throws ServletException {
        request.login(username, password);
        updateCustomerInSession();
    }

    private void updateCustomerInSession() {
        User user = tryToGetLoggedUser();
        if (user.getCustomer() != null) {
            customerInSessionService.saveCustomerInSession(user.getCustomer());
        }
    }
}
