package com.github.tomj0101.ebankv1.ordersystemv2.repository;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
