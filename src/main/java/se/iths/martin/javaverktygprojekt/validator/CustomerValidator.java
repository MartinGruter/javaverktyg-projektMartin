package se.iths.martin.javaverktygprojekt.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.iths.martin.javaverktygprojekt.exceptions.CustomerValidationException;
import se.iths.martin.javaverktygprojekt.model.Customer;

@Component
public class CustomerValidator {
    private static final Logger logger =
            LoggerFactory.getLogger(CustomerValidator.class);

    public void validateCustomerName(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("Name of customer can't be null or empty.");
            throw new CustomerValidationException("Name of customer can't be null or empty.");
        }
    }

    public void validateCustomerAge(int age) {
        if (age <= 0 || age > 150) {
            logger.error("Age of customer can't be less than 0 or greater than 150.");
            throw new CustomerValidationException(
                    "Age of customer can't be below or equal to 0" +
                            " and can't be larger than 150");
        }
    }

    public void validateCustomerEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.error("Email of customer can't be null or empty.");
            throw new CustomerValidationException("Customer email can't be null or empty.");
        }
    }

    public void validateCustomerCity(String city) {
        if (city == null || city.isEmpty()) {
            logger.error("City of customer can't be null or empty.");
            throw new CustomerValidationException("Customer city can't be null or empty.");
        }
    }

    public void validate(Customer customer) {
        validateCustomerName(customer.getName());
        validateCustomerAge(customer.getAge());
        validateCustomerEmail(customer.getEmail());
        validateCustomerCity(customer.getCity());
        logger.info("Customer validation succeeded.");
    }

}
