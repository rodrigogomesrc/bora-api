package br.ufrn.bora.service;

import br.ufrn.bora.domain.Exemplo;
import br.ufrn.bora.repository.ExemploRepository;
import br.ufrn.bora.service.dto.ExemploDTO;
import br.ufrn.bora.service.mapper.ExemploMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Exemplo}.
 */
@Service
public class ExemploService {

    private final Logger log = LoggerFactory.getLogger(ExemploService.class);

    private final ExemploRepository exemploRepository;

    private final ExemploMapper exemploMapper;

    public ExemploService(ExemploRepository exemploRepository, ExemploMapper exemploMapper) {
        this.exemploRepository = exemploRepository;
        this.exemploMapper = exemploMapper;
    }

    /**
     * Save a exemplo.
     *
     * @param exemploDTO the entity to save.
     * @return the persisted entity.
     */
    public ExemploDTO save(ExemploDTO exemploDTO) {
        log.debug("Request to save Exemplo : {}", exemploDTO);
        Exemplo exemplo = exemploMapper.toEntity(exemploDTO);
        exemplo = exemploRepository.save(exemplo);
        return exemploMapper.toDto(exemplo);
    }

    /**
     * Update a exemplo.
     *
     * @param exemploDTO the entity to save.
     * @return the persisted entity.
     */
    public ExemploDTO update(ExemploDTO exemploDTO) {
        log.debug("Request to update Exemplo : {}", exemploDTO);
        Exemplo exemplo = exemploMapper.toEntity(exemploDTO);
        exemplo = exemploRepository.save(exemplo);
        return exemploMapper.toDto(exemplo);
    }

    /**
     * Partially update a exemplo.
     *
     * @param exemploDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExemploDTO> partialUpdate(ExemploDTO exemploDTO) {
        log.debug("Request to partially update Exemplo : {}", exemploDTO);

        return exemploRepository
            .findById(exemploDTO.getId())
            .map(existingExemplo -> {
                exemploMapper.partialUpdate(existingExemplo, exemploDTO);

                return existingExemplo;
            })
            .map(exemploRepository::save)
            .map(exemploMapper::toDto);
    }

    /**
     * Get all the exemplos.
     *
     * @return the list of entities.
     */
    public List<ExemploDTO> findAll() {
        log.debug("Request to get all Exemplos");
        return exemploRepository.findAll().stream().map(exemploMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one exemplo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ExemploDTO> findOne(String id) {
        log.debug("Request to get Exemplo : {}", id);
        return exemploRepository.findById(id).map(exemploMapper::toDto);
    }

    /**
     * Delete the exemplo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Exemplo : {}", id);
        exemploRepository.deleteById(id);
    }
}
