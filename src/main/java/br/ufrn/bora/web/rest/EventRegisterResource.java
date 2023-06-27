package br.ufrn.bora.web.rest;

import br.ufrn.bora.domain.EventRegister;
import br.ufrn.bora.repository.EventRegisterRepository;
import br.ufrn.bora.service.EventRegisterService;
import br.ufrn.bora.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.ufrn.bora.domain.EventRegister}.
 */
@RestController
@RequestMapping("/api")
public class EventRegisterResource {

    private final Logger log = LoggerFactory.getLogger(EventRegisterResource.class);

    private static final String ENTITY_NAME = "eventRegister";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventRegisterService eventRegisterService;

    private final EventRegisterRepository eventRegisterRepository;

    public EventRegisterResource(EventRegisterService eventRegisterService, EventRegisterRepository eventRegisterRepository) {
        this.eventRegisterService = eventRegisterService;
        this.eventRegisterRepository = eventRegisterRepository;
    }

    /**
     * {@code POST  /event-registers} : Create a new eventRegister.
     *
     * @param eventRegister the eventRegister to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventRegister, or with status {@code 400 (Bad Request)} if the eventRegister has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-registers")
    public ResponseEntity<EventRegister> createEventRegister(@RequestBody EventRegister eventRegister) throws URISyntaxException {
        log.debug("REST request to save EventRegister : {}", eventRegister);
        if (eventRegister.getId() != null) {
            throw new BadRequestAlertException("A new eventRegister cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventRegister result = eventRegisterService.save(eventRegister);
        return ResponseEntity
            .created(new URI("/api/event-registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /event-registers/:id} : Updates an existing eventRegister.
     *
     * @param id the id of the eventRegister to save.
     * @param eventRegister the eventRegister to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventRegister,
     * or with status {@code 400 (Bad Request)} if the eventRegister is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventRegister couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-registers/{id}")
    public ResponseEntity<EventRegister> updateEventRegister(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody EventRegister eventRegister
    ) throws URISyntaxException {
        log.debug("REST request to update EventRegister : {}, {}", id, eventRegister);
        if (eventRegister.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventRegister.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRegisterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventRegister result = eventRegisterService.update(eventRegister);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventRegister.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-registers/:id} : Partial updates given fields of an existing eventRegister, field will ignore if it is null
     *
     * @param id the id of the eventRegister to save.
     * @param eventRegister the eventRegister to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventRegister,
     * or with status {@code 400 (Bad Request)} if the eventRegister is not valid,
     * or with status {@code 404 (Not Found)} if the eventRegister is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventRegister couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-registers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventRegister> partialUpdateEventRegister(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody EventRegister eventRegister
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventRegister partially : {}, {}", id, eventRegister);
        if (eventRegister.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventRegister.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventRegisterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventRegister> result = eventRegisterService.partialUpdate(eventRegister);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventRegister.getId())
        );
    }

    /**
     * {@code GET  /event-registers} : get all the eventRegisters.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventRegisters in body.
     */
    @GetMapping("/event-registers")
    public List<EventRegister> getAllEventRegisters(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all EventRegisters");
        return eventRegisterService.findAll();
    }

    /**
     * {@code GET  /event-registers/:id} : get the "id" eventRegister.
     *
     * @param id the id of the eventRegister to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventRegister, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-registers/{id}")
    public ResponseEntity<EventRegister> getEventRegister(@PathVariable String id) {
        log.debug("REST request to get EventRegister : {}", id);
        Optional<EventRegister> eventRegister = eventRegisterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventRegister);
    }

    /**
     * {@code DELETE  /event-registers/:id} : delete the "id" eventRegister.
     *
     * @param id the id of the eventRegister to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-registers/{id}")
    public ResponseEntity<Void> deleteEventRegister(@PathVariable String id) {
        log.debug("REST request to delete EventRegister : {}", id);
        eventRegisterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
