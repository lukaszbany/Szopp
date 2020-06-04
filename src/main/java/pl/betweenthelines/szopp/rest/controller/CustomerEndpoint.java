package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.rest.dto.customer.CustomerDTO;
import pl.betweenthelines.szopp.rest.dto.customer.AddressDataDTO;
import pl.betweenthelines.szopp.rest.dto.customer.factory.CustomerDTOFactory;
import pl.betweenthelines.szopp.rest.success.SuccessResponse;
import pl.betweenthelines.szopp.rest.success.SuccessResponseFactory;
import pl.betweenthelines.szopp.service.customer.CustomerService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/customers")
public class CustomerEndpoint {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SuccessResponseFactory successResponseFactory;

    @Autowired
    private CustomerDTOFactory customerDTOFactory;

    @RequestMapping(method = GET, value = "/my-data")
    public CustomerDTO getCustomer() {
        Customer customer = customerService.getCustomer();

        return customerDTOFactory.buildCustomerDTO(customer);
    }

    @RequestMapping(method = PUT, value = "/my-data")
    public ResponseEntity<SuccessResponse> editCustomer(@RequestBody @Valid AddressDataDTO addressDataDTO) {
        customerService.editCustomerData(addressDataDTO);

        return successResponseFactory.buildSuccessResponseEntity("success.customer.data.saved");
    }

    @RequestMapping(method = GET)
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerService.getCustomers();

        return customerDTOFactory.buildCustomerDTOs(customers);
    }

    @RequestMapping(method = GET, value = "/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);

        return customerDTOFactory.buildCustomerDTO(customer);
    }

}
