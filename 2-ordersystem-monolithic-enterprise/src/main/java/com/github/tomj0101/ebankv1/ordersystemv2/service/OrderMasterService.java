package com.github.tomj0101.ebankv1.ordersystemv2.service;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.OrderMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OrderMaster}.
 */
public interface OrderMasterService {
    /**
     * Save a orderMaster.
     *
     * @param orderMaster the entity to save.
     * @return the persisted entity.
     */
    OrderMaster save(OrderMaster orderMaster);

    /**
     * Partially updates a orderMaster.
     *
     * @param orderMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderMaster> partialUpdate(OrderMaster orderMaster);

    /**
     * Get all the orderMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderMaster> findAll(Pageable pageable);

    /**
     * Get the "id" orderMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderMaster> findOne(Long id);

    /**
     * Delete the "id" orderMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the orderMaster corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderMaster> search(String query, Pageable pageable);
}
