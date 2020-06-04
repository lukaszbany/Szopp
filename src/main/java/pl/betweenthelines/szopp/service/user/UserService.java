package pl.betweenthelines.szopp.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.exception.NoSuchUserException;
import pl.betweenthelines.szopp.exception.UserHasSubmittedOrdersException;
import pl.betweenthelines.szopp.security.AuthenticationService;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.RoleName;
import pl.betweenthelines.szopp.security.domain.User;
import pl.betweenthelines.szopp.security.domain.repository.UserRepository;
import pl.betweenthelines.szopp.service.order.OrderService;

import javax.transaction.Transactional;
import java.util.Optional;

import static pl.betweenthelines.szopp.security.domain.RoleName.REGISTERED_USER;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;

    @Transactional
    public void updateUserRole(Long userId, RoleName newRoleName) {
        User user = tryToGetUser(userId);
        tryToDetachCustomer(user, newRoleName);

        updateUserRole(user, newRoleName);
    }

    private void tryToDetachCustomer(User user, RoleName newRoleName) {
        if (!newRoleName.equals(REGISTERED_USER)) {
            Optional.ofNullable(user.getCustomer())
                    .ifPresent(this::tryToDetachCustomer);

            user.setCustomer(null);
        }
    }

    private void tryToDetachCustomer(Customer customer) {
        if (hasSubmittedOrders(customer)) {
            throw new UserHasSubmittedOrdersException();
        }

        customer.setUser(null);
    }

    private boolean hasSubmittedOrders(Customer customer) {
        return !orderService
                .getCustomerOrders(customer)
                .isEmpty();
    }

    private User tryToGetUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NoSuchUserException::new);
    }

    private void updateUserRole(User user, RoleName newRoleName) {
        Role newRole = authenticationService.findRole(newRoleName);

        user.getRoles().clear();
        user.getRoles().add(newRole);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = tryToGetUser(userId);
        Optional.ofNullable(user.getCustomer())
                .ifPresent(this::detachCustomer);

        user.setCustomer(null);

        userRepository.delete(user);
    }

    private void detachCustomer(Customer customer) {
        customer.setUser(null);
    }
}
