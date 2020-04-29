package pl.betweenthelines.szopp.service.order.checkout.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.exception.CustomerDataIncompleteException;
import pl.betweenthelines.szopp.exception.EmptyOrderException;
import pl.betweenthelines.szopp.exception.ProductNotAvailableException;
import pl.betweenthelines.szopp.service.order.CartQuantitiesService;

import static org.springframework.util.StringUtils.isEmpty;
import static pl.betweenthelines.szopp.domain.CustomerType.COMPANY;
import static pl.betweenthelines.szopp.domain.OrderStatus.NEW;

@Service
public class OrderValidationService {

    @Autowired
    private CartQuantitiesService cartQuantitiesService;

    public void validateOnCheckout(Order order) {
        validateOrderIsNotEmpty(order);
        validateOrderStatus(order);
        validateCustomerData(order);
        validateQuantities(order);
    }

    private void validateOrderIsNotEmpty(Order order) {
        if (order.getOrderItems().isEmpty()) {
            throw new EmptyOrderException();
        }
    }

    private void validateOrderStatus(Order order) {
        if (!NEW.equals(order.getStatus())) {
            throw new ProductNotAvailableException();
        }
    }

    private void validateCustomerData(Order order) {
        Customer customer = order.getCustomer();
        if (isCustomerDataIncomplete(customer)) {
            throw new CustomerDataIncompleteException();
        }
    }

    private boolean isCustomerDataIncomplete(Customer customer) {
        return customer == null ||
                isIndividualDataIncomplete(customer) ||
                isCompanyDataIncomplete(customer);
    }

    private boolean isIndividualDataIncomplete(Customer customer) {
        return isEmpty(customer.getFirstName()) ||
                isEmpty(customer.getLastName()) ||
                isEmpty(customer.getEmail()) ||
                isEmpty(customer.getCity()) ||
                isEmpty(customer.getZipCode()) ||
                isEmpty(customer.getStreet());
    }

    private boolean isCompanyDataIncomplete(Customer customer) {
        return COMPANY.equals(customer.getType()) &&
                (isEmpty(customer.getCompanyName()) || isEmpty(customer.getNip()));
    }

    private void validateQuantities(Order order) {
        cartQuantitiesService.validateQuantities(order);
    }
}
