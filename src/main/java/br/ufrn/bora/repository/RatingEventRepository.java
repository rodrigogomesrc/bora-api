package br.ufrn.bora.repository;

import br.ufrn.bora.domain.Event;
import br.ufrn.bora.domain.RatingEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingEventRepository extends MongoRepository<RatingEvent, String> {

    List<RatingEvent> findByEventId(String eventId);

    List<RatingEvent> findByUserId(String userId);

    List<RatingEvent> findByUserIdAndEventId(String userId, String eventId);

}

