package pl.betweenthelines.szopp.service.order.checkout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.ShipmentAddress;
import pl.betweenthelines.szopp.domain.repository.ShipmentAddressRepository;
import pl.betweenthelines.szopp.exception.ProductInCartNotAvailableAnymoreException;
import pl.betweenthelines.szopp.exception.SzoppException;
import pl.betweenthelines.szopp.rest.dto.customer.AddressDataDTO;
import pl.betweenthelines.szopp.service.order.OrderInSessionService;
import pl.betweenthelines.szopp.service.order.checkout.validation.OrderValidationService;
import pl.betweenthelines.szopp.service.product.ProductStockService;

import javax.transaction.Transactional;

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
    public void checkout(AddressDataDTO shipmentAddressDTO) {
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

    private void saveShipmentAddressIfNeeded(AddressDataDTO shipmentAddressDTO, Order order) {
        if (shipmentAddressDTO == null) {
            copyShipmentAddressFromCustomer(order);
        } else {
            saveShipmentAddress(shipmentAddressDTO, order);
        }
    }

    private void copyShipmentAddressFromCustomer(Order order) {
        ShipmentAddress shipmentAddress = createShipmentAddressFromCustomerAddress(order);
        order.setShipmentAddress(shipmentAddress);
        shipmentAddressRepository.save(shipmentAddress);
    }

    private void saveShipmentAddress(AddressDataDTO shipmentAddressDTO, Order order) {
        ShipmentAddress shipmentAddress = createShipmentAddress(shipmentAddressDTO, order);
        order.setShipmentAddress(shipmentAddress);
        shipmentAddressRepository.save(shipmentAddress);
    }

    private ShipmentAddress createShipmentAddressFromCustomerAddress(Order order) {
        Customer customer = order.getCustomer();
        if (customer == null) {
            throw new SzoppException();
        }

        return ShipmentAddress.builder()
                .order(order)
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .city(customer.getCity())
                .zipCode(customer.getZipCode())
                .street(customer.getStreet())
                .type(customer.getType())
                .companyName(customer.getCompanyName())
                .nip(customer.getNip())
                .build();
    }

    private ShipmentAddress createShipmentAddress(AddressDataDTO shipmentAddressDTO, Order order) {
        return ShipmentAddress.builder()
                .order(order)
                .firstName(shipmentAddressDTO.getFirstName())
                .lastName(shipmentAddressDTO.getLastName())
                .email(shipmentAddressDTO.getEmail())
                .phone(shipmentAddressDTO.getPhone())
                .city(shipmentAddressDTO.getCity())
                .zipCode(shipmentAddressDTO.getZipCode())
                .street(shipmentAddressDTO.getStreet())
                .type(shipmentAddressDTO.getType())
                .companyName(shipmentAddressDTO.getCompanyName())
                .nip(shipmentAddressDTO.getNip())
                .build();
    }

    private void validateOrder(Order order) {
        orderValidationService.validateOnCheckout(order);
    }

}
