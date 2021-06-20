package com.github.tomj0101.ebankv1.ordersystemv2.repository.search;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Product} entity.
 */
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {}
