package br.ufrn.bora.repository;

import br.ufrn.bora.domain.Exemplo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Exemplo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExemploRepository extends MongoRepository<Exemplo, String> {}
