package br.ufrn.bora.web.rest;

import br.ufrn.bora.repository.ExemploRepository;
import br.ufrn.bora.service.ExemploService;
import br.ufrn.bora.service.dto.ExemploDTO;
import br.ufrn.bora.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.ufrn.bora.domain.Exemplo}.
 */
@RestController
@RequestMapping("/api")
public class ExemploResource {

    private final Logger log = LoggerFactory.getLogger(ExemploResource.class);

    private static final String ENTITY_NAME = "exemplo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExemploService exemploService;

    private final ExemploRepository exemploRepository;

    public ExemploResource(ExemploService exemploService, ExemploRepository exemploRepository) {
        this.exemploService = exemploService;
        this.exemploRepository = exemploRepository;
    }

    /**
     * {@code POST  /exemplos} : Create a new exemplo.
     *
     * @param exemploDTO the exemploDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exemploDTO, or with status {@code 400 (Bad Request)} if the exemplo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exemplos")
    public ResponseEntity<ExemploDTO> createExemplo(@RequestBody ExemploDTO exemploDTO) throws URISyntaxException {
        log.debug("REST request to save Exemplo : {}", exemploDTO);
        if (exemploDTO.getId() != null) {
            throw new BadRequestAlertException("A new exemplo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExemploDTO result = exemploService.save(exemploDTO);
        return ResponseEntity
            .created(new URI("/api/exemplos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /exemplos/:id} : Updates an existing exemplo.
     *
     * @param id the id of the exemploDTO to save.
     * @param exemploDTO the exemploDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exemploDTO,
     * or with status {@code 400 (Bad Request)} if the exemploDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exemploDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exemplos/{id}")
    public ResponseEntity<ExemploDTO> updateExemplo(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ExemploDTO exemploDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Exemplo : {}, {}", id, exemploDTO);
        if (exemploDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exemploDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exemploRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExemploDTO result = exemploService.update(exemploDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exemploDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /exemplos/:id} : Partial updates given fields of an existing exemplo, field will ignore if it is null
     *
     * @param id the id of the exemploDTO to save.
     * @param exemploDTO the exemploDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exemploDTO,
     * or with status {@code 400 (Bad Request)} if the exemploDTO is not valid,
     * or with status {@code 404 (Not Found)} if the exemploDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the exemploDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exemplos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExemploDTO> partialUpdateExemplo(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ExemploDTO exemploDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Exemplo partially : {}, {}", id, exemploDTO);
        if (exemploDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exemploDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exemploRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExemploDTO> result = exemploService.partialUpdate(exemploDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exemploDTO.getId())
        );
    }

    /**
     * {@code GET  /exemplos} : get all the exemplos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exemplos in body.
     */
    @GetMapping("/exemplos")
    public List<ExemploDTO> getAllExemplos() {
        log.debug("REST request to get all Exemplos");
        return exemploService.findAll();
    }

    /**
     * {@code GET  /exemplos/:id} : get the "id" exemplo.
     *
     * @param id the id of the exemploDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exemploDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exemplos/{id}")
    public ResponseEntity<ExemploDTO> getExemplo(@PathVariable String id) {
        log.debug("REST request to get Exemplo : {}", id);
        Optional<ExemploDTO> exemploDTO = exemploService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exemploDTO);
    }

    /**
     * {@code DELETE  /exemplos/:id} : delete the "id" exemplo.
     *
     * @param id the id of the exemploDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exemplos/{id}")
    public ResponseEntity<Void> deleteExemplo(@PathVariable String id) {
        log.debug("REST request to delete Exemplo : {}", id);
        exemploService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
