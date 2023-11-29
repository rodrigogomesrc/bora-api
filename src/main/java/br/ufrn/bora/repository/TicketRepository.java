package br.ufrn.bora.repository;

import br.ufrn.bora.domain.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Ticket entity.
 */
//@SuppressWarnings("unused")
//@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {}
