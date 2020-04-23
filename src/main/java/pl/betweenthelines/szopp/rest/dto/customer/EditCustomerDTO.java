package pl.betweenthelines.szopp.rest.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.betweenthelines.szopp.domain.CustomerType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCustomerDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    private String phone;

    @NotBlank
    private String city;

    @NotBlank
    private String zipCode; // TODO: Validate zip code

    @NotBlank
    private String street;

    @NotNull
    private CustomerType type; //TODO: Data validation for company and for individual

    private String companyName;

    private String nip;
}
