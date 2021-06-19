package com.github.tomj0101.ebankv1.ordersystem.service;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderMasterV1;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link OrderMasterV1}.
 */
public interface OrderMasterService {
    /**
     * Save a orderMaster.
     *
     * @param orderMasterV1 the entity to save.
     * @return the persisted entity.
     */
    OrderMasterV1 save(OrderMasterV1 orderMasterV1);

    /**
     * Partially updates a orderMaster.
     *
     * @param orderMasterV1 the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderMasterV1> partialUpdate(OrderMasterV1 orderMasterV1);

    /**
     * Get all the orderMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderMasterV1> findAll(Pageable pageable);

    /**
     * Get the "id" orderMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderMasterV1> findOne(Long id);

    /**
     * Delete the "id" orderMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
