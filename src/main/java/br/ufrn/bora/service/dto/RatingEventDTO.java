package br.ufrn.bora.service.dto;

public class RatingEventDTO {

    private String userId;
    private String eventId;
    private Float rating;

    private String comment;

    public RatingEventDTO() {}

    public RatingEventDTO(String userId, String eventId, Float rating, String comment) {
        this.userId = userId;
        this.eventId = eventId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
