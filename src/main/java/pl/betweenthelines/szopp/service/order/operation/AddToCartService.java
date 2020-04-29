package pl.betweenthelines.szopp.service.order.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.exception.ProductNotAvailableException;
import pl.betweenthelines.szopp.service.order.CartQuantitiesService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AddToCartService extends OrderOperationService {

    @Autowired
    private CartQuantitiesService cartQuantitiesService;

    @Transactional
    public Order increaseProductQuantity(Long productId) {
        Order order = orderInSessionService.getFromSessionOrCreate();
        Product product = productService.getProduct(productId);

        Optional<OrderItem> currentItem = findExistingItem(order, product);
        if (currentItem.isPresent()) {
            return increaseQuantity(order, currentItem.get());
        }

        return addNewItem(product, order);
    }

    @Transactional
    public Order increaseOrderItemQuantity(Long orderItemId) {
        Order order = orderInSessionService.getFromSessionOrCreate();
        OrderItem orderItem = findItemInCart(order, orderItemId);

        return increaseQuantity(order, orderItem);
    }

    private Order increaseQuantity(Order order, OrderItem item) {
        item.incrementQuantity();
        updateQuantities(order);

        return order;
    }

    private Order addNewItem(Product product, Order order) {
        if (!product.isAvailable()) {
            throw new ProductNotAvailableException();
        }

        OrderItem newItem = createNewItem(product, order);
        order.getOrderItems().add(newItem);

        return order;
    }

    private OrderItem createNewItem(Product product, Order order) {
        return OrderItem.builder()
                .product(product)
                .order(order)
                .price(product.getPrice())
                .quantity(ONE)
                .build();
    }

    private void updateQuantities(Order orderInSession) {
        cartQuantitiesService.updateQuantitiesInCart(orderInSession);
    }
}
