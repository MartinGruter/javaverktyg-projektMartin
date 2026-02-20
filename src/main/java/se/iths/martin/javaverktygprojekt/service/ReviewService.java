package se.iths.martin.javaverktygprojekt.service;

import org.springframework.stereotype.Service;
import se.iths.martin.javaverktygprojekt.exceptions.ReviewNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Review;
import se.iths.martin.javaverktygprojekt.repository.ReviewRepository;
import se.iths.martin.javaverktygprojekt.validator.ReviewValidator;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewValidator reviewValidator;

    public ReviewService(ReviewRepository reviewRepository, ReviewValidator reviewValidator) {
        this.reviewRepository = reviewRepository;
        this.reviewValidator = reviewValidator;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }
    public Review createReview(Review review) {
        reviewValidator.validateReview(review);
        return reviewRepository.save(review);
    }
    public Review updateReview(Long id, Review updatedReview) {
        getReviewById(id);
        reviewValidator.validateReview(updatedReview);
        updatedReview.setId(id);
        return reviewRepository.save(updatedReview);
    }
    public void deleteReviewById(Long id) {
        Review existingReview = getReviewById(id);
        reviewRepository.delete(existingReview);
    }

}
