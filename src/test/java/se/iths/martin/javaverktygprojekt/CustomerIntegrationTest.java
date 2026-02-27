package se.iths.martin.javaverktygprojekt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.martin.javaverktygprojekt.model.Customer;
import se.iths.martin.javaverktygprojekt.repository.CustomerRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void cleanDatabase() {
        customerRepository.deleteAll();
    }

    //Create customer test
    @Test
    @DisplayName("Create customer")
    void createCustomer() throws Exception {
        mockMvc.perform(post("/customers")
                        .param("name", "Martin")
                        .param("age", "28")
                        .param("email", "martin.test@gmail.com")
                        .param("city", "Stockholm"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertEquals(1, customerRepository.count());
    }

    //Integration test to get customer-id
    @Test
    @DisplayName("Get customer by id")
    void getCustomerByIdTest() throws Exception {
        Customer saved = customerRepository.save(
                new Customer("Martin", 28, "martin.test", "Stockholm")
        );

        mockMvc.perform(get("/customers/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Martin")));
    }

    @Test
    @DisplayName("Get all supplier from H2")
    void getAllCustomersTest() throws Exception {
        customerRepository.save(new Customer("Anwar", 15, "awnes.se", "Uppsala"));
        customerRepository.save(new Customer("Martin", 28, "martin.test", "Stockholm"));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Anwar")))
                .andExpect(content().string(containsString("Martin")));
    }
}
