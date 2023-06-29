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
     * {@code POST  /event-registers/:eventId/:userId} : Create a new eventRegister.
     *
     * @param event id of the event to register.
     * @param user id of the user to register.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventRegister, or with status {@code 400 (Bad Request)} if the eventRegister has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-registers/{eventId}/{userId}")
    public ResponseEntity<EventRegister> createEventRegister(
        @PathVariable(value = "eventId", required = true) final int eventId,
        @PathVariable(value = "userId", required = true) final int userId
    ) throws URISyntaxException {
        log.debug("REST request to save EventRegister : {}", eventId);

        //TODO: check if the user and event exists
        //TODO: check if the event is valid according to the issue requirements

        EventRegister result = eventRegisterService.save(eventId, userId);
        return ResponseEntity
            .created(new URI("/api/event-registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

}
