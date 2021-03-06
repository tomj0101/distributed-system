package com.github.tomj0101.ebankv1.ordersystem.repository;

import com.github.tomj0101.ebankv1.ordersystem.domain.PersistentToken;
import com.github.tomj0101.ebankv1.ordersystem.domain.UserV1;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link PersistentToken} entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {
    List<PersistentToken> findByUser(UserV1 user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);
}
