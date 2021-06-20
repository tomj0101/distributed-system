package com.github.tomj0101.ebankv1.ordersystemv2.repository.search;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Status;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Status} entity.
 */
public interface StatusSearchRepository extends ElasticsearchRepository<Status, Long> {}
