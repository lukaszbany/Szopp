package pl.betweenthelines.szopp.rest.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.rest.dto.validation.ValidCustomerData;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ValidCustomerData(message = "{validation.company.data.blank}")
public class EditCustomerDTO {

    @NotBlank(message = "{validation.first.name.blank}")
    private String firstName;

    @NotBlank(message = "{validation.last.name.blank}")
    private String lastName;

    @Email(message = "{validation.email.invalid}")
    @NotBlank(message = "{validation.email.blank}")
    private String email;

    private String phone;

    @NotBlank(message = "{validation.city.blank}")
    private String city;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "{validation.zip.code.invalid}")
    @NotBlank(message = "{validation.zip.code.blank}")
    private String zipCode;

    @NotBlank(message = "{validation.street.blank}")
    private String street;

    @NotNull(message = "{validation.type.blank}")
    private CustomerType type;

    private String companyName;

    private String nip;
}
