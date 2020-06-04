package pl.betweenthelines.szopp.service.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.repository.OrderRepository;
import pl.betweenthelines.szopp.service.customer.CustomerInSessionService;
import pl.betweenthelines.szopp.session.SessionAttributes;

import javax.transaction.Transactional;
import java.util.Optional;

import static pl.betweenthelines.szopp.domain.OrderStatus.NEW;

@Slf4j
@Service
public class OrderInSessionService {

    @Autowired
    private SessionAttributes sessionAttributes;

    @Autowired
    private CustomerInSessionService customerInSessionService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order getFromSessionOrCreate() {
        Long orderId = sessionAttributes.getOrderId();

        return Optional.ofNullable(orderId)
                .flatMap(this::findNewOrderById)
                .filter(this::isOwnedByCustomerInSession)
                .orElseGet(this::getFromLoggedCustomerOrCreate);
    }

    private Optional<Order> findNewOrderById(Long orderId) {
        return orderRepository.findByIdAndStatus(orderId, NEW);
    }

    private boolean isOwnedByCustomerInSession(Order orderInSession) {
        Customer customerInSession = customerInSessionService.getFromSessionOrCreate();
        boolean isOwnedByCustomerInSession = orderInSession.getCustomer().equals(customerInSession);
        log.info("Order {} is owned by customer {}: {}",
                orderInSession.getId(), customerInSession.getId(), isOwnedByCustomerInSession);

        return isOwnedByCustomerInSession;
    }

    private Order getFromLoggedCustomerOrCreate() {
        Customer customerInSession = customerInSessionService.getFromSessionOrCreate();

        return Optional.ofNullable(customerInSession)
                .filter(Customer::isLogged)
                .flatMap(this::findCartByCustomer)
                .orElseGet(this::createOrder);
    }

    private Optional<Order> findCartByCustomer(Customer customerInSession) {
        log.debug("Searching for cart saved for customer {}.", customerInSession.getId());
        Optional<Order> loggedCustomerCart = orderRepository.findFirstByCustomerOrderByIdDesc(customerInSession)
                .filter(this::hasNewStatus);
        loggedCustomerCart.ifPresent(this::saveOrderInSession);

        return loggedCustomerCart;
    }

    private boolean hasNewStatus(Order order) {
        return NEW.equals(order.getStatus());
    }

    private Order createOrder() {
        Customer customer = customerInSessionService.getFromSessionOrCreate();
        Order order = saveNewOrder(customer);
        log.debug("Created new order {} for customer {}.", order.getId(), customer.getId());
        saveOrderInSession(order);

        return order;
    }

    private Order saveNewOrder(Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);

        return orderRepository.save(order);
    }

    private void saveOrderInSession(Order order) {
        if (order != null) {
            sessionAttributes.setOrderId(order.getId());
        }
    }
}
