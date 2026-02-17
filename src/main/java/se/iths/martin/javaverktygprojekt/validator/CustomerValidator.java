package se.iths.martin.javaverktygprojekt.validator;

import org.springframework.stereotype.Component;
import se.iths.martin.javaverktygprojekt.exceptions.CustomerValidationException;
import se.iths.martin.javaverktygprojekt.model.Customer;

@Component
public class CustomerValidator {

    public void validateCustomerName(String name) {
        if (name == null || name.isEmpty()) {
            throw new CustomerValidationException("Name of customer can't be null or empty.");
        }
    }

    public void validateCustomerAge(int age) {
        if (age <= 0 || age >= 150) {
            throw new CustomerValidationException(
                    "Age of customer can't be below or equal to 0" +
                            " and can't be larger than 150");
        }
    }

    public void validateCustomerEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomerValidationException("Customer email can't be null or empty.");
        }
    }

    public void validateCustomerCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new CustomerValidationException("Customer city can't be null or empty.");
        }
    }

    public void validate(Customer customer) {
        validateCustomerName(customer.getName());
        validateCustomerAge(customer.getAge());
        validateCustomerEmail(customer.getEmail());
        validateCustomerCity(customer.getCity());
    }
}
