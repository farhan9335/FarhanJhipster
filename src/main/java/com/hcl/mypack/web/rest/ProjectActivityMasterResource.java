package com.hcl.mypack.web.rest;

import com.hcl.mypack.domain.ProjectActivityMaster;
import com.hcl.mypack.repository.ProjectActivityMasterRepository;
import com.hcl.mypack.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hcl.mypack.domain.ProjectActivityMaster}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectActivityMasterResource {

    private final Logger log = LoggerFactory.getLogger(ProjectActivityMasterResource.class);

    private static final String ENTITY_NAME = "projectActivityMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectActivityMasterRepository projectActivityMasterRepository;

    public ProjectActivityMasterResource(ProjectActivityMasterRepository projectActivityMasterRepository) {
        this.projectActivityMasterRepository = projectActivityMasterRepository;
    }

    /**
     * {@code POST  /project-activity-masters} : Create a new projectActivityMaster.
     *
     * @param projectActivityMaster the projectActivityMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectActivityMaster, or with status {@code 400 (Bad Request)} if the projectActivityMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-activity-masters")
    public ResponseEntity<ProjectActivityMaster> createProjectActivityMaster(
        @Valid @RequestBody ProjectActivityMaster projectActivityMaster
    ) throws URISyntaxException {
        log.debug("REST request to save ProjectActivityMaster : {}", projectActivityMaster);
        if (projectActivityMaster.getId() != null) {
            throw new BadRequestAlertException("A new projectActivityMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectActivityMaster result = projectActivityMasterRepository.save(projectActivityMaster);
        return ResponseEntity
            .created(new URI("/api/project-activity-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-activity-masters/:id} : Updates an existing projectActivityMaster.
     *
     * @param id the id of the projectActivityMaster to save.
     * @param projectActivityMaster the projectActivityMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectActivityMaster,
     * or with status {@code 400 (Bad Request)} if the projectActivityMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectActivityMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-activity-masters/{id}")
    public ResponseEntity<ProjectActivityMaster> updateProjectActivityMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectActivityMaster projectActivityMaster
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectActivityMaster : {}, {}", id, projectActivityMaster);
        if (projectActivityMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectActivityMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectActivityMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectActivityMaster result = projectActivityMasterRepository.save(projectActivityMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectActivityMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-activity-masters/:id} : Partial updates given fields of an existing projectActivityMaster, field will ignore if it is null
     *
     * @param id the id of the projectActivityMaster to save.
     * @param projectActivityMaster the projectActivityMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectActivityMaster,
     * or with status {@code 400 (Bad Request)} if the projectActivityMaster is not valid,
     * or with status {@code 404 (Not Found)} if the projectActivityMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectActivityMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-activity-masters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectActivityMaster> partialUpdateProjectActivityMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectActivityMaster projectActivityMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectActivityMaster partially : {}, {}", id, projectActivityMaster);
        if (projectActivityMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectActivityMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectActivityMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectActivityMaster> result = projectActivityMasterRepository
            .findById(projectActivityMaster.getId())
            .map(
                existingProjectActivityMaster -> {
                    if (projectActivityMaster.getProjectActivityId() != null) {
                        existingProjectActivityMaster.setProjectActivityId(projectActivityMaster.getProjectActivityId());
                    }
                    if (projectActivityMaster.getProjectActivityCode() != null) {
                        existingProjectActivityMaster.setProjectActivityCode(projectActivityMaster.getProjectActivityCode());
                    }
                    if (projectActivityMaster.getDescription() != null) {
                        existingProjectActivityMaster.setDescription(projectActivityMaster.getDescription());
                    }

                    return existingProjectActivityMaster;
                }
            )
            .map(projectActivityMasterRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectActivityMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /project-activity-masters} : get all the projectActivityMasters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectActivityMasters in body.
     */
    @GetMapping("/project-activity-masters")
    public List<ProjectActivityMaster> getAllProjectActivityMasters() {
        log.debug("REST request to get all ProjectActivityMasters");
        return projectActivityMasterRepository.findAll();
    }

    /**
     * {@code GET  /project-activity-masters/:id} : get the "id" projectActivityMaster.
     *
     * @param id the id of the projectActivityMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectActivityMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-activity-masters/{id}")
    public ResponseEntity<ProjectActivityMaster> getProjectActivityMaster(@PathVariable Long id) {
        log.debug("REST request to get ProjectActivityMaster : {}", id);
        Optional<ProjectActivityMaster> projectActivityMaster = projectActivityMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectActivityMaster);
    }

    /**
     * {@code DELETE  /project-activity-masters/:id} : delete the "id" projectActivityMaster.
     *
     * @param id the id of the projectActivityMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-activity-masters/{id}")
    public ResponseEntity<Void> deleteProjectActivityMaster(@PathVariable Long id) {
        log.debug("REST request to delete ProjectActivityMaster : {}", id);
        projectActivityMasterRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
