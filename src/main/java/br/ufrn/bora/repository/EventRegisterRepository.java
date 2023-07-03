package br.ufrn.bora.repository;

import br.ufrn.bora.domain.EventRegister;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the EventRegister entity.
 */
@Repository
public interface EventRegisterRepository extends MongoRepository<EventRegister, String> {
    @Query("{}")
    Page<EventRegister> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<EventRegister> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<EventRegister> findOneWithEagerRelationships(String id);

    @Query("{'user.id': ?0}")
    Optional<EventRegister> findByUserId(String userId);
}
