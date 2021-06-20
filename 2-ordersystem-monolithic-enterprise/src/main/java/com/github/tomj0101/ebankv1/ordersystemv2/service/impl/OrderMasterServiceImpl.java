package com.github.tomj0101.ebankv1.ordersystemv2.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.OrderMaster;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.OrderMasterRepository;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.search.OrderMasterSearchRepository;
import com.github.tomj0101.ebankv1.ordersystemv2.service.OrderMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderMaster}.
 */
@Service
@Transactional
public class OrderMasterServiceImpl implements OrderMasterService {

    private final Logger log = LoggerFactory.getLogger(OrderMasterServiceImpl.class);

    private final OrderMasterRepository orderMasterRepository;

    private final OrderMasterSearchRepository orderMasterSearchRepository;

    public OrderMasterServiceImpl(OrderMasterRepository orderMasterRepository, OrderMasterSearchRepository orderMasterSearchRepository) {
        this.orderMasterRepository = orderMasterRepository;
        this.orderMasterSearchRepository = orderMasterSearchRepository;
    }

    @Override
    public OrderMaster save(OrderMaster orderMaster) {
        log.debug("Request to save OrderMaster : {}", orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        orderMasterSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<OrderMaster> partialUpdate(OrderMaster orderMaster) {
        log.debug("Request to partially update OrderMaster : {}", orderMaster);

        return orderMasterRepository
            .findById(orderMaster.getId())
            .map(
                existingOrderMaster -> {
                    if (orderMaster.getPaymentMethod() != null) {
                        existingOrderMaster.setPaymentMethod(orderMaster.getPaymentMethod());
                    }
                    if (orderMaster.getTotal() != null) {
                        existingOrderMaster.setTotal(orderMaster.getTotal());
                    }
                    if (orderMaster.getEmail() != null) {
                        existingOrderMaster.setEmail(orderMaster.getEmail());
                    }
                    if (orderMaster.getRegisterDate() != null) {
                        existingOrderMaster.setRegisterDate(orderMaster.getRegisterDate());
                    }

                    return existingOrderMaster;
                }
            )
            .map(orderMasterRepository::save)
            .map(
                savedOrderMaster -> {
                    orderMasterSearchRepository.save(savedOrderMaster);

                    return savedOrderMaster;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderMaster> findAll(Pageable pageable) {
        log.debug("Request to get all OrderMasters");
        return orderMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderMaster> findOne(Long id) {
        log.debug("Request to get OrderMaster : {}", id);
        return orderMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderMaster : {}", id);
        orderMasterRepository.deleteById(id);
        orderMasterSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderMaster> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderMasters for query {}", query);
        return orderMasterSearchRepository.search(queryStringQuery(query), pageable);
    }
}
