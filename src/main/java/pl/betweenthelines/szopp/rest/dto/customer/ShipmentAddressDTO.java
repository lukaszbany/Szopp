package pl.betweenthelines.szopp.rest.dto.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.betweenthelines.szopp.domain.CustomerType;

@Getter
@NoArgsConstructor
public class ShipmentAddressDTO extends AddressDataDTO {

    private Long id;

    @Builder(builderMethodName = "shipmentAddressBuilder")
    public ShipmentAddressDTO(Long id,
                              String firstName,
                              String lastName,
                              String email,
                              String phone,
                              String city,
                              String zipCode,
                              String street,
                              CustomerType type,
                              String companyName,
                              String nip) {
        super(firstName, lastName, email, phone, city, zipCode, street, type, companyName, nip);
        this.id = id;
    }

}
