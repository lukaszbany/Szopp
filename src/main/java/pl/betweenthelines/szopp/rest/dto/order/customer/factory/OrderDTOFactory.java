package pl.betweenthelines.szopp.rest.dto.order.customer.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.domain.ShipmentAddress;
import pl.betweenthelines.szopp.rest.dto.order.customer.OrderDTO;
import pl.betweenthelines.szopp.rest.dto.order.customer.OrderItemDTO;
import pl.betweenthelines.szopp.rest.dto.shipment.ShipmentAddressDTO;
import pl.betweenthelines.szopp.rest.dto.shipment.factory.ShipmentAddressDTOFactory;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDTOFactory {

    @Autowired
    private OrderItemDTOFactory orderItemDTOFactory;

    @Autowired
    private ShipmentAddressDTOFactory shipmentAddressDTOFactory;

    public List<OrderDTO> buildOrderDTOs(List<Order> orders) {
        return orders.stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .dateCreated(order.getDateCreated())
                .dateSent(order.getDateSent())
                .status(order.getStatus())
                .orderItems(getOrderItemsDTO(order))
                .shipmentAddress(getShipmentAddress(order))
                .build();
    }

    private ShipmentAddressDTO getShipmentAddress(Order order) {
        ShipmentAddress shipmentAddress = order.getShipmentAddress();
        return shipmentAddressDTOFactory.buildShipmentAddressDTO(shipmentAddress);
    }

    private List<OrderItemDTO> getOrderItemsDTO(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        return orderItemDTOFactory.buildOrderItemDTOs(orderItems);
    }
}
