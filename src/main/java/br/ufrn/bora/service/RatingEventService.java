package br.ufrn.bora.service;

import br.ufrn.bora.domain.RatingEvent;
import br.ufrn.bora.repository.RatingEventRepository;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RatingEventService {

    private final Logger log = LoggerFactory.getLogger(RatingEventService.class);

    RatingEventRepository ratingEventRepository;

    public RatingEventService(RatingEventRepository ratingEventRepository) {
        this.ratingEventRepository = ratingEventRepository;
    }

    /**
     * Save a ratingEvent.
     *
     * @param ratingEvent the entity to save.
     * @return the persisted entity.
     */
    public RatingEvent save(RatingEvent ratingEvent) {
        log.debug("Request to save RatingEvent : {}", ratingEvent);
        return ratingEventRepository.save(ratingEvent);
    }

    /**
     * Get all the ratingEvents.
     *
     * @return the list of entities.
     */
    public List<RatingEvent> findAll() {
        log.debug("Request to get all RatingEvents");
        return ratingEventRepository.findAll();
    }

    /**
     * Get a list of ratingEvents by eventId.
     *
     * @param eventId the id of the event.
     * @return the list of entities.
     */
    public List<RatingEvent> findByEventId(String eventId) {
        log.debug("Request to get all RatingEvents by eventId");
        return ratingEventRepository.findByEventId(eventId);
    }

    /**
     * Get a list of ratingEvents by userId.
     *
     * @param userId the id of the user.
     * @return the list of entities.
     */
    public List<RatingEvent> findByUserId(String userId) {
        log.debug("Request to get all RatingEvents by userId");
        return ratingEventRepository.findByUserId(userId);
    }

    /**
     * Get a list of ratingEvents by eventId and userId.
     *
     * @param eventId the id of the event.
     * @param userId the id of the user.
     * @return the list of entities.
     */
    public List<RatingEvent> findByEventIdAndUserId(String eventId, String userId) {
        log.debug("Request to get all RatingEvents by eventId and userId");
        return ratingEventRepository.findByUserIdAndEventId(userId, eventId);
    }
}
