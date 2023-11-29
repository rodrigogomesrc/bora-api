package br.ufrn.bora.service;

import br.ufrn.bora.domain.Ticket;
import br.ufrn.bora.repository.TicketRepository;
import br.ufrn.bora.service.dto.TicketDTO;
import br.ufrn.bora.service.mapper.TicketMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Ticket}.
 */
//@Service
public class TicketService {

    private final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    //@requires ticketRepository != null;
    //@requires ticketMapper != null;
    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    //@requires ticketDTO != null;
    //@ensures \result != null;
    //@pure
    public TicketDTO save(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket : {}", ticketDTO);
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    //@requires ticketDTO != null;
    //@ensures \result != null;
    //@pure
    public TicketDTO update(TicketDTO ticketDTO) {
        log.debug("Request to update Ticket : {}", ticketDTO);
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    //@requires ticketDTO != null;
    //@ensures \result != null || \result.isEmpty();
    //@pure
    public Optional<TicketDTO> partialUpdate(TicketDTO ticketDTO) {
        log.debug("Request to partially update Ticket : {}", ticketDTO);

        return ticketRepository
            .findById(ticketDTO.getId())
            .map(existingTicket -> {
                ticketMapper.partialUpdate(existingTicket, ticketDTO);
                return existingTicket;
            })
            .map(ticketRepository::save)
            .map(ticketMapper::toDto);
    }

    //@ensures \result != null;
    //@pure
    public List<TicketDTO> findAll() {
        log.debug("Request to get all Tickets");
        return ticketRepository.findAll().stream().map(ticketMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    //@requires id != null;
    //@ensures \result != null;
    //@pure
    public Optional<TicketDTO> findOne(String id) {
        log.debug("Request to get Ticket : {}", id);
        return ticketRepository.findById(id).map(ticketMapper::toDto);
    }

    //@requires id != null;
    public void delete(String id) {
        log.debug("Request to delete Ticket : {}", id);
        ticketRepository.deleteById(id);
    }
}
