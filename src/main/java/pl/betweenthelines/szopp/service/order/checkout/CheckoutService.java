package pl.betweenthelines.szopp.service.order.checkout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.ShipmentAddress;
import pl.betweenthelines.szopp.domain.repository.ShipmentAddressRepository;
import pl.betweenthelines.szopp.exception.ProductInCartNotAvailableAnymoreException;
import pl.betweenthelines.szopp.rest.dto.shipment.ShipmentAddressDTO;
import pl.betweenthelines.szopp.service.order.OrderInSessionService;
import pl.betweenthelines.szopp.service.order.checkout.validation.OrderValidationService;
import pl.betweenthelines.szopp.service.product.ProductStockService;

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

    @Autowired
    private ShipmentAddressRepository shipmentAddressRepository;

    @Autowired
    private ProductStockService productStockService;

    @Transactional(dontRollbackOn = ProductInCartNotAvailableAnymoreException.class)
    public void checkout(ShipmentAddressDTO shipmentAddressDTO) {
        Order order = orderInSessionService.getFromSessionOrCreate();
        validateOrder(order);

        log.info("Submitting order {}", order.getId());
        saveShipmentAddressIfNeeded(shipmentAddressDTO, order);
        updateStock(order);
        order.statusTransition(SUBMITTED);
    }

    private void updateStock(Order order) {
        productStockService.updateStock(order);
    }

    private void saveShipmentAddressIfNeeded(@RequestBody(required = false) @Valid ShipmentAddressDTO shipmentAddressDTO, Order order) {
        if (shipmentAddressDTO != null) {
            saveShipmentAddress(shipmentAddressDTO, order);
        }
    }

    private void saveShipmentAddress(@RequestBody(required = false) @Valid ShipmentAddressDTO shipmentAddressDTO, Order order) {
        ShipmentAddress shipmentAddress = createShipmentAddress(shipmentAddressDTO, order);
        order.setShipmentAddress(shipmentAddress);
        shipmentAddressRepository.save(shipmentAddress);
    }

    private ShipmentAddress createShipmentAddress(@RequestBody(required = false) @Valid ShipmentAddressDTO shipmentAddressDTO, Order order) {
        return ShipmentAddress.builder()
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
    }

    private void validateOrder(Order order) {
        orderValidationService.validateOnCheckout(order);
    }

}
