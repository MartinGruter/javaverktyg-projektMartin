package se.iths.martin.javaverktygprojekt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.martin.javaverktygprojekt.exceptions.CustomerNotFoundException;
import se.iths.martin.javaverktygprojekt.exceptions.CustomerValidationException;
import se.iths.martin.javaverktygprojekt.model.Customer;
import se.iths.martin.javaverktygprojekt.repository.CustomerRepository;
import se.iths.martin.javaverktygprojekt.validator.CustomerValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerValidator customerValidator;

    @Test
    void shouldGetAllCustomersTest() {
        List<Customer> mockedCustomers = List.of(
                new Customer("Martin", 28, "martin.test@gmail.com", "Stockholm"),
                new Customer("Yunus", 25, "yunus.test@gmail.com", "Stockholm"),
                new Customer("Anwar", 22, "anwar.test@gmail.com", "Stockholm")
        );
        when(customerRepository.findAll()).thenReturn(mockedCustomers);
        List<Customer> result = customerService.getAllCustomers();
        assertEquals(3, result.size());
    }

    @Test
    void getCustomerByIdTest() {
        Customer mockedCustomer = new Customer(
                "Martin", 28, "martin.test@gmail.com", "Stockholm");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockedCustomer));
        Customer result = customerService.getCustomer(1L);

        assertEquals(mockedCustomer, result);
    }

    @Test
    void throwsCustomerNotFoundExceptionIfIdDontExist() {
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomer(1L));
    }

    @Test
    void createCustomerTest() {
        Customer mockedCustomer = new Customer(
                "Martin", 28, "martin.test@gmail.com", "Stockholm"
        );
        when(customerRepository.save(mockedCustomer)).thenReturn(mockedCustomer);
        Customer result = customerService.createCustomer(mockedCustomer);
        assertEquals(mockedCustomer, result);
        verify(customerValidator).validate(mockedCustomer);
    }

    @Test
    void shouldUpdateProduct() {
        Customer oldCustomer = new Customer("Martin", 28, "martin.test@gmail.com", "Stockholm");
        Customer updatedCustomer = new Customer("Johanna", 30, "johanna.test@gmail.com", "Uppsala");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(oldCustomer));
        when(customerRepository.save(oldCustomer)).thenReturn(oldCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        assertEquals("Johanna", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("johanna.test@gmail.com", result.getEmail());
        assertEquals("Uppsala", result.getCity());
    }

    @Test
    void throwsCustomerNotFoundExceptionIfIdDontExistWhileUpdatingCustomer() {
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.updateCustomer(1L, new Customer(
                        "Martin", 28, "martin.test@gmail.com", "Stockholm"))
        );
    }

    @Test
    void throwsCustomerValidationExceptionWhenUpdatingCustomerWithInvalidAttributes() {
        Customer oldCustomer = new Customer("Martin", 28, "martin.test@gmail.com", "Stockholm");
        Customer invalidCustomer = new Customer("", 0, null, null);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(oldCustomer));

        doThrow(new CustomerValidationException("Invalid customer input"))
                .when(customerValidator).validate(invalidCustomer);

        assertThrows(CustomerValidationException.class,
                () -> customerService.updateCustomer(1L, invalidCustomer)
        );
    }

    @Test
    void deleteCustomerTest() {
        Customer customer = new Customer(
                "Martin", 28, "martin.test@gmail.com", "Stockholm");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(1L);
        verify(customerRepository).delete(customer);
    }

    @Test
    void ifCustomerIdDontExistThrowCustomerNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.deleteCustomer(1L)
        );
    }

}
