package pl.betweenthelines.szopp.service.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderStatus;
import pl.betweenthelines.szopp.domain.repository.OrderRepository;
import pl.betweenthelines.szopp.exception.NotFoundException;
import pl.betweenthelines.szopp.exception.UserNotLoggedException;
import pl.betweenthelines.szopp.security.AuthenticationService;
import pl.betweenthelines.szopp.security.domain.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static pl.betweenthelines.szopp.domain.OrderStatus.NEW;
import static pl.betweenthelines.szopp.domain.OrderStatus.SENT;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Transactional
    public List<Order> getOrders(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return orderRepository.findAllByStatusNot(NEW);
        }

        return orderRepository.findAllByStatus(orderStatus);
    }

    @Transactional
    public List<Order> getCustomerOrders() {
        Customer currentCustomer = getLoggedCustomer();

        return orderRepository.findAllByCustomerAndStatusNot(currentCustomer, NEW);
    }

    private Customer getLoggedCustomer() {
        User loggedUser = authenticationService.findLoggedUser()
                .orElseThrow(UserNotLoggedException::new);

        Customer currentCustomer = loggedUser.getCustomer();
        if (currentCustomer == null) {
            log.info("Customer requested for order of other customer!");
            throw new NotFoundException();
        }

        return currentCustomer;
    }

    @Transactional
    public Order changeStatus(Long orderId, OrderStatus status) {
        Order order = getOrder(orderId, status);

        changeOrderStatus(order, status);

        return order;
    }

    private void changeOrderStatus(Order order, OrderStatus status) {
        if (status.equals(SENT)) {
            order.setDateSent(new Date());
        }

        log.info("Changing status of order {} to {}", order.getId(), status.name());
        order.statusTransition(status);
    }

    private Order getOrder(Long orderId, OrderStatus status) {
        return orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public List<Order> getCustomerOrders(Customer customer) {
        return orderRepository.findAllByCustomerAndStatusNot(customer, NEW);
    }

}
