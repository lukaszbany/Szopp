package pl.betweenthelines.szopp.service.order.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.service.order.OrderInSessionService;
import pl.betweenthelines.szopp.service.product.ProductStockService;

@Service
public class GetCartService {

    @Autowired
    private OrderInSessionService orderInSessionService;

    @Autowired
    private ProductStockService productStockService;

    public Order getCart() {
        Order cart = orderInSessionService.getFromSessionOrCreate();
        updateQuantities(cart);

        return cart;
    }

    private void updateQuantities(Order cart) {
        productStockService.updateQuantitiesInCart(cart);
    }
}
