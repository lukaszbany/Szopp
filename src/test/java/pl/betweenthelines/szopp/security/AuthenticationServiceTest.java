package pl.betweenthelines.szopp.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.exception.UserAlreadyExistsException;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.RoleRepository;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;
import pl.betweenthelines.szopp.service.customer.CustomerInSessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.betweenthelines.szopp.security.domain.RoleName.REGISTERED_USER;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "dsfhslkfhsgfdhgsoifhsdfsd";

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private RoleRepository roleRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private CustomerInSessionService customerInSessionServiceMock;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private Role roleMock;

    @Mock
    private Customer customerMock;

    @Mock
    private HttpServletRequest requestMock;

    @Test
    void shouldThrowIfUsernameExist() {
        givenExistingUser();

        assertThrows(UserAlreadyExistsException.class, () -> authenticationService.registerUser(USERNAME, PASSWORD));
    }

    private void givenExistingUser() {
        User existingUser = new User();
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(Optional.of(existingUser));
    }

    @Test
    void shouldSaveUser() {
        when(roleRepositoryMock.findByName(REGISTERED_USER)).thenReturn(Optional.of(roleMock));
        when(customerInSessionServiceMock.getFromSessionOrCreate()).thenReturn(customerMock);
        when(passwordEncoderMock.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        authenticationService.registerUser(USERNAME, PASSWORD);

        User savedUser = thenUserSaved();
        assertEquals(USERNAME, savedUser.getUsername());
        assertEquals(ENCODED_PASSWORD, savedUser.getPassword());
    }

    private User thenUserSaved() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock).save(captor.capture());

        return captor.getValue();
    }

//    @Test
//    void shouldLogin() throws ServletException {
//
//        authenticationService.login(requestMock, USERNAME, PASSWORD);
//
//
//    }
}