package se.iths.martin.javaverktygprojekt.service;

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
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with id: + " + id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer exist = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with id: + " + id));
        customerValidator.validate(updatedCustomer);
        exist.setName(updatedCustomer.getName());
        exist.setAge(updatedCustomer.getAge());
        exist.setEmail(updatedCustomer.getEmail());
        exist.setCity(updatedCustomer.getCity());
        return customerRepository.save(exist);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
