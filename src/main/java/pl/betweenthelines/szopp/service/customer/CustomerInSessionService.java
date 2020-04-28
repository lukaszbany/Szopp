package pl.betweenthelines.szopp.service.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.repository.CustomerRepository;
import pl.betweenthelines.szopp.session.SessionAttributes;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class CustomerInSessionService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SessionAttributes sessionAttributes;

    @Transactional
    public Customer getFromSessionOrCreate() {
        Long customerId = sessionAttributes.getCustomerId();
        if (customerId != null) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            if (customer.isPresent()) {
                log.debug("Found customer {} in session.", customer.get().getId());
                return customer.get();
            }
        }

        return createCustomer();
    }

    @Transactional
    public Customer createCustomer() {
        log.debug("Customer not found in session. Creating new one.");
        Customer customer = customerRepository.save(new Customer());
        sessionAttributes.setCustomerId(customer.getId());

        return customer;
    }

    public void saveCustomerInSession(Customer customer) {
        if (customer != null) {
            sessionAttributes.setCustomerId(customer.getId());
        }
    }
}
