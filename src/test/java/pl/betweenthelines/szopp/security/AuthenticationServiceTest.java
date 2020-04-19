package pl.betweenthelines.szopp.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.betweenthelines.szopp.exception.UserAlreadyExists;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldThrowIfUsernameExist() {
        givenExistingUser();

        assertThrows(UserAlreadyExists.class, () -> authenticationService.registerUser(USERNAME, PASSWORD));
    }

    private void givenExistingUser() {
        User existingUser = new User();
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(Optional.of(existingUser));
    }

    @Test
    void shouldSaveUser() {
        authenticationService.registerUser(USERNAME, PASSWORD);

        User savedUser = thenUserSaved();
        assertEquals(USERNAME, savedUser.getUsername());
        assertEquals(PASSWORD, savedUser.getPassword());
    }

    private User thenUserSaved() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock).save(captor.capture());

        return captor.getValue();
    }
}