package br.ufrn.bora.web.rest;

import br.ufrn.bora.domain.RatingEvent;
import br.ufrn.bora.service.EventRegisterService;
import br.ufrn.bora.service.RatingEventService;
import br.ufrn.bora.service.dto.RatingEventDTO;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RatingEventResource {

    private final Logger log = LoggerFactory.getLogger(RatingEventResource.class);

    private final RatingEventService ratingEventService;

    private final EventRegisterService eventRegisterService;

    public RatingEventResource(RatingEventService ratingEventService, EventRegisterService eventRegisterService) {
        this.ratingEventService = ratingEventService;
        this.eventRegisterService = eventRegisterService;
    }

    @GetMapping("/rating-event")
    public ResponseEntity<List<RatingEvent>> getAll() {
        log.debug("REST request to get all RatingEvents");
        return ResponseEntity.ok().body(ratingEventService.findAll());
    }

    @PostMapping("/rating-event")
    public ResponseEntity<RatingEvent> createRatingEvent(@RequestBody RatingEventDTO ratingEventDTO) {
        log.debug("REST request to create RatingEvent : {}", ratingEventDTO);

        List<RatingEvent> ratingEvents = ratingEventService.findByEventIdAndUserId(ratingEventDTO.getEventId(), ratingEventDTO.getUserId());
        if (!ratingEvents.isEmpty()) {
            return ResponseEntity.status(409).build();
        }

        if (!eventRegisterService.hasUserAttendedEvent(ratingEventDTO.getEventId(), ratingEventDTO.getUserId())) {
            return ResponseEntity.badRequest().build();
        }

        RatingEvent ratingEvent = new RatingEvent();
        ratingEvent.setRating(ratingEventDTO.getRating());
        ratingEvent.setComment(ratingEventDTO.getComment());
        return ResponseEntity.ok().body(ratingEventService.save(ratingEvent));
    }

    @GetMapping("/rating-event/{eventId}")
    public ResponseEntity<List<RatingEvent>> getRatingEventByEventId(@PathVariable String eventId) {
        log.debug("REST request to get RatingEvent by eventId : {}", eventId);
        return ResponseEntity.ok().body(ratingEventService.findByEventId(eventId));
    }

    @GetMapping("/rating-event/{eventId}/{userId}")
    public ResponseEntity<List<RatingEvent>> getRatingEventByEventIdAndUserId(
        @PathVariable String eventId, @PathVariable String userId) {
        log.debug("REST request to get RatingEvent by eventId and userId : {}, {}", eventId, userId);
        return ResponseEntity.ok().body(ratingEventService.findByEventIdAndUserId(eventId, userId));
    }

    @GetMapping("/rating-event/user/{userId}")
    public ResponseEntity<List<RatingEvent>> getRatingEventByUserId(@PathVariable String userId) {
        log.debug("REST request to get RatingEvent by userId : {}", userId);
        return ResponseEntity.ok().body(ratingEventService.findByUserId(userId));
    }
}
