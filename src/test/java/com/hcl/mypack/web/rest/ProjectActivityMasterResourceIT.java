package com.hcl.mypack.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hcl.mypack.IntegrationTest;
import com.hcl.mypack.domain.ProjectActivityMaster;
import com.hcl.mypack.repository.ProjectActivityMasterRepository;
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
 * Integration tests for the {@link ProjectActivityMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectActivityMasterResourceIT {

    private static final Integer DEFAULT_PROJECT_ACTIVITY_ID = 1;
    private static final Integer UPDATED_PROJECT_ACTIVITY_ID = 2;

    private static final String DEFAULT_PROJECT_ACTIVITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_ACTIVITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-activity-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectActivityMasterRepository projectActivityMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectActivityMasterMockMvc;

    private ProjectActivityMaster projectActivityMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectActivityMaster createEntity(EntityManager em) {
        ProjectActivityMaster projectActivityMaster = new ProjectActivityMaster()
            .projectActivityId(DEFAULT_PROJECT_ACTIVITY_ID)
            .projectActivityCode(DEFAULT_PROJECT_ACTIVITY_CODE)
            .description(DEFAULT_DESCRIPTION);
        return projectActivityMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectActivityMaster createUpdatedEntity(EntityManager em) {
        ProjectActivityMaster projectActivityMaster = new ProjectActivityMaster()
            .projectActivityId(UPDATED_PROJECT_ACTIVITY_ID)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE)
            .description(UPDATED_DESCRIPTION);
        return projectActivityMaster;
    }

    @BeforeEach
    public void initTest() {
        projectActivityMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectActivityMaster() throws Exception {
        int databaseSizeBeforeCreate = projectActivityMasterRepository.findAll().size();
        // Create the ProjectActivityMaster
        restProjectActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectActivityMaster testProjectActivityMaster = projectActivityMasterList.get(projectActivityMasterList.size() - 1);
        assertThat(testProjectActivityMaster.getProjectActivityId()).isEqualTo(DEFAULT_PROJECT_ACTIVITY_ID);
        assertThat(testProjectActivityMaster.getProjectActivityCode()).isEqualTo(DEFAULT_PROJECT_ACTIVITY_CODE);
        assertThat(testProjectActivityMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createProjectActivityMasterWithExistingId() throws Exception {
        // Create the ProjectActivityMaster with an existing ID
        projectActivityMaster.setId(1L);

        int databaseSizeBeforeCreate = projectActivityMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkProjectActivityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectActivityMasterRepository.findAll().size();
        // set the field null
        projectActivityMaster.setProjectActivityCode(null);

        // Create the ProjectActivityMaster, which fails.

        restProjectActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isBadRequest());

        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectActivityMasterRepository.findAll().size();
        // set the field null
        projectActivityMaster.setDescription(null);

        // Create the ProjectActivityMaster, which fails.

        restProjectActivityMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isBadRequest());

        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectActivityMasters() throws Exception {
        // Initialize the database
        projectActivityMasterRepository.saveAndFlush(projectActivityMaster);

        // Get all the projectActivityMasterList
        restProjectActivityMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectActivityMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectActivityId").value(hasItem(DEFAULT_PROJECT_ACTIVITY_ID)))
            .andExpect(jsonPath("$.[*].projectActivityCode").value(hasItem(DEFAULT_PROJECT_ACTIVITY_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getProjectActivityMaster() throws Exception {
        // Initialize the database
        projectActivityMasterRepository.saveAndFlush(projectActivityMaster);

        // Get the projectActivityMaster
        restProjectActivityMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, projectActivityMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectActivityMaster.getId().intValue()))
            .andExpect(jsonPath("$.projectActivityId").value(DEFAULT_PROJECT_ACTIVITY_ID))
            .andExpect(jsonPath("$.projectActivityCode").value(DEFAULT_PROJECT_ACTIVITY_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingProjectActivityMaster() throws Exception {
        // Get the projectActivityMaster
        restProjectActivityMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectActivityMaster() throws Exception {
        // Initialize the database
        projectActivityMasterRepository.saveAndFlush(projectActivityMaster);

        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();

        // Update the projectActivityMaster
        ProjectActivityMaster updatedProjectActivityMaster = projectActivityMasterRepository.findById(projectActivityMaster.getId()).get();
        // Disconnect from session so that the updates on updatedProjectActivityMaster are not directly saved in db
        em.detach(updatedProjectActivityMaster);
        updatedProjectActivityMaster
            .projectActivityId(UPDATED_PROJECT_ACTIVITY_ID)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE)
            .description(UPDATED_DESCRIPTION);

        restProjectActivityMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectActivityMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectActivityMaster))
            )
            .andExpect(status().isOk());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
        ProjectActivityMaster testProjectActivityMaster = projectActivityMasterList.get(projectActivityMasterList.size() - 1);
        assertThat(testProjectActivityMaster.getProjectActivityId()).isEqualTo(UPDATED_PROJECT_ACTIVITY_ID);
        assertThat(testProjectActivityMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
        assertThat(testProjectActivityMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingProjectActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();
        projectActivityMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectActivityMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectActivityMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();
        projectActivityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectActivityMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();
        projectActivityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectActivityMasterMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectActivityMasterWithPatch() throws Exception {
        // Initialize the database
        projectActivityMasterRepository.saveAndFlush(projectActivityMaster);

        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();

        // Update the projectActivityMaster using partial update
        ProjectActivityMaster partialUpdatedProjectActivityMaster = new ProjectActivityMaster();
        partialUpdatedProjectActivityMaster.setId(projectActivityMaster.getId());

        partialUpdatedProjectActivityMaster.projectActivityId(UPDATED_PROJECT_ACTIVITY_ID).description(UPDATED_DESCRIPTION);

        restProjectActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectActivityMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectActivityMaster))
            )
            .andExpect(status().isOk());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
        ProjectActivityMaster testProjectActivityMaster = projectActivityMasterList.get(projectActivityMasterList.size() - 1);
        assertThat(testProjectActivityMaster.getProjectActivityId()).isEqualTo(UPDATED_PROJECT_ACTIVITY_ID);
        assertThat(testProjectActivityMaster.getProjectActivityCode()).isEqualTo(DEFAULT_PROJECT_ACTIVITY_CODE);
        assertThat(testProjectActivityMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateProjectActivityMasterWithPatch() throws Exception {
        // Initialize the database
        projectActivityMasterRepository.saveAndFlush(projectActivityMaster);

        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();

        // Update the projectActivityMaster using partial update
        ProjectActivityMaster partialUpdatedProjectActivityMaster = new ProjectActivityMaster();
        partialUpdatedProjectActivityMaster.setId(projectActivityMaster.getId());

        partialUpdatedProjectActivityMaster
            .projectActivityId(UPDATED_PROJECT_ACTIVITY_ID)
            .projectActivityCode(UPDATED_PROJECT_ACTIVITY_CODE)
            .description(UPDATED_DESCRIPTION);

        restProjectActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectActivityMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectActivityMaster))
            )
            .andExpect(status().isOk());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
        ProjectActivityMaster testProjectActivityMaster = projectActivityMasterList.get(projectActivityMasterList.size() - 1);
        assertThat(testProjectActivityMaster.getProjectActivityId()).isEqualTo(UPDATED_PROJECT_ACTIVITY_ID);
        assertThat(testProjectActivityMaster.getProjectActivityCode()).isEqualTo(UPDATED_PROJECT_ACTIVITY_CODE);
        assertThat(testProjectActivityMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingProjectActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();
        projectActivityMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectActivityMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();
        projectActivityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectActivityMaster() throws Exception {
        int databaseSizeBeforeUpdate = projectActivityMasterRepository.findAll().size();
        projectActivityMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectActivityMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectActivityMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectActivityMaster in the database
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectActivityMaster() throws Exception {
        // Initialize the database
        projectActivityMasterRepository.saveAndFlush(projectActivityMaster);

        int databaseSizeBeforeDelete = projectActivityMasterRepository.findAll().size();

        // Delete the projectActivityMaster
        restProjectActivityMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectActivityMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectActivityMaster> projectActivityMasterList = projectActivityMasterRepository.findAll();
        assertThat(projectActivityMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
