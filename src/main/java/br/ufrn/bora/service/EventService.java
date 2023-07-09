package br.ufrn.bora.service;

import br.ufrn.bora.domain.Event;
import br.ufrn.bora.repository.EventRepository;
import br.ufrn.bora.repository.LocationRepository;
import br.ufrn.bora.repository.TicketRepository;
import br.ufrn.bora.service.dto.EventDTO;
import br.ufrn.bora.service.mapper.EventMapper;
import br.ufrn.bora.service.mapper.LocationMapper;
import br.ufrn.bora.service.mapper.TicketMapper;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private final LocationRepository locationRepository;
    private final TicketRepository ticketRepository;

    public EventService(
        EventRepository eventRepository,
        EventMapper eventMapper,
        LocationService locationService,
        LocationMapper locationMapper,
        TicketService ticketService,
        TicketMapper ticketMapper,
        LocationRepository locationRepository,
        TicketRepository ticketRepository
    ) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.locationRepository = locationRepository;
        this.ticketRepository = ticketRepository;
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

    public List<EventDTO> searchEvent(
        LocalDateTime dateStart,
        String address,
        String city,
        String state,
        Float lowerTicketValue,
        Float upperTicketValue
    ) {
        Stream<Event> events = eventRepository.findAll().stream();

        if (dateStart != null) {
            events = events.filter(event -> event.getDateStart() != null);
            events = events.filter(event -> event.getDateStart().isEqual(dateStart));
        }

        if (address != null || city != null || state != null) {
            events =
                events
                    .filter(event -> event.getLocation() != null)
                    .peek(event -> event.setLocation(locationRepository.findById(event.getLocation().getId()).get()));
        }

        if (address != null) events = events.filter(event -> event.getLocation().getAddress().equals(address));
        if (city != null) events = events.filter(event -> event.getLocation().getCity().equals(city));
        if (state != null) events = events.filter(event -> event.getLocation().getState().equals(state));

        if (lowerTicketValue != null && upperTicketValue != null) {
            events =
                events.peek(event ->
                    event.setTicket(
                        ticketRepository
                            .findById(event.getTicket().getId())
                            .orElseThrow(() -> new RuntimeException("Some event has no valid ticket"))
                    )
                );

            events =
                events.filter(event -> {
                    Float value = event.getTicket().getPrice();

                    return (
                        (value.compareTo(lowerTicketValue) == 0 || value.compareTo(lowerTicketValue) > 0) &&
                        (value.compareTo(upperTicketValue) == 0 || value.compareTo(upperTicketValue) < 0)
                    );
                });
        }

        return events.map(eventMapper::toDto).collect(Collectors.toList());
    }
}
