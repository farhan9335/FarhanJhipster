package com.hcl.mypack.web.rest;

import com.hcl.mypack.domain.ActivityMaster;
import com.hcl.mypack.repository.ActivityMasterRepository;
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
 * REST controller for managing {@link com.hcl.mypack.domain.ActivityMaster}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActivityMasterResource {

    private final Logger log = LoggerFactory.getLogger(ActivityMasterResource.class);

    private static final String ENTITY_NAME = "activityMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityMasterRepository activityMasterRepository;

    public ActivityMasterResource(ActivityMasterRepository activityMasterRepository) {
        this.activityMasterRepository = activityMasterRepository;
    }

    /**
     * {@code POST  /activity-masters} : Create a new activityMaster.
     *
     * @param activityMaster the activityMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityMaster, or with status {@code 400 (Bad Request)} if the activityMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity-masters")
    public ResponseEntity<ActivityMaster> createActivityMaster(@Valid @RequestBody ActivityMaster activityMaster)
        throws URISyntaxException {
        log.debug("REST request to save ActivityMaster : {}", activityMaster);
        if (activityMaster.getId() != null) {
            throw new BadRequestAlertException("A new activityMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityMaster result = activityMasterRepository.save(activityMaster);
        return ResponseEntity
            .created(new URI("/api/activity-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity-masters/:id} : Updates an existing activityMaster.
     *
     * @param id the id of the activityMaster to save.
     * @param activityMaster the activityMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityMaster,
     * or with status {@code 400 (Bad Request)} if the activityMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity-masters/{id}")
    public ResponseEntity<ActivityMaster> updateActivityMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActivityMaster activityMaster
    ) throws URISyntaxException {
        log.debug("REST request to update ActivityMaster : {}, {}", id, activityMaster);
        if (activityMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActivityMaster result = activityMasterRepository.save(activityMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /activity-masters/:id} : Partial updates given fields of an existing activityMaster, field will ignore if it is null
     *
     * @param id the id of the activityMaster to save.
     * @param activityMaster the activityMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityMaster,
     * or with status {@code 400 (Bad Request)} if the activityMaster is not valid,
     * or with status {@code 404 (Not Found)} if the activityMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the activityMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/activity-masters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ActivityMaster> partialUpdateActivityMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActivityMaster activityMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActivityMaster partially : {}, {}", id, activityMaster);
        if (activityMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivityMaster> result = activityMasterRepository
            .findById(activityMaster.getId())
            .map(
                existingActivityMaster -> {
                    if (activityMaster.getActivityMasterId() != null) {
                        existingActivityMaster.setActivityMasterId(activityMaster.getActivityMasterId());
                    }
                    if (activityMaster.getActivity() != null) {
                        existingActivityMaster.setActivity(activityMaster.getActivity());
                    }
                    if (activityMaster.getChangeRequestCode() != null) {
                        existingActivityMaster.setChangeRequestCode(activityMaster.getChangeRequestCode());
                    }
                    if (activityMaster.getProjectActivityCode() != null) {
                        existingActivityMaster.setProjectActivityCode(activityMaster.getProjectActivityCode());
                    }

                    return existingActivityMaster;
                }
            )
            .map(activityMasterRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /activity-masters} : get all the activityMasters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activityMasters in body.
     */
    @GetMapping("/activity-masters")
    public List<ActivityMaster> getAllActivityMasters() {
        log.debug("REST request to get all ActivityMasters");
        return activityMasterRepository.findAll();
    }

    /**
     * {@code GET  /activity-masters/:id} : get the "id" activityMaster.
     *
     * @param id the id of the activityMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/activity-masters/{id}")
    public ResponseEntity<ActivityMaster> getActivityMaster(@PathVariable Long id) {
        log.debug("REST request to get ActivityMaster : {}", id);
        Optional<ActivityMaster> activityMaster = activityMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(activityMaster);
    }

    /**
     * {@code DELETE  /activity-masters/:id} : delete the "id" activityMaster.
     *
     * @param id the id of the activityMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/activity-masters/{id}")
    public ResponseEntity<Void> deleteActivityMaster(@PathVariable Long id) {
        log.debug("REST request to delete ActivityMaster : {}", id);
        activityMasterRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
