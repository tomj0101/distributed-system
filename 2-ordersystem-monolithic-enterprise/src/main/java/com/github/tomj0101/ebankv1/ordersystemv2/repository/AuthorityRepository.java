package com.github.tomj0101.ebankv1.ordersystemv2.repository;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
