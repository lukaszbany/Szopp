package pl.betweenthelines.szopp.rest.dto.shipment.factory;

import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.ShipmentAddress;
import pl.betweenthelines.szopp.rest.dto.shipment.ShipmentAddressDTO;

@Component
public class ShipmentAddressDTOFactory {

    public ShipmentAddressDTO buildShipmentAddressDTO(ShipmentAddress shipmentAddress) {
        if (shipmentAddress == null) {
            return null;
        }

        return ShipmentAddressDTO.builder()
                .id(shipmentAddress.getId())
                .firstName(shipmentAddress.getFirstName())
                .lastName(shipmentAddress.getLastName())
                .email(shipmentAddress.getEmail())
                .phone(shipmentAddress.getPhone())
                .street(shipmentAddress.getStreet())
                .zipCode(shipmentAddress.getZipCode())
                .city(shipmentAddress.getCity())
                .type(shipmentAddress.getType().name())
                .companyName(shipmentAddress.getCompanyName())
                .nip(shipmentAddress.getNip())
                .build();
    }

}
