package pl.betweenthelines.szopp.rest.dto.order.customer.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.rest.dto.order.customer.OrderItemDTO;
import pl.betweenthelines.szopp.rest.dto.product.ProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.factory.ProductDTOFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemDTOFactory {

    @Autowired
    private ProductDTOFactory productDTOFactory;

    public List<OrderItemDTO> buildOrderItemDTOs(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::buildOrderItemDTO)
                .sorted(byId())
                .collect(Collectors.toList());
    }

    private Comparator<OrderItemDTO> byId() {
        return Comparator.comparing(OrderItemDTO::getId);
    }

    public OrderItemDTO buildOrderItemDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .product(getProductDTO(orderItem.getProduct()))
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    private ProductDTO getProductDTO(Product product) {
        return productDTOFactory.buildProductDTO(product);
    }
}
