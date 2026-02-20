package se.iths.martin.javaverktygprojekt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.martin.javaverktygprojekt.exceptions.ReviewNotFoundException;
import se.iths.martin.javaverktygprojekt.exceptions.ReviewValidationException;
import se.iths.martin.javaverktygprojekt.model.Review;
import se.iths.martin.javaverktygprojekt.repository.ReviewRepository;
import se.iths.martin.javaverktygprojekt.validator.ReviewValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewValidator reviewValidator;

    @Test
    void getAllReviewsTest() {
        List<Review> reviews = List.of(
                makeReview("Kevin", 5, "Fin tröja", "Väldigt bra tröja!", LocalDate.now()),
                makeReview("Håkan", 3, "Ok byxor", "Helt okej", LocalDate.now().minusDays(2))
        );

        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> result = reviewService.getAllReviews();

        assertEquals(2, result.size());
        verify(reviewRepository).findAll();
    }

    @Test
    void getReviewByIdTest() {
        Review review = makeReview("Kevin", 5, "Bra passform", "Klagar inte!", LocalDate.now());
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewService.getReviewById(1L);

        assertEquals(review, result);
        verify(reviewRepository).findById(1L);
    }

    @Test
    void getReviewByIdNotFoundTest() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewById(1L));

        verify(reviewRepository).findById(1L);
    }

    @Test
    void createReviewTest() {
        Review review = makeReview("Kevin", 5, "BRA SKJORTA!!!!!", "En väldigt bra skjorta som" +
                "faktiskt satt bra på mig!", LocalDate.now());

        when(reviewRepository.save(review)).thenReturn(review);

        Review result = reviewService.createReview(review);

        assertEquals(review, result);
        verify(reviewValidator).validateReview(review);
        verify(reviewRepository).save(review);
    }

    @Test
    void invalidReviewTest() {
        Review review = makeReview("", 2, "för liten", "inte glad...", LocalDate.now());

        doThrow(new ReviewValidationException("Invalid review"))
                .when(reviewValidator).validateReview(review);

        assertThrows(ReviewValidationException.class, () -> reviewService.createReview(review));

        verify(reviewRepository, never()).save(any());
    }

    @Test
    void updateReviewTest() {
        Review existingReview = makeReview("Kevin", 4, "Röd kofta", "Den fungerar...", LocalDate.now().minusDays(1));
        existingReview.setId(1L);

        Review updatedReview = makeReview("Kevin", 5, "Röd kofta", "Nu sitter den bättre!", LocalDate.now());

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenAnswer(inv -> inv.getArgument(0));

        Review result = reviewService.updateReview(1L, updatedReview);

        assertEquals(1L, result.getId());
        assertEquals(5, result.getRating());
        assertEquals("Röd kofta", result.getTitle());

        verify(reviewRepository).findById(1L);
        verify(reviewValidator).validateReview(updatedReview);
        verify(reviewRepository).save(updatedReview);
    }

    @Test
    void invalidUpdateReviewNotFoundTest() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        Review updated = makeReview("Kevin", 5, "Toppen", "Text", LocalDate.now());

        assertThrows(ReviewNotFoundException.class, () -> reviewService.updateReview(1L, updated));

        verify(reviewRepository, never()).save(any());
    }

    @Test
    void deleteReviewTest() {
        Review review = makeReview("Kevin", 5, "Toppen", "Väldigt bra", LocalDate.now());
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReviewById(1L);

        verify(reviewRepository).delete(review);
    }

    @Test
    void deleteReviewNotFoundTest() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReviewById(1L));

        verify(reviewRepository, never()).delete(any());
    }

    private Review makeReview(String reviewerName, int rating, String title, String comment, LocalDate createdDate) {
        Review r = new Review();
        r.setReviewerName(reviewerName);
        r.setRating(rating);
        r.setTitle(title);
        r.setComment(comment);
        r.setCreatedDate(createdDate);
        return r;
    }
}
