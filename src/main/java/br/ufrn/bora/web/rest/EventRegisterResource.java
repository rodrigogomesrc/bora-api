package br.ufrn.bora.web.rest;

import br.ufrn.bora.domain.Event;
import br.ufrn.bora.domain.EventRegister;
import br.ufrn.bora.domain.User;
import br.ufrn.bora.repository.EventRegisterRepository;
import br.ufrn.bora.service.EventRegisterService;
import br.ufrn.bora.service.EventService;
import br.ufrn.bora.service.UserService;
import br.ufrn.bora.service.dto.EventRegisterDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

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

    private final EventService eventService;

    private final UserService userService;

    private final EventRegisterRepository eventRegisterRepository;

    public EventRegisterResource(
        EventRegisterService eventRegisterService,
        EventService eventService,
        UserService userService,
        EventRegisterRepository eventRegisterRepository
    ) {
        this.eventRegisterService = eventRegisterService;
        this.eventService = eventService;
        this.userService = userService;
        this.eventRegisterRepository = eventRegisterRepository;
    }

    /**
     * {@code POST  /event-registers} : Create a new eventRegister.
     *
     * @param eventRegister the eventRegister to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventRegister, or with status {@code 400 (Bad Request)} if the eventRegister has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    /*@PostMapping("/event-registers")
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
    }*/

    /**
     * {@code POST  /event-registers : Create a new eventRegister.
     *
     * @body event id of the event to register.
     * @body user id of the user to register.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventRegister, or with status {@code 400 (Bad Request)} if the eventRegister has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-registers")
    public ResponseEntity<EventRegister> createEventRegister(@RequestBody EventRegisterDTO eventDto) throws URISyntaxException {
        log.debug("REST request to save EventRegister : {}", eventDto.getEventId());

        //check if the user and event exists
        Optional<User> opUser = userService.getUserById(eventDto.getUserId());
        if (!opUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Event> opEvent = eventService.getEventById(eventDto.getEventId());
        if (!opEvent.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        //check if the user is already registered
        Optional<EventRegister> opEventRegister = eventRegisterService.findByUserId(opUser.get().getId());
        EventRegister eventRegister;
        EventRegister result;
        if (opEventRegister.isPresent()) {
            eventRegister = opEventRegister.get();
            eventRegister.addEvent(opEvent.get());
            result = eventRegisterService.update(eventRegister);
        } else {
            eventRegister = new EventRegister();
            eventRegister.setUser(opUser.get());
            eventRegister.addEvent(opEvent.get());
            result = eventRegisterService.save(eventRegister);
        }

        return ResponseEntity
            .created(new URI("/api/event-registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code GET /event-registers/:userId} : get the event register associated with the user.
     * @param userId the id of the user to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the events, or with status {@code 404 (Not Found)}.
     *
     */
    @GetMapping("/event-registers/{userId}")
    public ResponseEntity<Set<Event>> getEventRegisterByUserId(@PathVariable String userId) {
        log.debug("REST request to get EventRegister : {}", userId);
        Optional<EventRegister> eventRegister = eventRegisterService.findByUserId(userId);
        if (eventRegister.isPresent()) {
            return ResponseEntity.ok().body(eventRegister.get().getEvents());
        }
        return ResponseEntity.notFound().build();
    }
}
