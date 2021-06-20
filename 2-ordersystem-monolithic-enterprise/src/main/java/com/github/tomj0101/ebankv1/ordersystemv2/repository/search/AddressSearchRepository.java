package com.github.tomj0101.ebankv1.ordersystemv2.repository.search;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Address} entity.
 */
public interface AddressSearchRepository extends ElasticsearchRepository<Address, Long> {}
