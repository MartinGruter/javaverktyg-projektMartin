package se.iths.martin.javaverktygprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.martin.javaverktygprojekt.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
