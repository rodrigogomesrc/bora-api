package br.ufrn.bora.web.rest;

import br.ufrn.bora.domain.RatingEvent;
import br.ufrn.bora.service.RatingEventService;
import br.ufrn.bora.service.dto.RatingEventDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RatingEventResource {

    //TODO: Add logger

    private final RatingEventService ratingEventService;

    public RatingEventResource(RatingEventService ratingEventService) {
        this.ratingEventService = ratingEventService;
    }

    @GetMapping("/rating-event")
    public ResponseEntity<List<RatingEvent>> getAll() {
        return ResponseEntity.ok().body(ratingEventService.findAll());
    }

    @PostMapping("/rating-event")
    public ResponseEntity<RatingEvent> createRatingEvent(@RequestBody RatingEventDTO ratingEventDTO) {
        //TODO: verify if said user already rated said event
        //TODO: verify if the user has gone to the event
        RatingEvent ratingEvent = new RatingEvent();
        ratingEvent.setRating(ratingEventDTO.getRating());
        ratingEvent.setComment(ratingEventDTO.getComment());
        return ResponseEntity.ok().body(ratingEventService.save(ratingEvent));
    }
    //TODO: add methods to get RatingEvent by event id and user id
}
