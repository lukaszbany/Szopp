package pl.betweenthelines.szopp.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.domain.repository.CustomerRepository;
import pl.betweenthelines.szopp.exception.NotFoundException;
import pl.betweenthelines.szopp.rest.dto.customer.AddressDataDTO;
import pl.betweenthelines.szopp.service.order.OrderService;

import javax.transaction.Transactional;
import java.util.List;

import static pl.betweenthelines.szopp.domain.CustomerType.INDIVIDUAL;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerInSessionService customerInSessionService;

    @Transactional
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Customer getCustomer() {
        return customerInSessionService.getFromSessionOrCreate();
    }

    @Transactional
    public void editCustomerData(AddressDataDTO addressDataDTO) {
        Customer customer = getCustomer();

        log.info("Updating data of customer {}", customer.getId());
        fillFields(customer, addressDataDTO);
    }

    private void fillFields(Customer customer, AddressDataDTO addressDataDTO) {
        customer.setFirstName(addressDataDTO.getFirstName());
        customer.setLastName(addressDataDTO.getLastName());
        customer.setEmail(addressDataDTO.getEmail());
        customer.setPhone(addressDataDTO.getPhone());
        customer.setZipCode(addressDataDTO.getZipCode());
        customer.setCity(addressDataDTO.getCity());
        customer.setStreet(addressDataDTO.getStreet());
        customer.setType(getType(addressDataDTO));
        customer.setCompanyName(addressDataDTO.getCompanyName());
        customer.setNip(addressDataDTO.getNip());
    }

    private CustomerType getType(AddressDataDTO addressDataDTO) {
        if (addressDataDTO.getType() != null) {
            return addressDataDTO.getType();
        }

        return INDIVIDUAL;
    }

}
