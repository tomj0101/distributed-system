package com.github.tomj0101.ebankv1.repository;

import com.github.tomj0101.ebankv1.domain.IssueSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IssueSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssueSystemRepository extends JpaRepository<IssueSystem, Long> {}
