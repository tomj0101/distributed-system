package com.github.tomj0101.ebankv1.ordersystem.repository;

import com.github.tomj0101.ebankv1.ordersystem.domain.StatusV1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StatusV1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends JpaRepository<StatusV1, Long> {}
