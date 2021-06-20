package com.github.tomj0101.ebankv1.ordersystemv2.repository.search;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.OrderMaster;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrderMaster} entity.
 */
public interface OrderMasterSearchRepository extends ElasticsearchRepository<OrderMaster, Long> {}
