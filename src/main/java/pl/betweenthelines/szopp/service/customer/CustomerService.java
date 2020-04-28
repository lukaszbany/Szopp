package pl.betweenthelines.szopp.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.domain.repository.CustomerRepository;
import pl.betweenthelines.szopp.exception.NotFoundException;
import pl.betweenthelines.szopp.rest.dto.customer.EditCustomerDTO;

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
    public void editCustomerData(EditCustomerDTO editCustomerDTO) {
        Customer customer = getCustomer();

        log.info("Updating data of customer {}", customer.getId());
        fillFields(customer, editCustomerDTO);
    }

    private void fillFields(Customer customer, EditCustomerDTO editCustomerDTO) {
        customer.setFirstName(editCustomerDTO.getFirstName());
        customer.setLastName(editCustomerDTO.getLastName());
        customer.setEmail(editCustomerDTO.getEmail());
        customer.setPhone(editCustomerDTO.getPhone());
        customer.setZipCode(editCustomerDTO.getZipCode());
        customer.setCity(editCustomerDTO.getCompanyName());
        customer.setType(getType(editCustomerDTO));
        customer.setCompanyName(editCustomerDTO.getCompanyName());
        customer.setNip(editCustomerDTO.getNip());
    }

    private CustomerType getType(EditCustomerDTO editCustomerDTO) {
        if (editCustomerDTO.getType() != null) {
            return editCustomerDTO.getType();
        }

        return INDIVIDUAL;
    }

}
