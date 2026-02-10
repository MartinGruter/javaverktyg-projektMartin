package se.iths.martin.javaverktygprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.martin.javaverktygprojekt.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
