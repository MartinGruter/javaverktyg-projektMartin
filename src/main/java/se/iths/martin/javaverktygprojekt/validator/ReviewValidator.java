package se.iths.martin.javaverktygprojekt.validator;


import org.springframework.stereotype.Component;
import se.iths.martin.javaverktygprojekt.exceptions.ReviewValidationException;
import se.iths.martin.javaverktygprojekt.model.Review;


import java.time.LocalDate;

@Component
public class ReviewValidator {

    public void validateReviewerName(String reviewerName) {
        if (reviewerName == null || reviewerName.trim().isEmpty()) {
            throw new ReviewValidationException("Namnet får inte vara tomt!");
        }
        if (reviewerName.trim().length() > 100) {
            throw new ReviewValidationException("Namnet får inte vara mer än 100 bokstäver!");
        }
    }
    public void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new ReviewValidationException("Betyget måste vara mellan 1-5!");
        }
    }
    public void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new  ReviewValidationException("Titeln får inte vara tom!");
        }
        if (title.trim().length() > 100) {
            throw new ReviewValidationException("Titeln får inte vara längre än 100 bokstäver!");
        }
    }
    public void validateComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            throw new ReviewValidationException("Kommentaren får inte vara tom!");
        }
        if (comment.trim().length() > 1000) {
            throw new ReviewValidationException("Kommentaren får inte vara mer än 1000 bokstäver!");
        }
    }
    public void validateCreatedDate(LocalDate createdDate) {
        if (createdDate == null) {
            throw new ReviewValidationException("Datumet får inte vara null!");
        }
        if (createdDate.isAfter(LocalDate.now())) {
            throw new ReviewValidationException("Datumet kan inte vara i framtiden!");
        }
    }
    public void validateReview(Review review) {
        validateReviewerName(review.getReviewerName());
        validateRating(review.getRating());
        validateTitle(review.getTitle());
        validateComment(review.getComment());
        validateCreatedDate(review.getCreatedDate());
    }
}
