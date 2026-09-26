package bikr.model;

import bikr.model.enums.StarRating;

public class Review {
    private int reviewId;
    private int racerId;
    private int raceId;
    private StarRating rating;
    private String comment;

    //default constructor
    public Review() { }

    //constructor
    public Review(int racerId, int raceId, StarRating rating, String comment) {
        this.racerId = racerId;
        this.raceId = raceId;
        this.rating = rating;
        this.comment = comment;
    }

    //setters and getters
    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getRacerId() { return racerId; }
    public void setRacerId(int racerId) { this.racerId = racerId; }

    public int getRaceId() { return raceId; }
    public void setRaceId(int raceId) { this.raceId = raceId; }

    public StarRating getRating() { return rating; }
    public void setRating(StarRating rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}