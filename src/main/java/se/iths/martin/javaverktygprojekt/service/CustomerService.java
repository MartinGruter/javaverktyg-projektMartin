package se.iths.martin.javaverktygprojekt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.iths.martin.javaverktygprojekt.exceptions.CustomerNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Customer;
import se.iths.martin.javaverktygprojekt.repository.CustomerRepository;
import se.iths.martin.javaverktygprojekt.validator.CustomerValidator;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        customerValidator.validate(customer);
        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long id) {
        logger.info("Fetching customer with id {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Customer with id " + id + " not found.");
                    return new CustomerNotFoundException("No customer found with id: + " + id);
                });
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        logger.info("Updating customer with id {}", id);
        Customer exist = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No customer found with id {} found", id);
                    return new CustomerNotFoundException("Customer not found.");
                });
        customerValidator.validate(updatedCustomer);
        exist.setName(updatedCustomer.getName());
        exist.setAge(updatedCustomer.getAge());
        exist.setEmail(updatedCustomer.getEmail());
        exist.setCity(updatedCustomer.getCity());
        return customerRepository.save(exist);
    }

    public void deleteCustomer(Long id) {
        logger.info("Deleting customer with id {}", id);
        if (!customerRepository.existsById(id)) {
            logger.warn("Customer with id {} not found", id);
            throw new CustomerNotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

}
