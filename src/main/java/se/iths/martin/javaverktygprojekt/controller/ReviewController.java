package se.iths.martin.javaverktygprojekt.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.martin.javaverktygprojekt.exceptions.ReviewNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Review;
import se.iths.martin.javaverktygprojekt.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "reviews/list";
    }
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "reviews/view";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("review", new Review());
        return "reviews/create";
    }
    @PostMapping
    public String create(@ModelAttribute Review review) {
        reviewService.createReview(review);
        return "redirect:/reviews";
    }
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "reviews/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Review review) {
        reviewService.updateReview(id, review);
        return "redirect:/reviews";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return "redirect:/reviews";
    }
    @ExceptionHandler(ReviewNotFoundException.class)
    public String handleNotFound() {
        return "reviews/not-found";
    }


}
