package pl.betweenthelines.szopp.service.order.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.domain.repository.OrderRepository;
import pl.betweenthelines.szopp.exception.NoSuchOrderItemInCartException;
import pl.betweenthelines.szopp.service.order.OrderInSessionService;
import pl.betweenthelines.szopp.service.product.ProductService;

import java.util.Optional;

@Service
public class OrderOperationService {

    protected static final int ONE = 1;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected OrderInSessionService orderInSessionService;

    @Autowired
    protected ProductService productService;

    protected Optional<OrderItem> findExistingItem(Order order, Product product) {
        return order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.isProduct(product))
                .findFirst();
    }

    protected OrderItem findItemInCart(Order order, Long orderItemId) {
        return order.getOrderItems()
                .stream()
                .filter(item -> isItemWithId(item, orderItemId))
                .findFirst()
                .orElseThrow(NoSuchOrderItemInCartException::new);
    }

    private boolean isItemWithId(OrderItem item, Long orderItemId) {
        return item.getId().equals(orderItemId);
    }

}
