package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.rest.dto.order.customer.OrderDTO;
import pl.betweenthelines.szopp.rest.dto.order.customer.factory.OrderDTOFactory;
import pl.betweenthelines.szopp.rest.dto.shipment.ShipmentAddressDTO;
import pl.betweenthelines.szopp.service.order.checkout.CheckoutService;
import pl.betweenthelines.szopp.service.order.operation.AddToCartService;
import pl.betweenthelines.szopp.service.order.operation.GetCartService;
import pl.betweenthelines.szopp.service.order.operation.RemoveFromCartService;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class CartEndpoint {

    @Autowired
    private AddToCartService addToCartService;

    @Autowired
    private RemoveFromCartService removeFromCartService;

    @Autowired
    private GetCartService getCartService;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private OrderDTOFactory orderDTOFactory;

    @RequestMapping(method = GET, value = "/cart")
    public OrderDTO getCart() {
        Order cart = getCartService.getCart();

        return orderDTOFactory.buildOrderDTO(cart);
    }

    @RequestMapping(method = PUT, value = "/cart/product/{productId}")
    public OrderDTO increaseProductQuantity(@PathVariable Long productId) {
        Order order = addToCartService.increaseProductQuantity(productId);

        return orderDTOFactory.buildOrderDTO(order);
    }

    @RequestMapping(method = DELETE, value = "/cart/product/{productId}")
    public OrderDTO decreaseProductQuantity(@PathVariable Long productId) {
        Order order = removeFromCartService.decreaseProductQuantity(productId);

        return orderDTOFactory.buildOrderDTO(order);
    }

    @RequestMapping(method = PUT, value = "/cart/item/{orderItemId}")
    public OrderDTO increaseOrderItemQuantity(@PathVariable Long orderItemId) {
        Order order = addToCartService.increaseOrderItemQuantity(orderItemId);

        return orderDTOFactory.buildOrderDTO(order);
    }

    @RequestMapping(method = DELETE, value = "/cart/item/{orderItemId}")
    public OrderDTO decreasePOrderItemQuantity(@PathVariable Long orderItemId) {
        Order order = removeFromCartService.decreaseItemQuantity(orderItemId);

        return orderDTOFactory.buildOrderDTO(order);
    }

    @RequestMapping(method = POST, value = "/cart/checkout")
    public String checkout(ShipmentAddressDTO shipmentAddressDTO) {
        checkoutService.checkout(shipmentAddressDTO);

        return "OK";
    }
}
