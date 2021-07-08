package com.hcl.mypack.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hcl.mypack.IntegrationTest;
import com.hcl.mypack.domain.ActivityMaster;
import com.hcl.mypack.repository.ActivityMasterRepository;
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
 * Integration tests for the {@link ActivityMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityMasterResourceIT {

    private static final Integer DEFAULT_ACTIVITY_MASTER_ID = 1;
    private static final Integer UPDATED_ACTIVITY_MASTER_ID = 2;

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_CHANGE_REQUEST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_REQUEST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_ACTIVITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ACTIVITY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/activity-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActivityMasterRepository activityMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityMasterMockMvc;

    private ActivityMaster activityMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityMaster createEntity(EntityManager em) {
        ActivityMaster activityMaster = new ActivityMaster()
            .activityMasterId(DEFAULT_ACTIVITY_MASTER_ID)
            .activity(DEFAULT_ACTIVITY)
            .changeRequestCode(DEFAULT_CHANGE_REQUEST_CODE)
            .projectActivityCode(DEFAULT_PROJECT_ACTIVITY_CODE);
        return activityMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityMaster createUpdatedEntity(EntityManager em) {
        ActivityMaster activityMaster = new ActivityMaster()
            .activityMasterId(UPDATED_ACTIVITY_MASTER_ID)
            .activity(UPDATED_ACTIVITY)
            .changeRequestCode(UPDATED_CHANGE_REQUEST_CODE)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);
        return activityMaster;
    }

    @BeforeEach
    public void initTest() {
        activityMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createActivityMaster() throws Exception {
        int databaseSizeBeforeCreate = activityMasterRepository.findAll().size();
        // Create the ActivityMaster
        restActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isCreated());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityMaster testActivityMaster = activityMasterList.get(activityMasterList.size() - 1);
        assertThat(testActivityMaster.getActivityMasterId()).isEqualTo(DEFAULT_ACTIVITY_MASTER_ID);
        assertThat(testActivityMaster.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testActivityMaster.getChangeRequestCode()).isEqualTo(DEFAULT_CHANGE_REQUEST_CODE);
        assertThat(testActivityMaster.getProjectActivityCode()).isEqualTo(DEFAULT_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void createActivityMasterWithExistingId() throws Exception {
        // Create the ActivityMaster with an existing ID
        activityMaster.setId(1L);

        int databaseSizeBeforeCreate = activityMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityMasterRepository.findAll().size();
        // set the field null
        activityMaster.setActivity(null);

        // Create the ActivityMaster, which fails.

        restActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChangeRequestCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityMasterRepository.findAll().size();
        // set the field null
        activityMaster.setChangeRequestCode(null);

        // Create the ActivityMaster, which fails.

        restActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProjectActivityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityMasterRepository.findAll().size();
        // set the field null
        activityMaster.setProjectActivityCode(null);

        // Create the ActivityMaster, which fails.

        restActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllActivityMasters() throws Exception {
        // Initialize the database
        activityMasterRepository.saveAndFlush(activityMaster);

        // Get all the activityMasterList
        restActivityMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityMasterId").value(hasItem(DEFAULT_ACTIVITY_MASTER_ID)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].changeRequestCode").value(hasItem(DEFAULT_CHANGE_REQUEST_CODE)))
            .andExpect(jsonPath("$.[*].projectActivityCode").value(hasItem(DEFAULT_PROJECT_ACTIVITY_CODE)));
    }

    @Test
    @Transactional
    void getActivityMaster() throws Exception {
        // Initialize the database
        activityMasterRepository.saveAndFlush(activityMaster);

        // Get the activityMaster
        restActivityMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, activityMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activityMaster.getId().intValue()))
            .andExpect(jsonPath("$.activityMasterId").value(DEFAULT_ACTIVITY_MASTER_ID))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.changeRequestCode").value(DEFAULT_CHANGE_REQUEST_CODE))
            .andExpect(jsonPath("$.projectActivityCode").value(DEFAULT_PROJECT_ACTIVITY_CODE));
    }

    @Test
    @Transactional
    void getNonExistingActivityMaster() throws Exception {
        // Get the activityMaster
        restActivityMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewActivityMaster() throws Exception {
        // Initialize the database
        activityMasterRepository.saveAndFlush(activityMaster);

        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();

        // Update the activityMaster
        ActivityMaster updatedActivityMaster = activityMasterRepository.findById(activityMaster.getId()).get();
        // Disconnect from session so that the updates on updatedActivityMaster are not directly saved in db
        em.detach(updatedActivityMaster);
        updatedActivityMaster
            .activityMasterId(UPDATED_ACTIVITY_MASTER_ID)
            .activity(UPDATED_ACTIVITY)
            .changeRequestCode(UPDATED_CHANGE_REQUEST_CODE)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);

        restActivityMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivityMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivityMaster))
            )
            .andExpect(status().isOk());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
        ActivityMaster testActivityMaster = activityMasterList.get(activityMasterList.size() - 1);
        assertThat(testActivityMaster.getActivityMasterId()).isEqualTo(UPDATED_ACTIVITY_MASTER_ID);
        assertThat(testActivityMaster.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testActivityMaster.getChangeRequestCode()).isEqualTo(UPDATED_CHANGE_REQUEST_CODE);
        assertThat(testActivityMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();
        activityMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();
        activityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();
        activityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(activityMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityMasterWithPatch() throws Exception {
        // Initialize the database
        activityMasterRepository.saveAndFlush(activityMaster);

        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();

        // Update the activityMaster using partial update
        ActivityMaster partialUpdatedActivityMaster = new ActivityMaster();
        partialUpdatedActivityMaster.setId(activityMaster.getId());

        partialUpdatedActivityMaster
            .activityMasterId(UPDATED_ACTIVITY_MASTER_ID)
            .activity(UPDATED_ACTIVITY)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);

        restActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityMaster))
            )
            .andExpect(status().isOk());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
        ActivityMaster testActivityMaster = activityMasterList.get(activityMasterList.size() - 1);
        assertThat(testActivityMaster.getActivityMasterId()).isEqualTo(UPDATED_ACTIVITY_MASTER_ID);
        assertThat(testActivityMaster.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testActivityMaster.getChangeRequestCode()).isEqualTo(DEFAULT_CHANGE_REQUEST_CODE);
        assertThat(testActivityMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateActivityMasterWithPatch() throws Exception {
        // Initialize the database
        activityMasterRepository.saveAndFlush(activityMaster);

        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();

        // Update the activityMaster using partial update
        ActivityMaster partialUpdatedActivityMaster = new ActivityMaster();
        partialUpdatedActivityMaster.setId(activityMaster.getId());

        partialUpdatedActivityMaster
            .activityMasterId(UPDATED_ACTIVITY_MASTER_ID)
            .activity(UPDATED_ACTIVITY)
            .changeRequestCode(UPDATED_CHANGE_REQUEST_CODE)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE);

        restActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivityMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivityMaster))
            )
            .andExpect(status().isOk());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
        ActivityMaster testActivityMaster = activityMasterList.get(activityMasterList.size() - 1);
        assertThat(testActivityMaster.getActivityMasterId()).isEqualTo(UPDATED_ACTIVITY_MASTER_ID);
        assertThat(testActivityMaster.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testActivityMaster.getChangeRequestCode()).isEqualTo(UPDATED_CHANGE_REQUEST_CODE);
        assertThat(testActivityMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();
        activityMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activityMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();
        activityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = activityMasterRepository.findAll().size();
        activityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(activityMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivityMaster in the database
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivityMaster() throws Exception {
        // Initialize the database
        activityMasterRepository.saveAndFlush(activityMaster);

        int databaseSizeBeforeDelete = activityMasterRepository.findAll().size();

        // Delete the activityMaster
        restActivityMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, activityMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityMaster> activityMasterList = activityMasterRepository.findAll();
        assertThat(activityMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
