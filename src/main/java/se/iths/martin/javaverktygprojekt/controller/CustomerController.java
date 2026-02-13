package se.iths.martin.javaverktygprojekt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.martin.javaverktygprojekt.model.Customer;
import se.iths.martin.javaverktygprojekt.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showAllCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/{id}")
    public String showCustomerById(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomer(id));
        return "customer";
    }

    @GetMapping("/new")
    public String showCreateCustomerForm() {
        return "create-customer";
    }

    @PostMapping
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/customers";
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
        customerService.updateCustomer(id, customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomer(id);
        model.addAttribute("customer", customer);
        return "edit-customer";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

}
