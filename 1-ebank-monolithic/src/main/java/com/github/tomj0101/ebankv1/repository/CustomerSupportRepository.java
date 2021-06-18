package com.github.tomj0101.ebankv1.repository;

import com.github.tomj0101.ebankv1.domain.CustomerSupport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomerSupport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerSupportRepository extends JpaRepository<CustomerSupport, Long> {}
