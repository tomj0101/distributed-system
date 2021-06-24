package com.github.tomj0101.ebankv1.ordersystemv3.order.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.github.tomj0101.ebankv1.ordersystemv3.order.domain.OrderMaster;
import com.github.tomj0101.ebankv1.ordersystemv3.order.repository.OrderMasterRepository;
import com.github.tomj0101.ebankv1.ordersystemv3.order.service.OrderMasterService;
import com.github.tomj0101.ebankv1.ordersystemv3.order.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.github.tomj0101.ebankv1.ordersystemv3.order.domain.OrderMaster}.
 */
@RestController
@RequestMapping("/api")
public class OrderMasterResource {

    private final Logger log = LoggerFactory.getLogger(OrderMasterResource.class);

    private static final String ENTITY_NAME = "orderapiOrderMaster";

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
     * @param orderMaster the orderMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderMaster, or with status {@code 400 (Bad Request)} if the orderMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-masters")
    public ResponseEntity<OrderMaster> createOrderMaster(@Valid @RequestBody OrderMaster orderMaster) throws URISyntaxException {
        log.debug("REST request to save OrderMaster : {}", orderMaster);
        if (orderMaster.getId() != null) {
            throw new BadRequestAlertException("A new orderMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderMaster result = orderMasterService.save(orderMaster);
        return ResponseEntity
            .created(new URI("/api/order-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-masters/:id} : Updates an existing orderMaster.
     *
     * @param id the id of the orderMaster to save.
     * @param orderMaster the orderMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderMaster,
     * or with status {@code 400 (Bad Request)} if the orderMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-masters/{id}")
    public ResponseEntity<OrderMaster> updateOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderMaster orderMaster
    ) throws URISyntaxException {
        log.debug("REST request to update OrderMaster : {}, {}", id, orderMaster);
        if (orderMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderMaster result = orderMasterService.save(orderMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-masters/:id} : Partial updates given fields of an existing orderMaster, field will ignore if it is null
     *
     * @param id the id of the orderMaster to save.
     * @param orderMaster the orderMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderMaster,
     * or with status {@code 400 (Bad Request)} if the orderMaster is not valid,
     * or with status {@code 404 (Not Found)} if the orderMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-masters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrderMaster> partialUpdateOrderMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderMaster orderMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderMaster partially : {}, {}", id, orderMaster);
        if (orderMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderMaster> result = orderMasterService.partialUpdate(orderMaster);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /order-masters} : get all the orderMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderMasters in body.
     */
    @GetMapping("/order-masters")
    public ResponseEntity<List<OrderMaster>> getAllOrderMasters(Pageable pageable) {
        log.debug("REST request to get a page of OrderMasters");
        Page<OrderMaster> page = orderMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-masters/:id} : get the "id" orderMaster.
     *
     * @param id the id of the orderMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-masters/{id}")
    public ResponseEntity<OrderMaster> getOrderMaster(@PathVariable Long id) {
        log.debug("REST request to get OrderMaster : {}", id);
        Optional<OrderMaster> orderMaster = orderMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderMaster);
    }

    /**
     * {@code DELETE  /order-masters/:id} : delete the "id" orderMaster.
     *
     * @param id the id of the orderMaster to delete.
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

    /**
     * {@code SEARCH  /_search/order-masters?query=:query} : search for the orderMaster corresponding
     * to the query.
     *
     * @param query the query of the orderMaster search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/order-masters")
    public ResponseEntity<List<OrderMaster>> searchOrderMasters(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrderMasters for query {}", query);
        Page<OrderMaster> page = orderMasterService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
