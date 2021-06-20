package com.github.tomj0101.ebankv1.ordersystemv2.repository;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Status;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {}
