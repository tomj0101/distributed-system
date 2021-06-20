package com.github.tomj0101.ebankv1.ordersystemv2.repository;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Address;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
