package com.github.tomj0101.ebankv1.web.rest;

import com.github.tomj0101.ebankv1.domain.CustomerSupport;
import com.github.tomj0101.ebankv1.repository.CustomerSupportRepository;
import com.github.tomj0101.ebankv1.service.CustomerSupportService;
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
 * REST controller for managing {@link com.github.tomj0101.ebankv1.domain.CustomerSupport}.
 */
@RestController
@RequestMapping("/api")
public class CustomerSupportResource {

    private final Logger log = LoggerFactory.getLogger(CustomerSupportResource.class);

    private static final String ENTITY_NAME = "customerSupport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerSupportService customerSupportService;

    private final CustomerSupportRepository customerSupportRepository;

    public CustomerSupportResource(CustomerSupportService customerSupportService, CustomerSupportRepository customerSupportRepository) {
        this.customerSupportService = customerSupportService;
        this.customerSupportRepository = customerSupportRepository;
    }

    /**
     * {@code POST  /customer-supports} : Create a new customerSupport.
     *
     * @param customerSupport the customerSupport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerSupport, or with status {@code 400 (Bad Request)} if the customerSupport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-supports")
    public ResponseEntity<CustomerSupport> createCustomerSupport(@Valid @RequestBody CustomerSupport customerSupport)
        throws URISyntaxException {
        log.debug("REST request to save CustomerSupport : {}", customerSupport);
        if (customerSupport.getId() != null) {
            throw new BadRequestAlertException("A new customerSupport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerSupport result = customerSupportService.save(customerSupport);
        return ResponseEntity
            .created(new URI("/api/customer-supports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-supports/:id} : Updates an existing customerSupport.
     *
     * @param id the id of the customerSupport to save.
     * @param customerSupport the customerSupport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerSupport,
     * or with status {@code 400 (Bad Request)} if the customerSupport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerSupport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-supports/{id}")
    public ResponseEntity<CustomerSupport> updateCustomerSupport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerSupport customerSupport
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerSupport : {}, {}", id, customerSupport);
        if (customerSupport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerSupport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerSupportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerSupport result = customerSupportService.save(customerSupport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerSupport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-supports/:id} : Partial updates given fields of an existing customerSupport, field will ignore if it is null
     *
     * @param id the id of the customerSupport to save.
     * @param customerSupport the customerSupport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerSupport,
     * or with status {@code 400 (Bad Request)} if the customerSupport is not valid,
     * or with status {@code 404 (Not Found)} if the customerSupport is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerSupport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-supports/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomerSupport> partialUpdateCustomerSupport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerSupport customerSupport
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerSupport partially : {}, {}", id, customerSupport);
        if (customerSupport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerSupport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerSupportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerSupport> result = customerSupportService.partialUpdate(customerSupport);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerSupport.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-supports} : get all the customerSupports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerSupports in body.
     */
    @GetMapping("/customer-supports")
    public ResponseEntity<List<CustomerSupport>> getAllCustomerSupports(Pageable pageable) {
        log.debug("REST request to get a page of CustomerSupports");
        Page<CustomerSupport> page = customerSupportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-supports/:id} : get the "id" customerSupport.
     *
     * @param id the id of the customerSupport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerSupport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-supports/{id}")
    public ResponseEntity<CustomerSupport> getCustomerSupport(@PathVariable Long id) {
        log.debug("REST request to get CustomerSupport : {}", id);
        Optional<CustomerSupport> customerSupport = customerSupportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerSupport);
    }

    /**
     * {@code DELETE  /customer-supports/:id} : delete the "id" customerSupport.
     *
     * @param id the id of the customerSupport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-supports/{id}")
    public ResponseEntity<Void> deleteCustomerSupport(@PathVariable Long id) {
        log.debug("REST request to delete CustomerSupport : {}", id);
        customerSupportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
