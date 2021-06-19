package com.github.tomj0101.ebankv1.ordersystem.service;

import com.github.tomj0101.ebankv1.ordersystem.domain.ProductV1;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductV1}.
 */
public interface ProductService {
    /**
     * Save a product.
     *
     * @param productV1 the entity to save.
     * @return the persisted entity.
     */
    ProductV1 save(ProductV1 productV1);

    /**
     * Partially updates a product.
     *
     * @param productV1 the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductV1> partialUpdate(ProductV1 productV1);

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductV1> findAll(Pageable pageable);

    /**
     * Get the "id" product.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductV1> findOne(Long id);

    /**
     * Delete the "id" product.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
