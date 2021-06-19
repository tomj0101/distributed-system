package com.github.tomj0101.ebankv1.ordersystem.repository;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderDetailsV1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderDetailsV1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsV1, Long> {}
