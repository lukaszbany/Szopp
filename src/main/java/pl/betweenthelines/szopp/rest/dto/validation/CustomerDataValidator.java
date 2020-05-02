package pl.betweenthelines.szopp.rest.dto.validation;

import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.rest.dto.customer.EditCustomerDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.isEmpty;
import static pl.betweenthelines.szopp.domain.CustomerType.COMPANY;

public class CustomerDataValidator implements ConstraintValidator<PasswordsMatches, Object> {

    @Override
    public void initialize(PasswordsMatches constraint) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        EditCustomerDTO editCustomerDTO = (EditCustomerDTO) obj;
        CustomerType customerType = editCustomerDTO.getType();
        if (COMPANY.equals(customerType)) {
            return hasCompanyData(editCustomerDTO);
        }

        return true;
    }

    private boolean hasCompanyData(EditCustomerDTO editCustomerDTO) {
        return !isEmpty(editCustomerDTO.getCompanyName()) &&
                !isEmpty(editCustomerDTO.getNip());
    }
}
