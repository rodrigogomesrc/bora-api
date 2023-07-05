package br.ufrn.bora.service;

import br.ufrn.bora.domain.Event;
import br.ufrn.bora.domain.Location;
import br.ufrn.bora.repository.EventRepository;
import br.ufrn.bora.service.LocationService;
import br.ufrn.bora.service.dto.EventDTO;
import br.ufrn.bora.service.mapper.EventMapper;
import br.ufrn.bora.service.mapper.LocationMapper;
import br.ufrn.bora.service.mapper.TicketMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Event}.
 */
@Service
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public EventService(
        EventRepository eventRepository,
        EventMapper eventMapper,
        LocationService locationService,
        LocationMapper locationMapper,
        TicketService ticketService,
        TicketMapper ticketMapper
    ) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    public EventDTO save(EventDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event.setLocation(locationMapper.toEntity(locationService.findOne(event.getLocation().getId()).get()));
        event.setTicket(ticketMapper.toEntity(ticketService.findOne(event.getTicket().getId()).get()));
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    /**
     * Update a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    public EventDTO update(EventDTO eventDTO) {
        log.debug("Request to update Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    /**
     * Partially update a event.
     *
     * @param eventDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventDTO> partialUpdate(EventDTO eventDTO) {
        log.debug("Request to partially update Event : {}", eventDTO);

        return eventRepository
            .findById(eventDTO.getId())
            .map(existingEvent -> {
                eventMapper.partialUpdate(existingEvent, eventDTO);

                return existingEvent;
            })
            .map(eventRepository::save)
            .map(eventMapper::toDto);
    }

    /**
     * Get all the events.
     *
     * @return the list of entities.
     */
    public List<EventDTO> findAll() {
        log.debug("Request to get all Events");
        return eventRepository.findAll().stream().map(eventMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one event by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EventDTO> findOne(String id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id).map(eventMapper::toDto);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }

    /*
     * Get an event by its id
     *
     * @return the event.
     */
    public Optional<Event> getEventById(String eventId) {
        log.debug("Request to get Event : {}", eventId);
        return eventRepository.findById(eventId);
    }
}
