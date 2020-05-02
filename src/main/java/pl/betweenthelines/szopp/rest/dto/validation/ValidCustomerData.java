package pl.betweenthelines.szopp.rest.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CustomerDataValidator.class)
public @interface ValidCustomerData {

    String message() default "Customer type is COMPANY, but has empty company data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
