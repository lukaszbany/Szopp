package pl.betweenthelines.szopp.rest.dto.order.customer.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderItem;
import pl.betweenthelines.szopp.domain.ShipmentAddress;
import pl.betweenthelines.szopp.rest.dto.customer.ShipmentAddressDTO;
import pl.betweenthelines.szopp.rest.dto.customer.factory.AddressDataDTOFactory;
import pl.betweenthelines.szopp.rest.dto.order.customer.OrderDTO;
import pl.betweenthelines.szopp.rest.dto.order.customer.OrderItemDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDTOFactory {

    @Autowired
    private OrderItemDTOFactory orderItemDTOFactory;

    @Autowired
    private AddressDataDTOFactory addressDataDTOFactory;

    public List<OrderDTO> buildOrderDTOs(List<Order> orders) {
        return orders.stream()
                .map(this::buildOrderDTO)
                .sorted(byIdDesc())
                .collect(Collectors.toList());
    }

    private Comparator<OrderDTO> byIdDesc() {
        return Comparator.comparing(OrderDTO::getId).reversed();
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
                .totalPrice(order.getTotalPrice())
                .build();
    }

    private ShipmentAddressDTO getShipmentAddress(Order order) {
        ShipmentAddress shipmentAddress = order.getShipmentAddress();
        return addressDataDTOFactory.buildShipmentAddressDTO(shipmentAddress);
    }

    private List<OrderItemDTO> getOrderItemsDTO(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        return orderItemDTOFactory.buildOrderItemDTOs(orderItems);
    }
}
