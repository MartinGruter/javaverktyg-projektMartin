package se.iths.martin.javaverktygprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.martin.javaverktygprojekt.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
