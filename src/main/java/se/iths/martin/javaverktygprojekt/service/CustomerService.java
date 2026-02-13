package se.iths.martin.javaverktygprojekt.service;

import org.springframework.stereotype.Service;
import se.iths.martin.javaverktygprojekt.exceptions.CustomerNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Customer;
import se.iths.martin.javaverktygprojekt.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with id: + " + id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer exist = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with id: + " + id));

        exist.setCity(updatedCustomer.getCity());
        return customerRepository.save(exist);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
