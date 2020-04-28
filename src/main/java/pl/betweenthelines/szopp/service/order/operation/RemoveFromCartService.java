package pl.betweenthelines.szopp.service.order.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.exception.NoSuchProductInCartException;
import pl.betweenthelines.szopp.service.product.ProductStockService;

import javax.transaction.Transactional;

@Service
public class RemoveFromCartService extends OrderOperationService {

    @Autowired
    private ProductStockService productStockService;

    @Transactional
    public Order decreaseProductQuantity(Long productId) {
        Order order = orderInSessionService.getFromSessionOrCreate();
        Product product = productService.getProduct(productId);
        OrderItem orderItem = findExistingItem(order, product)
                .orElseThrow(NoSuchProductInCartException::new);

        return decreaseQuantity(order, orderItem);
    }

    @Transactional
    public Order decreaseItemQuantity(Long orderItemId) {
        Order order = orderInSessionService.getFromSessionOrCreate();
        OrderItem orderItem = findItemInCart(order, orderItemId);

        return decreaseQuantity(order, orderItem);
    }

    private Order decreaseQuantity(Order order, OrderItem item) {
        item.decrementQuantity(ONE);
        updateQuantities(order);

        return order;
    }

    private void updateQuantities(Order orderInSession) {
        productStockService.updateQuantitiesInCart(orderInSession);
    }

}
