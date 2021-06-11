package com.github.tomj0101.ebankv1.service;

import com.github.tomj0101.ebankv1.domain.RecentTransaction;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RecentTransaction}.
 */
public interface RecentTransactionService {
    /**
     * Save a recentTransaction.
     *
     * @param recentTransaction the entity to save.
     * @return the persisted entity.
     */
    RecentTransaction save(RecentTransaction recentTransaction);

    /**
     * Partially updates a recentTransaction.
     *
     * @param recentTransaction the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecentTransaction> partialUpdate(RecentTransaction recentTransaction);

    /**
     * Get all the recentTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecentTransaction> findAll(Pageable pageable);

    /**
     * Get the "id" recentTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecentTransaction> findOne(Long id);

    /**
     * Delete the "id" recentTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
