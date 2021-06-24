package com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.repository;

import com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.domain.Authority;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends ReactiveMongoRepository<Authority, String> {}
