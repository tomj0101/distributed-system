package com.github.tomj0101.ebankv1.ordersystemv2.repository.search;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {}
