package pl.betweenthelines.szopp.service.product;

import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.exception.ProductInCartNotAvailableAnymoreException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductStockService {

    public void validateQuantities(Order order) {
        if (isAnyItemNotAvailable(order)) {
            updateQuantitiesInCart(order);
            throw new ProductInCartNotAvailableAnymoreException();
        }
    }

    private boolean isAnyItemNotAvailable(Order order) {
        return order.getOrderItems()
                .stream()
                .anyMatch(this::isNotAvailable);
    }

    private boolean isNotAvailable(OrderItem item) {
        int quantityInCart = item.getQuantity();
        int quantityInStock = item.getProduct().getInStock();

        if (quantityInCart > quantityInStock) {
            return true;
        }

        return false;
    }

    public void updateQuantitiesInCart(Order order) {
        order.getOrderItems()
                .forEach(this::updateQuantity);

        removeItemsWithZeroQuantity(order);
    }

    private void updateQuantity(OrderItem item) {
        int quantityInCart = item.getQuantity();
        int quantityInStock = item.getProduct().getInStock();

        if (quantityInCart > quantityInStock) {
            item.setQuantity(quantityInStock);
        }
    }

    private void removeItemsWithZeroQuantity(Order order) {
        List<OrderItem> itemsToDelete = getItemsWithZeroQuantity(order);

        order.getOrderItems()
                .removeAll(itemsToDelete);
    }

    private List<OrderItem> getItemsWithZeroQuantity(Order order) {
        return order.getOrderItems()
                .stream()
                .filter(this::hasZeroQuantity)
                .collect(Collectors.toList());
    }

    private boolean hasZeroQuantity(OrderItem item) {
        return item.getQuantity() == 0;
    }

}
