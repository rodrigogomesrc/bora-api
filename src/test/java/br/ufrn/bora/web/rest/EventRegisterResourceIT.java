package br.ufrn.bora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.ufrn.bora.IntegrationTest;
import br.ufrn.bora.domain.EventRegister;
import br.ufrn.bora.repository.EventRegisterRepository;
import br.ufrn.bora.service.EventRegisterService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link EventRegisterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EventRegisterResourceIT {

    private static final String ENTITY_API_URL = "/api/event-registers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EventRegisterRepository eventRegisterRepository;

    @Mock
    private EventRegisterRepository eventRegisterRepositoryMock;

    @Mock
    private EventRegisterService eventRegisterServiceMock;

    @Autowired
    private MockMvc restEventRegisterMockMvc;

    private EventRegister eventRegister;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventRegister createEntity() {
        EventRegister eventRegister = new EventRegister();
        return eventRegister;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventRegister createUpdatedEntity() {
        EventRegister eventRegister = new EventRegister();
        return eventRegister;
    }

    @BeforeEach
    public void initTest() {
        eventRegisterRepository.deleteAll();
        eventRegister = createEntity();
    }

    @Test
    void createEventRegister() throws Exception {
        int databaseSizeBeforeCreate = eventRegisterRepository.findAll().size();
        // Create the EventRegister
        restEventRegisterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventRegister)))
            .andExpect(status().isCreated());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeCreate + 1);
        EventRegister testEventRegister = eventRegisterList.get(eventRegisterList.size() - 1);
    }

    @Test
    void createEventRegisterWithExistingId() throws Exception {
        // Create the EventRegister with an existing ID
        eventRegister.setId("existing_id");

        int databaseSizeBeforeCreate = eventRegisterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventRegisterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventRegister)))
            .andExpect(status().isBadRequest());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEventRegisters() throws Exception {
        // Initialize the database
        eventRegisterRepository.save(eventRegister);

        // Get all the eventRegisterList
        restEventRegisterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventRegister.getId())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventRegistersWithEagerRelationshipsIsEnabled() throws Exception {
        when(eventRegisterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventRegisterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(eventRegisterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventRegistersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(eventRegisterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventRegisterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(eventRegisterRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getEventRegister() throws Exception {
        // Initialize the database
        eventRegisterRepository.save(eventRegister);

        // Get the eventRegister
        restEventRegisterMockMvc
            .perform(get(ENTITY_API_URL_ID, eventRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventRegister.getId()));
    }

    @Test
    void getNonExistingEventRegister() throws Exception {
        // Get the eventRegister
        restEventRegisterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEventRegister() throws Exception {
        // Initialize the database
        eventRegisterRepository.save(eventRegister);

        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();

        // Update the eventRegister
        EventRegister updatedEventRegister = eventRegisterRepository.findById(eventRegister.getId()).get();

        restEventRegisterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEventRegister.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEventRegister))
            )
            .andExpect(status().isOk());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
        EventRegister testEventRegister = eventRegisterList.get(eventRegisterList.size() - 1);
    }

    @Test
    void putNonExistingEventRegister() throws Exception {
        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();
        eventRegister.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventRegisterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventRegister.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventRegister))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEventRegister() throws Exception {
        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();
        eventRegister.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventRegisterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventRegister))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEventRegister() throws Exception {
        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();
        eventRegister.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventRegisterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventRegister)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEventRegisterWithPatch() throws Exception {
        // Initialize the database
        eventRegisterRepository.save(eventRegister);

        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();

        // Update the eventRegister using partial update
        EventRegister partialUpdatedEventRegister = new EventRegister();
        partialUpdatedEventRegister.setId(eventRegister.getId());

        restEventRegisterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventRegister.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventRegister))
            )
            .andExpect(status().isOk());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
        EventRegister testEventRegister = eventRegisterList.get(eventRegisterList.size() - 1);
    }

    @Test
    void fullUpdateEventRegisterWithPatch() throws Exception {
        // Initialize the database
        eventRegisterRepository.save(eventRegister);

        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();

        // Update the eventRegister using partial update
        EventRegister partialUpdatedEventRegister = new EventRegister();
        partialUpdatedEventRegister.setId(eventRegister.getId());

        restEventRegisterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventRegister.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventRegister))
            )
            .andExpect(status().isOk());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
        EventRegister testEventRegister = eventRegisterList.get(eventRegisterList.size() - 1);
    }

    @Test
    void patchNonExistingEventRegister() throws Exception {
        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();
        eventRegister.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventRegisterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventRegister.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventRegister))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEventRegister() throws Exception {
        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();
        eventRegister.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventRegisterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventRegister))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEventRegister() throws Exception {
        int databaseSizeBeforeUpdate = eventRegisterRepository.findAll().size();
        eventRegister.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventRegisterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventRegister))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventRegister in the database
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEventRegister() throws Exception {
        // Initialize the database
        eventRegisterRepository.save(eventRegister);

        int databaseSizeBeforeDelete = eventRegisterRepository.findAll().size();

        // Delete the eventRegister
        restEventRegisterMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventRegister.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventRegister> eventRegisterList = eventRegisterRepository.findAll();
        assertThat(eventRegisterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
