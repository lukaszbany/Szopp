package pl.betweenthelines.szopp.service.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.repository.OrderRepository;
import pl.betweenthelines.szopp.service.customer.CustomerInSessionService;
import pl.betweenthelines.szopp.service.product.ProductStockService;
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

        if (orderId != null) {
            Optional<Order> orderInSession = orderRepository.findByIdAndStatus(orderId, NEW);
            if (orderInSession.isPresent()) {
                return getOrderFromSession(orderInSession.get());
            }
        }

        return createOrder();
    }

    private Order getOrderFromSession(Order orderInSession) {
        log.debug("Found order id {} in session.", orderInSession.getId());
        updateCustomerIfNeeded(orderInSession);

        return orderInSession;
    }

    private void updateCustomerIfNeeded(Order orderInSession) {
        Customer customerInSession = customerInSessionService.getFromSessionOrCreate();
        if (!orderInSession.getCustomer().equals(customerInSession)) {
            log.info("Order id {} has different customer than saved in session. Probably customer just logged in. Changing customer to {}.", orderInSession.getId(), customerInSession.getId());
            orderInSession.setCustomer(customerInSession);
        }
    }

    private Order createOrder() {
        Customer customer = customerInSessionService.getFromSessionOrCreate();
        Order order = saveNewOrder(customer);
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
