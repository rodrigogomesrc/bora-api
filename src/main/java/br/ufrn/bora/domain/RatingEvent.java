package br.ufrn.bora.domain;

import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "rating_event")
public class RatingEvent {

    @Id
    private String id;

    @Size(min = 1, max = 5)
    @Field("rating")
    private float rating;

    @Field("comment")
    private String comment;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("event")
    private Event events;
}
