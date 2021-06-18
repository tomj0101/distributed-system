package com.github.tomj0101.ebankv1.web.rest;

import com.github.tomj0101.ebankv1.domain.IssueSystem;
import com.github.tomj0101.ebankv1.repository.IssueSystemRepository;
import com.github.tomj0101.ebankv1.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.tomj0101.ebankv1.domain.IssueSystem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IssueSystemResource {

    private final Logger log = LoggerFactory.getLogger(IssueSystemResource.class);

    private static final String ENTITY_NAME = "issueSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssueSystemRepository issueSystemRepository;

    public IssueSystemResource(IssueSystemRepository issueSystemRepository) {
        this.issueSystemRepository = issueSystemRepository;
    }

    /**
     * {@code POST  /issue-systems} : Create a new issueSystem.
     *
     * @param issueSystem the issueSystem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issueSystem, or with status {@code 400 (Bad Request)} if the issueSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issue-systems")
    public ResponseEntity<IssueSystem> createIssueSystem(@RequestBody IssueSystem issueSystem) throws URISyntaxException {
        log.debug("REST request to save IssueSystem : {}", issueSystem);
        if (issueSystem.getId() != null) {
            throw new BadRequestAlertException("A new issueSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssueSystem result = issueSystemRepository.save(issueSystem);
        return ResponseEntity
            .created(new URI("/api/issue-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issue-systems/:id} : Updates an existing issueSystem.
     *
     * @param id the id of the issueSystem to save.
     * @param issueSystem the issueSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueSystem,
     * or with status {@code 400 (Bad Request)} if the issueSystem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issueSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issue-systems/{id}")
    public ResponseEntity<IssueSystem> updateIssueSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssueSystem issueSystem
    ) throws URISyntaxException {
        log.debug("REST request to update IssueSystem : {}, {}", id, issueSystem);
        if (issueSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issueSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IssueSystem result = issueSystemRepository.save(issueSystem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issueSystem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /issue-systems/:id} : Partial updates given fields of an existing issueSystem, field will ignore if it is null
     *
     * @param id the id of the issueSystem to save.
     * @param issueSystem the issueSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issueSystem,
     * or with status {@code 400 (Bad Request)} if the issueSystem is not valid,
     * or with status {@code 404 (Not Found)} if the issueSystem is not found,
     * or with status {@code 500 (Internal Server Error)} if the issueSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/issue-systems/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IssueSystem> partialUpdateIssueSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IssueSystem issueSystem
    ) throws URISyntaxException {
        log.debug("REST request to partial update IssueSystem partially : {}, {}", id, issueSystem);
        if (issueSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, issueSystem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!issueSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IssueSystem> result = issueSystemRepository
            .findById(issueSystem.getId())
            .map(
                existingIssueSystem -> {
                    if (issueSystem.getTitle() != null) {
                        existingIssueSystem.setTitle(issueSystem.getTitle());
                    }
                    if (issueSystem.getDescription() != null) {
                        existingIssueSystem.setDescription(issueSystem.getDescription());
                    }
                    if (issueSystem.getiCreated() != null) {
                        existingIssueSystem.setiCreated(issueSystem.getiCreated());
                    }

                    return existingIssueSystem;
                }
            )
            .map(issueSystemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issueSystem.getId().toString())
        );
    }

    /**
     * {@code GET  /issue-systems} : get all the issueSystems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issueSystems in body.
     */
    @GetMapping("/issue-systems")
    public List<IssueSystem> getAllIssueSystems() {
        log.debug("REST request to get all IssueSystems");
        return issueSystemRepository.findAll();
    }

    /**
     * {@code GET  /issue-systems/:id} : get the "id" issueSystem.
     *
     * @param id the id of the issueSystem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issueSystem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issue-systems/{id}")
    public ResponseEntity<IssueSystem> getIssueSystem(@PathVariable Long id) {
        log.debug("REST request to get IssueSystem : {}", id);
        Optional<IssueSystem> issueSystem = issueSystemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(issueSystem);
    }

    /**
     * {@code DELETE  /issue-systems/:id} : delete the "id" issueSystem.
     *
     * @param id the id of the issueSystem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issue-systems/{id}")
    public ResponseEntity<Void> deleteIssueSystem(@PathVariable Long id) {
        log.debug("REST request to delete IssueSystem : {}", id);
        issueSystemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
