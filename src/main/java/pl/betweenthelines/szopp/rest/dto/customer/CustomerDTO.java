package pl.betweenthelines.szopp.rest.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.rest.dto.user.UserDTO;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    private UserDTO user;

    private String firstName;

    private String lastName;

    private String email;

    private String city;

    private String phone;

    private String zipCode;

    private String street;

    private String type;

    private String companyName;

    private String nip;
}
