package com.github.tomj0101.ebankv1.ordersystem.repository;

import com.github.tomj0101.ebankv1.ordersystem.domain.AddressV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the AddressV1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<AddressV1, Long> {
    @Query("select a from AddressV1 a where a.user.login = ?#{principal.username}")
    Page<AddressV1> findByUserIsCurrentUser(Pageable pageable);
}
