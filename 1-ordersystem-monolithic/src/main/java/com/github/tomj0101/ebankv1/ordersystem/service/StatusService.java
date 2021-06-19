package com.github.tomj0101.ebankv1.ordersystem.service;

import com.github.tomj0101.ebankv1.ordersystem.domain.StatusV1;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StatusV1}.
 */
public interface StatusService {
    /**
     * Save a status.
     *
     * @param statusV1 the entity to save.
     * @return the persisted entity.
     */
    StatusV1 save(StatusV1 statusV1);

    /**
     * Partially updates a status.
     *
     * @param statusV1 the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatusV1> partialUpdate(StatusV1 statusV1);

    /**
     * Get all the statuses.
     *
     * @return the list of entities.
     */
    List<StatusV1> findAll();

    /**
     * Get the "id" status.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatusV1> findOne(Long id);

    /**
     * Delete the "id" status.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
