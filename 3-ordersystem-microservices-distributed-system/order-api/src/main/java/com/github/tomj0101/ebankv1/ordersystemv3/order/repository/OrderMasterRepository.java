package com.github.tomj0101.ebankv1.ordersystemv3.order.repository;

import com.github.tomj0101.ebankv1.ordersystemv3.order.domain.OrderMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the OrderMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderMasterRepository extends MongoRepository<OrderMaster, Long> {}
