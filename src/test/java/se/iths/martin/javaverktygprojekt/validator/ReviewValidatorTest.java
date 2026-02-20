package se.iths.martin.javaverktygprojekt.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.martin.javaverktygprojekt.exceptions.ReviewValidationException;
import se.iths.martin.javaverktygprojekt.model.Review;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReviewValidatorTest {

    private ReviewValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ReviewValidator();
    }

    @Test
    void validateReviewerName_shouldThrow_whenNull() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateReviewerName(null));
    }

    @Test
    void validateReviewerName_shouldThrow_whenBlank() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateReviewerName("   "));
    }

    @Test
    void validateReviewerName_shouldNotThrow_whenValid() {
        assertDoesNotThrow(() -> validator.validateReviewerName("Kevin"));
    }

    @Test
    void validateRating_shouldThrow_whenOutOfRangeLow() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateRating(0));
    }

    @Test
    void validateRating_shouldThrow_whenOutOfRangeHigh() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateRating(6));
    }

    @Test
    void validateRating_shouldNotThrow_whenValid() {
        assertDoesNotThrow(() -> validator.validateRating(5));
    }

    @Test
    void validateTitle_shouldThrow_whenBlank() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateTitle(" "));
    }

    @Test
    void validateTitle_shouldNotThrow_whenValid() {
        assertDoesNotThrow(() -> validator.validateTitle("Blåa jeans"));
    }

    @Test
    void validateComment_shouldThrow_whenBlank() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateComment(" "));
    }

    @Test
    void validateComment_shouldNotThrow_whenValid() {
        assertDoesNotThrow(() -> validator.validateComment("Dom satt bra!"));
    }

    @Test
    void validateCreatedDate_shouldThrow_whenNull() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateCreatedDate(null));
    }

    @Test
    void validateCreatedDate_shouldThrow_whenFutureDate() {
        assertThrows(ReviewValidationException.class,
                () -> validator.validateCreatedDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void validateCreatedDate_shouldNotThrow_whenTodayOrPast() {
        assertDoesNotThrow(() -> validator.validateCreatedDate(LocalDate.now()));
    }

    @Test
    void validateReview_shouldNotThrow_whenReviewIsValid() {
        Review review = new Review();
        review.setReviewerName("Kevin");
        review.setRating(5);
        review.setTitle("Blåa jeans");
        review.setComment("Dom passade!");
        review.setCreatedDate(LocalDate.now());

        assertDoesNotThrow(() -> validator.validateReview(review));
    }

    @Test
    void validateReview_shouldThrow_whenAnyFieldInvalid() {
        Review review = new Review();
        review.setReviewerName("");
        review.setRating(5);
        review.setTitle("Blåa jeans");
        review.setComment("Dom passade!");
        review.setCreatedDate(LocalDate.now());

        assertThrows(ReviewValidationException.class,
                () -> validator.validateReview(review));
    }
}
