package com.hcl.mypack.web.rest;

import com.hcl.mypack.domain.ChangeRequestMaster;
import com.hcl.mypack.repository.ChangeRequestMasterRepository;
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
 * REST controller for managing {@link com.hcl.mypack.domain.ChangeRequestMaster}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChangeRequestMasterResource {

    private final Logger log = LoggerFactory.getLogger(ChangeRequestMasterResource.class);

    private static final String ENTITY_NAME = "changeRequestMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChangeRequestMasterRepository changeRequestMasterRepository;

    public ChangeRequestMasterResource(ChangeRequestMasterRepository changeRequestMasterRepository) {
        this.changeRequestMasterRepository = changeRequestMasterRepository;
    }

    /**
     * {@code POST  /change-request-masters} : Create a new changeRequestMaster.
     *
     * @param changeRequestMaster the changeRequestMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new changeRequestMaster, or with status {@code 400 (Bad Request)} if the changeRequestMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/change-request-masters")
    public ResponseEntity<ChangeRequestMaster> createChangeRequestMaster(@Valid @RequestBody ChangeRequestMaster changeRequestMaster)
        throws URISyntaxException {
        log.debug("REST request to save ChangeRequestMaster : {}", changeRequestMaster);
        if (changeRequestMaster.getId() != null) {
            throw new BadRequestAlertException("A new changeRequestMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChangeRequestMaster result = changeRequestMasterRepository.save(changeRequestMaster);
        return ResponseEntity
            .created(new URI("/api/change-request-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /change-request-masters/:id} : Updates an existing changeRequestMaster.
     *
     * @param id the id of the changeRequestMaster to save.
     * @param changeRequestMaster the changeRequestMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeRequestMaster,
     * or with status {@code 400 (Bad Request)} if the changeRequestMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the changeRequestMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/change-request-masters/{id}")
    public ResponseEntity<ChangeRequestMaster> updateChangeRequestMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChangeRequestMaster changeRequestMaster
    ) throws URISyntaxException {
        log.debug("REST request to update ChangeRequestMaster : {}, {}", id, changeRequestMaster);
        if (changeRequestMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeRequestMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeRequestMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChangeRequestMaster result = changeRequestMasterRepository.save(changeRequestMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, changeRequestMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /change-request-masters/:id} : Partial updates given fields of an existing changeRequestMaster, field will ignore if it is null
     *
     * @param id the id of the changeRequestMaster to save.
     * @param changeRequestMaster the changeRequestMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeRequestMaster,
     * or with status {@code 400 (Bad Request)} if the changeRequestMaster is not valid,
     * or with status {@code 404 (Not Found)} if the changeRequestMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the changeRequestMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/change-request-masters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ChangeRequestMaster> partialUpdateChangeRequestMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChangeRequestMaster changeRequestMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChangeRequestMaster partially : {}, {}", id, changeRequestMaster);
        if (changeRequestMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeRequestMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeRequestMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChangeRequestMaster> result = changeRequestMasterRepository
            .findById(changeRequestMaster.getId())
            .map(
                existingChangeRequestMaster -> {
                    if (changeRequestMaster.getChangeRequestId() != null) {
                        existingChangeRequestMaster.setChangeRequestId(changeRequestMaster.getChangeRequestId());
                    }
                    if (changeRequestMaster.getChangeRequestCode() != null) {
                        existingChangeRequestMaster.setChangeRequestCode(changeRequestMaster.getChangeRequestCode());
                    }
                    if (changeRequestMaster.getProjectActivityCode() != null) {
                        existingChangeRequestMaster.setProjectActivityCode(changeRequestMaster.getProjectActivityCode());
                    }

                    return existingChangeRequestMaster;
                }
            )
            .map(changeRequestMasterRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, changeRequestMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /change-request-masters} : get all the changeRequestMasters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of changeRequestMasters in body.
     */
    @GetMapping("/change-request-masters")
    public List<ChangeRequestMaster> getAllChangeRequestMasters() {
        log.debug("REST request to get all ChangeRequestMasters");
        return changeRequestMasterRepository.findAll();
    }

    /**
     * {@code GET  /change-request-masters/:id} : get the "id" changeRequestMaster.
     *
     * @param id the id of the changeRequestMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the changeRequestMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/change-request-masters/{id}")
    public ResponseEntity<ChangeRequestMaster> getChangeRequestMaster(@PathVariable Long id) {
        log.debug("REST request to get ChangeRequestMaster : {}", id);
        Optional<ChangeRequestMaster> changeRequestMaster = changeRequestMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(changeRequestMaster);
    }

    /**
     * {@code DELETE  /change-request-masters/:id} : delete the "id" changeRequestMaster.
     *
     * @param id the id of the changeRequestMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/change-request-masters/{id}")
    public ResponseEntity<Void> deleteChangeRequestMaster(@PathVariable Long id) {
        log.debug("REST request to delete ChangeRequestMaster : {}", id);
        changeRequestMasterRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
