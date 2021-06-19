package com.github.tomj0101.ebankv1.ordersystem.repository;

import com.github.tomj0101.ebankv1.ordersystem.domain.UserV1;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link UserV1} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserV1, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<UserV1> findOneByActivationKey(String activationKey);

    List<UserV1> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<UserV1> findOneByResetKey(String resetKey);

    Optional<UserV1> findOneByEmailIgnoreCase(String email);

    Optional<UserV1> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<UserV1> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<UserV1> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<UserV1> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
