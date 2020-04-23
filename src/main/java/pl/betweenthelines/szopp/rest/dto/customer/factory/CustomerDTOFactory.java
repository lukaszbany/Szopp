package pl.betweenthelines.szopp.rest.dto.customer.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.rest.dto.customer.CustomerDTO;
import pl.betweenthelines.szopp.rest.dto.user.UserDTO;
import pl.betweenthelines.szopp.rest.dto.user.factory.UserDTOFactory;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerDTOFactory {

    @Autowired
    private UserDTOFactory userDTOFactory;

    public List<CustomerDTO> buildCustomerDTOs(List<Customer> customers) {
        return customers.stream()
                .map(this::buildCustomerDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO buildCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .user(getUserDTO(customer))
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .street(customer.getStreet())
                .zipCode(customer.getZipCode())
                .city(customer.getCity())
                .type(customer.getType().name())
                .companyName(customer.getCompanyName())
                .nip(customer.getNip())
                .build();
    }

    private UserDTO getUserDTO(Customer customer) {
        return userDTOFactory.buildUserDTO(customer.getUser());
    }
}
