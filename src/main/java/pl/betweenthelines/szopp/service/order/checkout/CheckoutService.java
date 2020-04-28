package pl.betweenthelines.szopp.service.order.checkout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.ShipmentAddress;
import pl.betweenthelines.szopp.exception.ProductInCartNotAvailableAnymoreException;
import pl.betweenthelines.szopp.rest.dto.shipment.ShipmentAddressDTO;
import pl.betweenthelines.szopp.service.order.OrderInSessionService;
import pl.betweenthelines.szopp.service.order.checkout.validation.OrderValidationService;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static pl.betweenthelines.szopp.domain.OrderStatus.SUBMITTED;

@Slf4j
@Service
public class CheckoutService {

    @Autowired
    private OrderValidationService orderValidationService;

    @Autowired
    private OrderInSessionService orderInSessionService;

    @Transactional(dontRollbackOn = ProductInCartNotAvailableAnymoreException.class)
    public void checkout(@RequestBody(required = false) @Valid ShipmentAddressDTO shipmentAddressDTO) {
        Order order = orderInSessionService.getFromSessionOrCreate();
        validateOrder(order);

        log.info("Submitting order {}", order.getId());
        saveShipmentAddressIfNeeded(shipmentAddressDTO, order);
        order.statusTransition(SUBMITTED);
    }

    private void saveShipmentAddressIfNeeded(@RequestBody(required = false) @Valid ShipmentAddressDTO shipmentAddressDTO, Order order) {
        if (shipmentAddressDTO != null) {
            saveShipmentAddress(shipmentAddressDTO, order);
        }
    }

    private void saveShipmentAddress(@RequestBody(required = false) @Valid ShipmentAddressDTO shipmentAddressDTO, Order order) {
        ShipmentAddress shipmentAddress = ShipmentAddress.builder()
                .order(order)
                .firstName(shipmentAddressDTO.getFirstName())
                .lastName(shipmentAddressDTO.getLastName())
                .email(shipmentAddressDTO.getEmail())
                .phone(shipmentAddressDTO.getPhone())
                .city(shipmentAddressDTO.getCity())
                .zipCode(shipmentAddressDTO.getZipCode())
                .street(shipmentAddressDTO.getStreet())
                .type(CustomerType.valueOf(shipmentAddressDTO.getType()))
                .companyName(shipmentAddressDTO.getCompanyName())
                .nip(shipmentAddressDTO.getNip())
                .build();

        order.setShipmentAddress(shipmentAddress);
    }

    private void validateOrder(Order order) {
        orderValidationService.validateOnCheckout(order);
    }

}
