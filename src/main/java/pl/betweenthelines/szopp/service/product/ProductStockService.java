package pl.betweenthelines.szopp.service.product;

import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.domain.Product;

import javax.transaction.Transactional;

@Service
public class ProductStockService {

    @Transactional
    public void updateStock(Order order) {
        order.getOrderItems()
                .forEach(this::updateStock);
    }

    private void updateStock(OrderItem item) {
        Product product = item.getProduct();
        product.decrementStock(item.getQuantity());
    }

}
