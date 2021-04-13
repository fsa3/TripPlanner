package hotelSystem.entities;

import java.util.ArrayList;

public class Review {
    private String reviewId;
    private String comment;
    private Double stars;
    private ArrayList<String> replies;

    public Review(String reviewId, String comment, Double stars, ArrayList<String> replies) {
        this.reviewId = reviewId;
        this.comment = comment;
        this.stars = stars;
        this.replies = replies;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public ArrayList<String> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<String> replies) {
        this.replies = replies;
    }
}
