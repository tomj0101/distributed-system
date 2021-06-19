package com.github.tomj0101.ebankv1.ordersystem.service;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderDetailsV1;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OrderDetailsV1}.
 */
public interface OrderDetailsService {
    /**
     * Save a orderDetails.
     *
     * @param orderDetailsV1 the entity to save.
     * @return the persisted entity.
     */
    OrderDetailsV1 save(OrderDetailsV1 orderDetailsV1);

    /**
     * Partially updates a orderDetails.
     *
     * @param orderDetailsV1 the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderDetailsV1> partialUpdate(OrderDetailsV1 orderDetailsV1);

    /**
     * Get all the orderDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderDetailsV1> findAll(Pageable pageable);

    /**
     * Get the "id" orderDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderDetailsV1> findOne(Long id);

    /**
     * Delete the "id" orderDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
