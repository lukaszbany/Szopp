package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderStatus;
import pl.betweenthelines.szopp.rest.dto.order.customer.OrderDTO;
import pl.betweenthelines.szopp.rest.dto.order.customer.factory.OrderDTOFactory;
import pl.betweenthelines.szopp.service.order.OrderService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;

@RestController
public class OrderEndpoint {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDTOFactory orderDTOFactory;

    @RequestMapping(method = GET, value = "/customers/{customerId}/orders")
    public List<OrderDTO> getCustomerOrders(@PathVariable Long customerId) {
        List<Order> customerOrders = orderService.getCustomerOrders(customerId);

        return orderDTOFactory.buildOrderDTOs(customerOrders);
    }

    @RequestMapping(method = GET, value = "/orders")
    public List<OrderDTO> getOrders(@RequestParam(required = false) OrderStatus status) {
        List<Order> customerOrders = orderService.getOrders(status);

        return orderDTOFactory.buildOrderDTOs(customerOrders);
    }

    @RequestMapping(method = PATCH, value = "/orders/{orderId}")
    public OrderDTO changeStatus(@PathVariable Long orderId, @RequestBody String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        Order order = orderService.changeStatus(orderId, orderStatus);

        return orderDTOFactory.buildOrderDTO(order);
    }

}
