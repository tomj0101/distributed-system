package com.github.tomj0101.ebankv1.service;

import com.github.tomj0101.ebankv1.domain.CustomerSupport;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CustomerSupport}.
 */
public interface CustomerSupportService {
    /**
     * Save a customerSupport.
     *
     * @param customerSupport the entity to save.
     * @return the persisted entity.
     */
    CustomerSupport save(CustomerSupport customerSupport);

    /**
     * Partially updates a customerSupport.
     *
     * @param customerSupport the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustomerSupport> partialUpdate(CustomerSupport customerSupport);

    /**
     * Get all the customerSupports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerSupport> findAll(Pageable pageable);

    /**
     * Get the "id" customerSupport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerSupport> findOne(Long id);

    /**
     * Delete the "id" customerSupport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
