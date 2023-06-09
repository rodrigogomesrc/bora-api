package br.ufrn.bora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufrn.bora.IntegrationTest;
import br.ufrn.bora.domain.Exemplo;
import br.ufrn.bora.repository.ExemploRepository;
import br.ufrn.bora.service.dto.ExemploDTO;
import br.ufrn.bora.service.mapper.ExemploMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ExemploResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExemploResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/exemplos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ExemploRepository exemploRepository;

    @Autowired
    private ExemploMapper exemploMapper;

    @Autowired
    private MockMvc restExemploMockMvc;

    private Exemplo exemplo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exemplo createEntity() {
        Exemplo exemplo = new Exemplo().nome(DEFAULT_NOME);
        return exemplo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exemplo createUpdatedEntity() {
        Exemplo exemplo = new Exemplo().nome(UPDATED_NOME);
        return exemplo;
    }

    @BeforeEach
    public void initTest() {
        exemploRepository.deleteAll();
        exemplo = createEntity();
    }

    @Test
    void createExemplo() throws Exception {
        int databaseSizeBeforeCreate = exemploRepository.findAll().size();
        // Create the Exemplo
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);
        restExemploMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exemploDTO)))
            .andExpect(status().isCreated());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeCreate + 1);
        Exemplo testExemplo = exemploList.get(exemploList.size() - 1);
        assertThat(testExemplo.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    void createExemploWithExistingId() throws Exception {
        // Create the Exemplo with an existing ID
        exemplo.setId("existing_id");
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);

        int databaseSizeBeforeCreate = exemploRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExemploMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exemploDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllExemplos() throws Exception {
        // Initialize the database
        exemploRepository.save(exemplo);

        // Get all the exemploList
        restExemploMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exemplo.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    void getExemplo() throws Exception {
        // Initialize the database
        exemploRepository.save(exemplo);

        // Get the exemplo
        restExemploMockMvc
            .perform(get(ENTITY_API_URL_ID, exemplo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exemplo.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    void getNonExistingExemplo() throws Exception {
        // Get the exemplo
        restExemploMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingExemplo() throws Exception {
        // Initialize the database
        exemploRepository.save(exemplo);

        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();

        // Update the exemplo
        Exemplo updatedExemplo = exemploRepository.findById(exemplo.getId()).get();
        updatedExemplo.nome(UPDATED_NOME);
        ExemploDTO exemploDTO = exemploMapper.toDto(updatedExemplo);

        restExemploMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exemploDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exemploDTO))
            )
            .andExpect(status().isOk());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
        Exemplo testExemplo = exemploList.get(exemploList.size() - 1);
        assertThat(testExemplo.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    void putNonExistingExemplo() throws Exception {
        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();
        exemplo.setId(UUID.randomUUID().toString());

        // Create the Exemplo
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExemploMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exemploDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exemploDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchExemplo() throws Exception {
        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();
        exemplo.setId(UUID.randomUUID().toString());

        // Create the Exemplo
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemploMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exemploDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamExemplo() throws Exception {
        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();
        exemplo.setId(UUID.randomUUID().toString());

        // Create the Exemplo
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemploMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exemploDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateExemploWithPatch() throws Exception {
        // Initialize the database
        exemploRepository.save(exemplo);

        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();

        // Update the exemplo using partial update
        Exemplo partialUpdatedExemplo = new Exemplo();
        partialUpdatedExemplo.setId(exemplo.getId());

        restExemploMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExemplo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExemplo))
            )
            .andExpect(status().isOk());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
        Exemplo testExemplo = exemploList.get(exemploList.size() - 1);
        assertThat(testExemplo.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    void fullUpdateExemploWithPatch() throws Exception {
        // Initialize the database
        exemploRepository.save(exemplo);

        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();

        // Update the exemplo using partial update
        Exemplo partialUpdatedExemplo = new Exemplo();
        partialUpdatedExemplo.setId(exemplo.getId());

        partialUpdatedExemplo.nome(UPDATED_NOME);

        restExemploMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExemplo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExemplo))
            )
            .andExpect(status().isOk());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
        Exemplo testExemplo = exemploList.get(exemploList.size() - 1);
        assertThat(testExemplo.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    void patchNonExistingExemplo() throws Exception {
        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();
        exemplo.setId(UUID.randomUUID().toString());

        // Create the Exemplo
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExemploMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, exemploDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exemploDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchExemplo() throws Exception {
        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();
        exemplo.setId(UUID.randomUUID().toString());

        // Create the Exemplo
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemploMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exemploDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamExemplo() throws Exception {
        int databaseSizeBeforeUpdate = exemploRepository.findAll().size();
        exemplo.setId(UUID.randomUUID().toString());

        // Create the Exemplo
        ExemploDTO exemploDTO = exemploMapper.toDto(exemplo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemploMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(exemploDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exemplo in the database
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteExemplo() throws Exception {
        // Initialize the database
        exemploRepository.save(exemplo);

        int databaseSizeBeforeDelete = exemploRepository.findAll().size();

        // Delete the exemplo
        restExemploMockMvc
            .perform(delete(ENTITY_API_URL_ID, exemplo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exemplo> exemploList = exemploRepository.findAll();
        assertThat(exemploList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
