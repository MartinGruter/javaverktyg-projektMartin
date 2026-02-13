package se.iths.martin.javaverktygprojekt.validator;

import org.springframework.stereotype.Component;
import se.iths.martin.javaverktygprojekt.exceptions.ProductValidationException;
import se.iths.martin.javaverktygprojekt.model.Product;

@Component
public class ProductValidator {

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ProductValidationException("Product name cannot be null or empty");
        }
    }

    public void validatePrice(double price) {
        if (price <= 0) {
            throw new ProductValidationException("Product price must be greater than 0");
        }
    }

    public void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new ProductValidationException("Product description cannot be null or empty");
        }
    }

    public void validate(Product product) {
        if (product == null) {
            throw new ProductValidationException("Product cannot be null");
        }

        validateName(product.getName());
        validatePrice(product.getPrice());
        validateDescription(product.getDescription());
    }


}
