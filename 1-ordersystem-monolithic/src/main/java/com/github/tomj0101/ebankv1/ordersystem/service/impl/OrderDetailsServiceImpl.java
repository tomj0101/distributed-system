package com.github.tomj0101.ebankv1.ordersystem.service.impl;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderDetailsV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.OrderDetailsRepository;
import com.github.tomj0101.ebankv1.ordersystem.security.SecurityUtils;
import com.github.tomj0101.ebankv1.ordersystem.service.OrderDetailsService;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderDetailsV1}.
 */
@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public OrderDetailsV1 save(OrderDetailsV1 orderDetailsV1) {
        log.debug("Request to save OrderDetails : {}", orderDetailsV1);
        orderDetailsV1.setRegisterDate(Instant.now());
        orderDetailsV1.setUserId(Long.parseLong(SecurityUtils.getCurrentUserLogin().get()));
        return orderDetailsRepository.save(orderDetailsV1);
    }

    @Override
    public Optional<OrderDetailsV1> partialUpdate(OrderDetailsV1 orderDetailsV1) {
        log.debug("Request to partially update OrderDetails : {}", orderDetailsV1);

        return orderDetailsRepository
            .findById(orderDetailsV1.getId())
            .map(
                existingOrderDetails -> {
                    if (orderDetailsV1.getUserId() != null) {
                        existingOrderDetails.setUserId(orderDetailsV1.getUserId());
                    }
                    if (orderDetailsV1.getRegisterDate() != null) {
                        existingOrderDetails.setRegisterDate(orderDetailsV1.getRegisterDate());
                    }

                    return existingOrderDetails;
                }
            )
            .map(orderDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailsV1> findAll(Pageable pageable) {
        log.debug("Request to get all OrderDetails");
        return orderDetailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailsV1> findOne(Long id) {
        log.debug("Request to get OrderDetails : {}", id);
        return orderDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetails : {}", id);
        orderDetailsRepository.deleteById(id);
    }
}
