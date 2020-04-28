package pl.betweenthelines.szopp.rest.dto.order.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.betweenthelines.szopp.domain.OrderStatus;
import pl.betweenthelines.szopp.rest.dto.shipment.ShipmentAddressDTO;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private OrderStatus status;

    private Date dateCreated;

    private Date dateSent;

    private Long customerId;

    private List<OrderItemDTO> orderItems;

    private ShipmentAddressDTO shipmentAddress;

}
