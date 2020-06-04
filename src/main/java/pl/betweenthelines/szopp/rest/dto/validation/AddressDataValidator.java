package pl.betweenthelines.szopp.rest.dto.validation;

import pl.betweenthelines.szopp.domain.CustomerType;
import pl.betweenthelines.szopp.rest.dto.customer.AddressDataDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.isEmpty;
import static pl.betweenthelines.szopp.domain.CustomerType.COMPANY;

public class AddressDataValidator implements ConstraintValidator<ValidAddressData, Object> {

    private String message;

    @Override
    public void initialize(ValidAddressData constraint) {
        message = constraint.message();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        AddressDataDTO addressDataDTO = (AddressDataDTO) obj;
        CustomerType customerType = addressDataDTO.getType();
        if (COMPANY.equals(customerType)) {
            return hasCompanyData(addressDataDTO, context);
        }

        return true;
    }

    private boolean hasCompanyData(AddressDataDTO addressDataDTO, ConstraintValidatorContext context) {
        boolean isCompanyNameValid = !isEmpty(addressDataDTO.getCompanyName());
        if (!isCompanyNameValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("companyName")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        boolean isNipValid = !isEmpty(addressDataDTO.getNip());
        if (!isNipValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("nip")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isCompanyNameValid && isNipValid;
    }
}
