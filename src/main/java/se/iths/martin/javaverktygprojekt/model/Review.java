package se.iths.martin.javaverktygprojekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;


@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewerName;
    private int rating;
    private String title;
    private String comment;
    private LocalDate createdDate;

    public Review() {

    }

    public Review(String reviewerName, int rating, String title, String comment, LocalDate createdDate) {
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.createdDate = createdDate;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

}
