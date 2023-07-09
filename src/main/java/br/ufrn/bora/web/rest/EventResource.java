package br.ufrn.bora.web.rest;

import br.ufrn.bora.repository.EventRepository;
import br.ufrn.bora.service.EventService;
import br.ufrn.bora.service.dto.EventDTO;
import br.ufrn.bora.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.ufrn.bora.domain.Event}.
 */
@RestController
@RequestMapping("/api")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    private static final String ENTITY_NAME = "event";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventService eventService;

    private final EventRepository eventRepository;

    public EventResource(EventService eventService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    /**
     * {@code POST  /events} : Create a new event.
     *
     * @param eventDTO the eventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new eventDTO, or with status {@code 400 (Bad Request)} if
     *         the event has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) throws URISyntaxException {
        log.debug("REST request to save Event : {}", eventDTO);
        if (eventDTO.getId() != null) {
            throw new BadRequestAlertException("A new event cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventDTO result = eventService.save(eventDTO);
        return ResponseEntity
            .created(new URI("/api/events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /events/:id} : Updates an existing event.
     *
     * @param id       the id of the eventDTO to save.
     * @param eventDTO the eventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated eventDTO,
     *         or with status {@code 400 (Bad Request)} if the eventDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the eventDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> updateEvent(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody EventDTO eventDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Event : {}, {}", id, eventDTO);
        if (eventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventDTO result = eventService.update(eventDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /events/:id} : Partial updates given fields of an existing
     * event, field will ignore if it is null
     *
     * @param id       the id of the eventDTO to save.
     * @param eventDTO the eventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated eventDTO,
     *         or with status {@code 400 (Bad Request)} if the eventDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the eventDTO is not found,
     *         or with status {@code 500 (Internal Server Error)} if the eventDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/events/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventDTO> partialUpdateEvent(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody EventDTO eventDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Event partially : {}, {}", id, eventDTO);
        if (eventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventDTO> result = eventService.partialUpdate(eventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventDTO.getId())
        );
    }

    /**
     * {@code GET  /events} : get all the events.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of events in body.
     */
    @GetMapping("/events")
    public List<EventDTO> getAllEvents() {
        log.debug("REST request to get all Events");
        return eventService.findAll();
    }

    /**
     * {@code GET  /search} : get all the events by search criteria.
     *
     * @param dateStart        inicial date of event
     * @param address          of event
     * @param city             of event
     * @param state            of event
     * @param lowerTicketValue ticket lower bound price
     * @param upperTicketValue ticket upper bound price
     * @return list of events that match the search criteria
     */
    @GetMapping("/events/search")
    public List<EventDTO> searchEvent(
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime dateStart,
        @RequestParam(required = false) String address,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) Float lowerTicketValue,
        @RequestParam(required = false) Float upperTicketValue
    ) {
        log.debug("REST request to get all Events by dateStart, address, city, state, lowerTicketValue, upperTicketValue");
        return eventService.searchEvent(dateStart, address, city, state, lowerTicketValue, upperTicketValue);
    }

    /**
     * {@code GET  /events/:id} : get the "id" event.
     *
     * @param id the id of the eventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the eventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable String id) {
        log.debug("REST request to get Event : {}", id);
        Optional<EventDTO> eventDTO = eventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventDTO);
    }

    /**
     * {@code DELETE  /events/:id} : delete the "id" event.
     *
     * @param id the id of the eventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        log.debug("REST request to delete Event : {}", id);
        eventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
