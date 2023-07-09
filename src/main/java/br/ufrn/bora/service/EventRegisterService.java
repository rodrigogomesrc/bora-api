package br.ufrn.bora.service;

import br.ufrn.bora.domain.Event;
import br.ufrn.bora.domain.EventRegister;
import br.ufrn.bora.repository.EventRegisterRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link EventRegister}.
 */
@Service
public class EventRegisterService {

    private final Logger log = LoggerFactory.getLogger(EventRegisterService.class);

    private final EventRegisterRepository eventRegisterRepository;

    public EventRegisterService(EventRegisterRepository eventRegisterRepository) {
        this.eventRegisterRepository = eventRegisterRepository;
    }

    /**
     * Save a eventRegister.
     *
     * @param eventRegister the entity to save.
     * @return the persisted entity.
     */
    public EventRegister save(EventRegister eventRegister) {
        log.debug("Request to save EventRegister : {}", eventRegister);
        return eventRegisterRepository.save(eventRegister);
    }

    /**
     * Save a eventRegister.
     *
     * @param eventId to get the event to add to EventRegister.
     * @param userId to get the user to add to EventRegister.
     * @return the persisted entity.
     */
    /*    public EventRegister save(String eventId, String userId) {
        EventRegister eventRegister = new EventRegister();
        eventRegister.setUser();

        //TODO: get event and user from database and add to eventRegister

        log.debug("Request to save EventRegister : {}", eventRegister);
        return eventRegisterRepository.save(eventRegister);
    }*/

    /**
     * Update a eventRegister.
     *
     * @param eventRegister the entity to save.
     * @return the persisted entity.
     */
    public EventRegister update(EventRegister eventRegister) {
        log.debug("Request to update EventRegister : {}", eventRegister);
        return eventRegisterRepository.save(eventRegister);
    }

    /**
     * Partially update a eventRegister.
     *
     * @param eventRegister the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventRegister> partialUpdate(EventRegister eventRegister) {
        log.debug("Request to partially update EventRegister : {}", eventRegister);

        return eventRegisterRepository
            .findById(eventRegister.getId())
            .map(existingEventRegister -> {
                return existingEventRegister;
            })
            .map(eventRegisterRepository::save);
    }

    /**
     * Get all the eventRegisters.
     *
     * @return the list of entities.
     */
    public List<EventRegister> findAll() {
        log.debug("Request to get all EventRegisters");
        return eventRegisterRepository.findAll();
    }

    /**
     * Get all the eventRegisters with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EventRegister> findAllWithEagerRelationships(Pageable pageable) {
        return eventRegisterRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one eventRegister by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EventRegister> findOne(String id) {
        log.debug("Request to get EventRegister : {}", id);
        return eventRegisterRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the eventRegister by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete EventRegister : {}", id);
        eventRegisterRepository.deleteById(id);
    }

    /**
     * Find an eventRegister by user id.
     * @param userId the id of the user
     */
    public Optional<EventRegister> findByUserId(String userId) {
        log.debug("Request to get EventRegister by user id : {}", userId);
        return eventRegisterRepository.findByUserId(userId);
    }

    /**
     * Check if an event is on the list of the events of the user
     * @param eventId the id of the event
     * @param userId the id of the user
     */
    public boolean hasUserAttendedEvent(String eventId, String userId) {
        log.debug("Request to check if event is registered : {}", eventId);
        Optional<EventRegister> eventRegister = findByUserId(userId);
        if (eventRegister.isPresent()) {
            List<String> events = eventRegister.get().getEvents().stream().map(Event::getId).collect(Collectors.toList());
            return events.contains(eventId);
        }
        return false;
    }
}
