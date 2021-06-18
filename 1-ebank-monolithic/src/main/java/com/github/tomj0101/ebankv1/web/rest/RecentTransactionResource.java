package com.github.tomj0101.ebankv1.web.rest;

import com.github.tomj0101.ebankv1.domain.RecentTransaction;
import com.github.tomj0101.ebankv1.repository.RecentTransactionRepository;
import com.github.tomj0101.ebankv1.service.RecentTransactionService;
import com.github.tomj0101.ebankv1.web.rest.errors.BadRequestAlertException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.tomj0101.ebankv1.domain.RecentTransaction}.
 */
@RestController
@RequestMapping("/api")
public class RecentTransactionResource {

    private final Logger log = LoggerFactory.getLogger(RecentTransactionResource.class);

    private static final String ENTITY_NAME = "recentTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecentTransactionService recentTransactionService;

    private final RecentTransactionRepository recentTransactionRepository;

    public RecentTransactionResource(
        RecentTransactionService recentTransactionService,
        RecentTransactionRepository recentTransactionRepository
    ) {
        this.recentTransactionService = recentTransactionService;
        this.recentTransactionRepository = recentTransactionRepository;
    }

    /**
     * {@code POST  /recent-transactions} : Create a new recentTransaction.
     *
     * @param recentTransaction the recentTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recentTransaction, or with status {@code 400 (Bad Request)} if the recentTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recent-transactions")
    public ResponseEntity<RecentTransaction> createRecentTransaction(@Valid @RequestBody RecentTransaction recentTransaction)
        throws URISyntaxException {
        log.debug("REST request to save RecentTransaction : {}", recentTransaction);
        if (recentTransaction.getId() != null) {
            throw new BadRequestAlertException("A new recentTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecentTransaction result = recentTransactionService.save(recentTransaction);
        return ResponseEntity
            .created(new URI("/api/recent-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recent-transactions/:id} : Updates an existing recentTransaction.
     *
     * @param id the id of the recentTransaction to save.
     * @param recentTransaction the recentTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recentTransaction,
     * or with status {@code 400 (Bad Request)} if the recentTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recentTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recent-transactions/{id}")
    public ResponseEntity<RecentTransaction> updateRecentTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecentTransaction recentTransaction
    ) throws URISyntaxException {
        log.debug("REST request to update RecentTransaction : {}, {}", id, recentTransaction);
        if (recentTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recentTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recentTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecentTransaction result = recentTransactionService.save(recentTransaction);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recentTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recent-transactions/:id} : Partial updates given fields of an existing recentTransaction, field will ignore if it is null
     *
     * @param id the id of the recentTransaction to save.
     * @param recentTransaction the recentTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recentTransaction,
     * or with status {@code 400 (Bad Request)} if the recentTransaction is not valid,
     * or with status {@code 404 (Not Found)} if the recentTransaction is not found,
     * or with status {@code 500 (Internal Server Error)} if the recentTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recent-transactions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RecentTransaction> partialUpdateRecentTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecentTransaction recentTransaction
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecentTransaction partially : {}, {}", id, recentTransaction);
        if (recentTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recentTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recentTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecentTransaction> result = recentTransactionService.partialUpdate(recentTransaction);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recentTransaction.getId().toString())
        );
    }

    /**
     * {@code GET  /recent-transactions} : get all the recentTransactions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recentTransactions in body.
     */
    @GetMapping("/recent-transactions")
    public ResponseEntity<List<RecentTransaction>> getAllRecentTransactions(Pageable pageable) {
        log.debug("REST request to get a page of RecentTransactions");
        Page<RecentTransaction> page = recentTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recent-transactions/:id} : get the "id" recentTransaction.
     *
     * @param id the id of the recentTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recentTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recent-transactions/{id}")
    public ResponseEntity<RecentTransaction> getRecentTransaction(@PathVariable Long id) {
        log.debug("REST request to get RecentTransaction : {}", id);
        Optional<RecentTransaction> recentTransaction = recentTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recentTransaction);
    }

    /**
     * {@code DELETE  /recent-transactions/:id} : delete the "id" recentTransaction.
     *
     * @param id the id of the recentTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recent-transactions/{id}")
    public ResponseEntity<Void> deleteRecentTransaction(@PathVariable Long id) {
        log.debug("REST request to delete RecentTransaction : {}", id);
        recentTransactionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
