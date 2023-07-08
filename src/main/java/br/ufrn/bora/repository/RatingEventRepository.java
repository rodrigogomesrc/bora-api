package br.ufrn.bora.repository;

import br.ufrn.bora.domain.Event;
import br.ufrn.bora.domain.RatingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingEventRepository extends MongoRepository<RatingEvent, String> {}
