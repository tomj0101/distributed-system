package com.github.tomj0101.ebankv1.ordersystem.repository;

import com.github.tomj0101.ebankv1.ordersystem.domain.AddressV1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AddressV1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<AddressV1, Long> {}
