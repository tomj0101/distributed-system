package com.github.tomj0101.ebankv1.ordersystem.web.rest;

import com.github.tomj0101.ebankv1.ordersystem.domain.StatusV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.StatusRepository;
import com.github.tomj0101.ebankv1.ordersystem.service.StatusService;
import com.github.tomj0101.ebankv1.ordersystem.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.tomj0101.ebankv1.ordersystem.domain.StatusV1}.
 */
@RestController
@RequestMapping("/api")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    private static final String ENTITY_NAME = "status";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusService statusService;

    private final StatusRepository statusRepository;

    public StatusResource(StatusService statusService, StatusRepository statusRepository) {
        this.statusService = statusService;
        this.statusRepository = statusRepository;
    }

    /**
     * {@code POST  /statuses} : Create a new status.
     *
     * @param statusV1 the statusV1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusV1, or with status {@code 400 (Bad Request)} if the status has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statuses")
    public ResponseEntity<StatusV1> createStatus(@Valid @RequestBody StatusV1 statusV1) throws URISyntaxException {
        log.debug("REST request to save Status : {}", statusV1);
        if (statusV1.getId() != null) {
            throw new BadRequestAlertException("A new status cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusV1 result = statusService.save(statusV1);
        return ResponseEntity
            .created(new URI("/api/statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statuses/:id} : Updates an existing status.
     *
     * @param id the id of the statusV1 to save.
     * @param statusV1 the statusV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusV1,
     * or with status {@code 400 (Bad Request)} if the statusV1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statuses/{id}")
    public ResponseEntity<StatusV1> updateStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StatusV1 statusV1
    ) throws URISyntaxException {
        log.debug("REST request to update Status : {}, {}", id, statusV1);
        if (statusV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatusV1 result = statusService.save(statusV1);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusV1.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /statuses/:id} : Partial updates given fields of an existing status, field will ignore if it is null
     *
     * @param id the id of the statusV1 to save.
     * @param statusV1 the statusV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusV1,
     * or with status {@code 400 (Bad Request)} if the statusV1 is not valid,
     * or with status {@code 404 (Not Found)} if the statusV1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the statusV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/statuses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<StatusV1> partialUpdateStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StatusV1 statusV1
    ) throws URISyntaxException {
        log.debug("REST request to partial update Status partially : {}, {}", id, statusV1);
        if (statusV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatusV1> result = statusService.partialUpdate(statusV1);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusV1.getId().toString())
        );
    }

    /**
     * {@code GET  /statuses} : get all the statuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statuses in body.
     */
    @GetMapping("/statuses")
    public List<StatusV1> getAllStatuses() {
        log.debug("REST request to get all Statuses");
        return statusService.findAll();
    }

    /**
     * {@code GET  /statuses/:id} : get the "id" status.
     *
     * @param id the id of the statusV1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusV1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statuses/{id}")
    public ResponseEntity<StatusV1> getStatus(@PathVariable Long id) {
        log.debug("REST request to get Status : {}", id);
        Optional<StatusV1> statusV1 = statusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusV1);
    }

    /**
     * {@code DELETE  /statuses/:id} : delete the "id" status.
     *
     * @param id the id of the statusV1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statuses/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        log.debug("REST request to delete Status : {}", id);
        statusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
