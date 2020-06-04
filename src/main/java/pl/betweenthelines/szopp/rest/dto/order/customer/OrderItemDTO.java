package pl.betweenthelines.szopp.rest.dto.order.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.betweenthelines.szopp.rest.dto.product.ProductDTO;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long id;

    private Long orderId;

    private ProductDTO product;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal totalPrice;

}
