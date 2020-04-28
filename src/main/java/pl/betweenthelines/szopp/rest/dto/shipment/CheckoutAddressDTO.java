package pl.betweenthelines.szopp.rest.dto.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutAddressDTO {

    @NotBlank(message = "{validation.first.name.blank}")
    private String firstName;

    @NotBlank(message = "{validation.last.name.blank}")
    private String lastName;

    @Email(message = "{validation.email.invalid}")
    @NotBlank(message = "{validation.email.blank}")
    private String email;

    @NotBlank(message = "{validation.city.blank}")
    private String city;

    private String phone;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "{validation.zip.code.invalid}")
    @NotBlank(message = "{validation.zip.code.blank}")
    private String zipCode;

    @NotBlank(message = "{validation.street.blank}")
    private String street;

    @NotNull(message = "{validation.type.blank}")
    private String type;

    private String companyName;

    private String nip;
}
