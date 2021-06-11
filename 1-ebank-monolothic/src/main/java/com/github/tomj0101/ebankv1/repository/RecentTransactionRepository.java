package com.github.tomj0101.ebankv1.repository;

import com.github.tomj0101.ebankv1.domain.RecentTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RecentTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecentTransactionRepository extends JpaRepository<RecentTransaction, Long> {}
