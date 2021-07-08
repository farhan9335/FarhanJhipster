package com.hcl.mypack.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hcl.mypack.IntegrationTest;
import com.hcl.mypack.domain.ChangeRequestMaster;
import com.hcl.mypack.repository.ChangeRequestMasterRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChangeRequestMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChangeRequestMasterResourceIT {

    private static final Integer DEFAULT_CHANGE_REQUEST_ID = 1;
    private static final Integer UPDATED_CHANGE_REQUEST_ID = 2;

    private static final String DEFAULT_CHANGE_REQUEST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_REQUEST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_ACTIVITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ACTIVITY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/change-request-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChangeRequestMasterRepository changeRequestMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChangeRequestMasterMockMvc;

    private ChangeRequestMaster changeRequestMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChangeRequestMaster createEntity(EntityManager em) {
        ChangeRequestMaster changeRequestMaster = new ChangeRequestMaster()
            .changeRequestId(DEFAULT_CHANGE_REQUEST_ID)
            .changeRequestCode(DEFAULT_CHANGE_REQUEST_CODE)
            .projectActivityCode(DEFAULT_PROJECT_ACTIVITY_CODE);
        return changeRequestMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChangeRequestMaster createUpdatedEntity(EntityManager em) {
        ChangeRequestMaster changeRequestMaster = new ChangeRequestMaster()
            .changeRequestId(UPDATED_CHANGE_REQUEST_ID)
            .changeRequestCode(UPDATED_CHANGE_REQUEST_CODE)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);
        return changeRequestMaster;
    }

    @BeforeEach
    public void initTest() {
        changeRequestMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createChangeRequestMaster() throws Exception {
        int databaseSizeBeforeCreate = changeRequestMasterRepository.findAll().size();
        // Create the ChangeRequestMaster
        restChangeRequestMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isCreated());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeCreate + 1);
        ChangeRequestMaster testChangeRequestMaster = changeRequestMasterList.get(changeRequestMasterList.size() - 1);
        assertThat(testChangeRequestMaster.getChangeRequestId()).isEqualTo(DEFAULT_CHANGE_REQUEST_ID);
        assertThat(testChangeRequestMaster.getChangeRequestCode()).isEqualTo(DEFAULT_CHANGE_REQUEST_CODE);
        assertThat(testChangeRequestMaster.getProjectActivityCode()).isEqualTo(DEFAULT_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void createChangeRequestMasterWithExistingId() throws Exception {
        // Create the ChangeRequestMaster with an existing ID
        changeRequestMaster.setId(1L);

        int databaseSizeBeforeCreate = changeRequestMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChangeRequestMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkChangeRequestCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = changeRequestMasterRepository.findAll().size();
        // set the field null
        changeRequestMaster.setChangeRequestCode(null);

        // Create the ChangeRequestMaster, which fails.

        restChangeRequestMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isBadRequest());

        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProjectActivityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = changeRequestMasterRepository.findAll().size();
        // set the field null
        changeRequestMaster.setProjectActivityCode(null);

        // Create the ChangeRequestMaster, which fails.

        restChangeRequestMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isBadRequest());

        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChangeRequestMasters() throws Exception {
        // Initialize the database
        changeRequestMasterRepository.saveAndFlush(changeRequestMaster);

        // Get all the changeRequestMasterList
        restChangeRequestMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(changeRequestMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].changeRequestId").value(hasItem(DEFAULT_CHANGE_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].changeRequestCode").value(hasItem(DEFAULT_CHANGE_REQUEST_CODE)))
            .andExpect(jsonPath("$.[*].projectActivityCode").value(hasItem(DEFAULT_PROJECT_ACTIVITY_CODE)));
    }

    @Test
    @Transactional
    void getChangeRequestMaster() throws Exception {
        // Initialize the database
        changeRequestMasterRepository.saveAndFlush(changeRequestMaster);

        // Get the changeRequestMaster
        restChangeRequestMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, changeRequestMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(changeRequestMaster.getId().intValue()))
            .andExpect(jsonPath("$.changeRequestId").value(DEFAULT_CHANGE_REQUEST_ID))
            .andExpect(jsonPath("$.changeRequestCode").value(DEFAULT_CHANGE_REQUEST_CODE))
            .andExpect(jsonPath("$.projectActivityCode").value(DEFAULT_PROJECT_ACTIVITY_CODE));
    }

    @Test
    @Transactional
    void getNonExistingChangeRequestMaster() throws Exception {
        // Get the changeRequestMaster
        restChangeRequestMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChangeRequestMaster() throws Exception {
        // Initialize the database
        changeRequestMasterRepository.saveAndFlush(changeRequestMaster);

        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();

        // Update the changeRequestMaster
        ChangeRequestMaster updatedChangeRequestMaster = changeRequestMasterRepository.findById(changeRequestMaster.getId()).get();
        // Disconnect from session so that the updates on updatedChangeRequestMaster are not directly saved in db
        em.detach(updatedChangeRequestMaster);
        updatedChangeRequestMaster
            .changeRequestId(UPDATED_CHANGE_REQUEST_ID)
            .changeRequestCode(UPDATED_CHANGE_REQUEST_CODE)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);

        restChangeRequestMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChangeRequestMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChangeRequestMaster))
            )
            .andExpect(status().isOk());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
        ChangeRequestMaster testChangeRequestMaster = changeRequestMasterList.get(changeRequestMasterList.size() - 1);
        assertThat(testChangeRequestMaster.getChangeRequestId()).isEqualTo(UPDATED_CHANGE_REQUEST_ID);
        assertThat(testChangeRequestMaster.getChangeRequestCode()).isEqualTo(UPDATED_CHANGE_REQUEST_CODE);
        assertThat(testChangeRequestMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingChangeRequestMaster() throws Exception {
        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();
        changeRequestMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangeRequestMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, changeRequestMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChangeRequestMaster() throws Exception {
        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();
        changeRequestMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeRequestMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChangeRequestMaster() throws Exception {
        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();
        changeRequestMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeRequestMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChangeRequestMasterWithPatch() throws Exception {
        // Initialize the database
        changeRequestMasterRepository.saveAndFlush(changeRequestMaster);

        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();

        // Update the changeRequestMaster using partial update
        ChangeRequestMaster partialUpdatedChangeRequestMaster = new ChangeRequestMaster();
        partialUpdatedChangeRequestMaster.setId(changeRequestMaster.getId());

        partialUpdatedChangeRequestMaster.projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);

        restChangeRequestMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangeRequestMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChangeRequestMaster))
            )
            .andExpect(status().isOk());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
        ChangeRequestMaster testChangeRequestMaster = changeRequestMasterList.get(changeRequestMasterList.size() - 1);
        assertThat(testChangeRequestMaster.getChangeRequestId()).isEqualTo(DEFAULT_CHANGE_REQUEST_ID);
        assertThat(testChangeRequestMaster.getChangeRequestCode()).isEqualTo(DEFAULT_CHANGE_REQUEST_CODE);
        assertThat(testChangeRequestMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateChangeRequestMasterWithPatch() throws Exception {
        // Initialize the database
        changeRequestMasterRepository.saveAndFlush(changeRequestMaster);

        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();

        // Update the changeRequestMaster using partial update
        ChangeRequestMaster partialUpdatedChangeRequestMaster = new ChangeRequestMaster();
        partialUpdatedChangeRequestMaster.setId(changeRequestMaster.getId());

        partialUpdatedChangeRequestMaster
            .changeRequestId(UPDATED_CHANGE_REQUEST_ID)
            .changeRequestCode(UPDATED_CHANGE_REQUEST_CODE)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);

        restChangeRequestMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangeRequestMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChangeRequestMaster))
            )
            .andExpect(status().isOk());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
        ChangeRequestMaster testChangeRequestMaster = changeRequestMasterList.get(changeRequestMasterList.size() - 1);
        assertThat(testChangeRequestMaster.getChangeRequestId()).isEqualTo(UPDATED_CHANGE_REQUEST_ID);
        assertThat(testChangeRequestMaster.getChangeRequestCode()).isEqualTo(UPDATED_CHANGE_REQUEST_CODE);
        assertThat(testChangeRequestMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingChangeRequestMaster() throws Exception {
        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();
        changeRequestMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangeRequestMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, changeRequestMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChangeRequestMaster() throws Exception {
        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();
        changeRequestMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeRequestMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChangeRequestMaster() throws Exception {
        int databaseSizeBeforeUpdate = changeRequestMasterRepository.findAll().size();
        changeRequestMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangeRequestMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(changeRequestMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChangeRequestMaster in the database
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChangeRequestMaster() throws Exception {
        // Initialize the database
        changeRequestMasterRepository.saveAndFlush(changeRequestMaster);

        int databaseSizeBeforeDelete = changeRequestMasterRepository.findAll().size();

        // Delete the changeRequestMaster
        restChangeRequestMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, changeRequestMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChangeRequestMaster> changeRequestMasterList = changeRequestMasterRepository.findAll();
        assertThat(changeRequestMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
