package com.github.tomj0101.ebankv1.ordersystem.web.rest;

import com.github.tomj0101.ebankv1.ordersystem.domain.AddressV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.AddressRepository;
import com.github.tomj0101.ebankv1.ordersystem.service.AddressService;
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
 * REST controller for managing {@link com.github.tomj0101.ebankv1.ordersystem.domain.AddressV1}.
 */
@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressService addressService;

    private final AddressRepository addressRepository;

    public AddressResource(AddressService addressService, AddressRepository addressRepository) {
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    /**
     * {@code POST  /addresses} : Create a new address.
     *
     * @param addressV1 the addressV1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressV1, or with status {@code 400 (Bad Request)} if the address has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/addresses")
    public ResponseEntity<AddressV1> createAddress(@Valid @RequestBody AddressV1 addressV1) throws URISyntaxException {
        log.debug("REST request to save Address : {}", addressV1);
        if (addressV1.getId() != null) {
            throw new BadRequestAlertException("A new address cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressV1 result = addressService.save(addressV1);
        return ResponseEntity
            .created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /addresses/:id} : Updates an existing address.
     *
     * @param id the id of the addressV1 to save.
     * @param addressV1 the addressV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressV1,
     * or with status {@code 400 (Bad Request)} if the addressV1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/addresses/{id}")
    public ResponseEntity<AddressV1> updateAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AddressV1 addressV1
    ) throws URISyntaxException {
        log.debug("REST request to update Address : {}, {}", id, addressV1);
        if (addressV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AddressV1 result = addressService.save(addressV1);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressV1.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /addresses/:id} : Partial updates given fields of an existing address, field will ignore if it is null
     *
     * @param id the id of the addressV1 to save.
     * @param addressV1 the addressV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressV1,
     * or with status {@code 400 (Bad Request)} if the addressV1 is not valid,
     * or with status {@code 404 (Not Found)} if the addressV1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the addressV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/addresses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AddressV1> partialUpdateAddress(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AddressV1 addressV1
    ) throws URISyntaxException {
        log.debug("REST request to partial update Address partially : {}, {}", id, addressV1);
        if (addressV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddressV1> result = addressService.partialUpdate(addressV1);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressV1.getId().toString())
        );
    }

    /**
     * {@code GET  /addresses} : get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addresses in body.
     */
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressV1>> getAllAddresses(Pageable pageable) {
        log.debug("REST request to get a page of Addresses");
        Page<AddressV1> page = addressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /addresses/:id} : get the "id" address.
     *
     * @param id the id of the addressV1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressV1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressV1> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        Optional<AddressV1> addressV1 = addressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressV1);
    }

    /**
     * {@code DELETE  /addresses/:id} : delete the "id" address.
     *
     * @param id the id of the addressV1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
