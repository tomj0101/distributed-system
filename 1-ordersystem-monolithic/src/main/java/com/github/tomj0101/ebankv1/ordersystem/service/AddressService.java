package com.github.tomj0101.ebankv1.ordersystem.service;

import com.github.tomj0101.ebankv1.ordersystem.domain.AddressV1;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AddressV1}.
 */
public interface AddressService {
    /**
     * Save a address.
     *
     * @param addressV1 the entity to save.
     * @return the persisted entity.
     */
    AddressV1 save(AddressV1 addressV1);

    /**
     * Partially updates a address.
     *
     * @param addressV1 the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AddressV1> partialUpdate(AddressV1 addressV1);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AddressV1> findAll(Pageable pageable);

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressV1> findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
