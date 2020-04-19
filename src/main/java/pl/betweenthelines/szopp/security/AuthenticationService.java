package pl.betweenthelines.szopp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.exception.SzoppException;
import pl.betweenthelines.szopp.exception.UserAlreadyExists;
import pl.betweenthelines.szopp.rest.controller.UserEndpoint;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.RoleName;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.RoleRepository;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import static pl.betweenthelines.szopp.security.domain.RoleName.REGISTERED_USER;

@Service
public class AuthenticationService {

    private Logger log = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        throw new UserAlreadyExists();
    }

    private void saveUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role role = findRole(REGISTERED_USER);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .role(role)
                .build();

        userRepository.save(user);
    }

    private Role findRole(RoleName name) {
        Optional<Role> role = roleRepository.findByName(name);

        return role.orElseThrow(SzoppException::new);
    }

}
