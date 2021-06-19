package com.github.tomj0101.ebankv1.ordersystem.web.rest;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderMasterV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.OrderMasterRepository;
import com.github.tomj0101.ebankv1.ordersystem.service.OrderMasterService;
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
 * REST controller for managing {@link com.github.tomj0101.ebankv1.ordersystem.domain.OrderMasterV1}.
 */
@RestController
@RequestMapping("/api")
public class OrderMasterResource {

    private final Logger log = LoggerFactory.getLogger(OrderMasterResource.class);

    private static final String ENTITY_NAME = "orderMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderMasterService orderMasterService;

    private final OrderMasterRepository orderMasterRepository;

    public OrderMasterResource(OrderMasterService orderMasterService, OrderMasterRepository orderMasterRepository) {
        this.orderMasterService = orderMasterService;
        this.orderMasterRepository = orderMasterRepository;
    }

    /**
     * {@code POST  /order-masters} : Create a new orderMaster.
     *
     * @param orderMasterV1 the orderMasterV1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderMasterV1, or with status {@code 400 (Bad Request)} if the orderMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-masters")
    public ResponseEntity<OrderMasterV1> createOrderMaster(@Valid @RequestBody OrderMasterV1 orderMasterV1) throws URISyntaxException {
        log.debug("REST request to save OrderMaster : {}", orderMasterV1);
        if (orderMasterV1.getId() != null) {
            throw new BadRequestAlertException("A new orderMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderMasterV1 result = orderMasterService.save(orderMasterV1);
        return ResponseEntity
            .created(new URI("/api/order-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-masters/:id} : Updates an existing orderMaster.
     *
     * @param id the id of the orderMasterV1 to save.
     * @param orderMasterV1 the orderMasterV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderMasterV1,
     * or with status {@code 400 (Bad Request)} if the orderMasterV1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderMasterV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-masters/{id}")
    public ResponseEntity<OrderMasterV1> updateOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderMasterV1 orderMasterV1
    ) throws URISyntaxException {
        log.debug("REST request to update OrderMaster : {}, {}", id, orderMasterV1);
        if (orderMasterV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderMasterV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderMasterV1 result = orderMasterService.save(orderMasterV1);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderMasterV1.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-masters/:id} : Partial updates given fields of an existing orderMaster, field will ignore if it is null
     *
     * @param id the id of the orderMasterV1 to save.
     * @param orderMasterV1 the orderMasterV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderMasterV1,
     * or with status {@code 400 (Bad Request)} if the orderMasterV1 is not valid,
     * or with status {@code 404 (Not Found)} if the orderMasterV1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderMasterV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-masters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrderMasterV1> partialUpdateOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderMasterV1 orderMasterV1
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderMaster partially : {}, {}", id, orderMasterV1);
        if (orderMasterV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderMasterV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderMasterV1> result = orderMasterService.partialUpdate(orderMasterV1);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderMasterV1.getId().toString())
        );
    }

    /**
     * {@code GET  /order-masters} : get all the orderMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderMasters in body.
     */
    @GetMapping("/order-masters")
    public ResponseEntity<List<OrderMasterV1>> getAllOrderMasters(Pageable pageable) {
        log.debug("REST request to get a page of OrderMasters");
        Page<OrderMasterV1> page = orderMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-masters/:id} : get the "id" orderMaster.
     *
     * @param id the id of the orderMasterV1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderMasterV1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-masters/{id}")
    public ResponseEntity<OrderMasterV1> getOrderMaster(@PathVariable Long id) {
        log.debug("REST request to get OrderMaster : {}", id);
        Optional<OrderMasterV1> orderMasterV1 = orderMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderMasterV1);
    }

    /**
     * {@code DELETE  /order-masters/:id} : delete the "id" orderMaster.
     *
     * @param id the id of the orderMasterV1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-masters/{id}")
    public ResponseEntity<Void> deleteOrderMaster(@PathVariable Long id) {
        log.debug("REST request to delete OrderMaster : {}", id);
        orderMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
