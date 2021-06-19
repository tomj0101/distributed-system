package com.github.tomj0101.ebankv1.ordersystem.web.rest;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderDetailsV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.OrderDetailsRepository;
import com.github.tomj0101.ebankv1.ordersystem.service.OrderDetailsService;
import com.github.tomj0101.ebankv1.ordersystem.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.github.tomj0101.ebankv1.ordersystem.domain.OrderDetailsV1}.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsResource.class);

    private static final String ENTITY_NAME = "orderDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderDetailsService orderDetailsService;

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsResource(OrderDetailsService orderDetailsService, OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsService = orderDetailsService;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    /**
     * {@code POST  /order-details} : Create a new orderDetails.
     *
     * @param orderDetailsV1 the orderDetailsV1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDetailsV1, or with status {@code 400 (Bad Request)} if the orderDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-details")
    public ResponseEntity<OrderDetailsV1> createOrderDetails(@RequestBody OrderDetailsV1 orderDetailsV1) throws URISyntaxException {
        log.debug("REST request to save OrderDetails : {}", orderDetailsV1);
        if (orderDetailsV1.getId() != null) {
            throw new BadRequestAlertException("A new orderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDetailsV1 result = orderDetailsService.save(orderDetailsV1);
        return ResponseEntity
            .created(new URI("/api/order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-details/:id} : Updates an existing orderDetails.
     *
     * @param id the id of the orderDetailsV1 to save.
     * @param orderDetailsV1 the orderDetailsV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailsV1,
     * or with status {@code 400 (Bad Request)} if the orderDetailsV1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailsV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-details/{id}")
    public ResponseEntity<OrderDetailsV1> updateOrderDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetailsV1 orderDetailsV1
    ) throws URISyntaxException {
        log.debug("REST request to update OrderDetails : {}, {}", id, orderDetailsV1);
        if (orderDetailsV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetailsV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderDetailsV1 result = orderDetailsService.save(orderDetailsV1);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderDetailsV1.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-details/:id} : Partial updates given fields of an existing orderDetails, field will ignore if it is null
     *
     * @param id the id of the orderDetailsV1 to save.
     * @param orderDetailsV1 the orderDetailsV1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailsV1,
     * or with status {@code 400 (Bad Request)} if the orderDetailsV1 is not valid,
     * or with status {@code 404 (Not Found)} if the orderDetailsV1 is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailsV1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-details/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OrderDetailsV1> partialUpdateOrderDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetailsV1 orderDetailsV1
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderDetails partially : {}, {}", id, orderDetailsV1);
        if (orderDetailsV1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetailsV1.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderDetailsV1> result = orderDetailsService.partialUpdate(orderDetailsV1);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderDetailsV1.getId().toString())
        );
    }

    /**
     * {@code GET  /order-details} : get all the orderDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetails in body.
     */
    @GetMapping("/order-details")
    public ResponseEntity<List<OrderDetailsV1>> getAllOrderDetails(Pageable pageable) {
        log.debug("REST request to get a page of OrderDetails");
        Page<OrderDetailsV1> page = orderDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-details/:id} : get the "id" orderDetails.
     *
     * @param id the id of the orderDetailsV1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDetailsV1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-details/{id}")
    public ResponseEntity<OrderDetailsV1> getOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get OrderDetails : {}", id);
        Optional<OrderDetailsV1> orderDetailsV1 = orderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDetailsV1);
    }

    /**
     * {@code DELETE  /order-details/:id} : delete the "id" orderDetails.
     *
     * @param id the id of the orderDetailsV1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-details/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetails : {}", id);
        orderDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
