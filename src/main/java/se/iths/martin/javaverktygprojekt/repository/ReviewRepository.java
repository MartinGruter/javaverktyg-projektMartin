package se.iths.martin.javaverktygprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.martin.javaverktygprojekt.model.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
