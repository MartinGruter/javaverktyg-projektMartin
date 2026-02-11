package se.iths.martin.javaverktygprojekt.service;

import se.iths.martin.javaverktygprojekt.exceptions.ReviewNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Review;
import se.iths.martin.javaverktygprojekt.repository.ReviewRepository;

import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }
    public Review updateReview(Long id, Review updatedReview) {
        getReviewById(id);

        updatedReview.setId(id);
        return reviewRepository.save(updatedReview);
    }
    public void deleteReviewById(Long id) {
        Review existingReview = getReviewById(id);
        reviewRepository.delete(existingReview);
    }
}
