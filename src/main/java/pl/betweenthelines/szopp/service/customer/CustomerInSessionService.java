package pl.betweenthelines.szopp.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.repository.CustomerRepository;
import pl.betweenthelines.szopp.session.SessionAttributes;

import javax.transaction.Transactional;
import java.util.Optional;

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
                return customer.get();
            }
        }

        return createCustomer();
    }

    @Transactional
    public Customer createCustomer() {
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
