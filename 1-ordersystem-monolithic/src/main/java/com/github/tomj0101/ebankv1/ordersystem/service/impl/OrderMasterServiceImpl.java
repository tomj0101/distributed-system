package com.github.tomj0101.ebankv1.ordersystem.service.impl;

import com.github.tomj0101.ebankv1.ordersystem.domain.OrderMasterV1;
import com.github.tomj0101.ebankv1.ordersystem.domain.UserV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.OrderMasterRepository;
import com.github.tomj0101.ebankv1.ordersystem.repository.UserRepository;
import com.github.tomj0101.ebankv1.ordersystem.security.SecurityUtils;
import com.github.tomj0101.ebankv1.ordersystem.service.OrderMasterService;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderMasterV1}.
 * This module is not in use
 */
@Service
@Transactional
public class OrderMasterServiceImpl implements OrderMasterService {

    private final Logger log = LoggerFactory.getLogger(OrderMasterServiceImpl.class);

    private final OrderMasterRepository orderMasterRepository;

    private final UserRepository userRepository;

    public OrderMasterServiceImpl(OrderMasterRepository orderMasterRepository, UserRepository userRepository) {
        this.orderMasterRepository = orderMasterRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderMasterV1 save(OrderMasterV1 orderMasterV1) {
        log.debug("Request to save OrderMaster : {}", orderMasterV1);
        UserV1 user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        orderMasterV1.setUser(user);
        orderMasterV1.setRegisterDate(Instant.now());
        return orderMasterRepository.save(orderMasterV1);
    }

    @Override
    public Optional<OrderMasterV1> partialUpdate(OrderMasterV1 orderMasterV1) {
        log.debug("Request to partially update OrderMaster : {}", orderMasterV1);

        return orderMasterRepository
            .findById(orderMasterV1.getId())
            .map(
                existingOrderMaster -> {
                    if (orderMasterV1.getPaymentMethod() != null) {
                        existingOrderMaster.setPaymentMethod(orderMasterV1.getPaymentMethod());
                    }
                    if (orderMasterV1.getTotal() != null) {
                        existingOrderMaster.setTotal(orderMasterV1.getTotal());
                    }
                    if (orderMasterV1.getEmail() != null) {
                        existingOrderMaster.setEmail(orderMasterV1.getEmail());
                    }
                    if (orderMasterV1.getRegisterDate() != null) {
                        existingOrderMaster.setRegisterDate(orderMasterV1.getRegisterDate());
                    }

                    return existingOrderMaster;
                }
            )
            .map(orderMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderMasterV1> findAll(Pageable pageable) {
        log.debug("Request to get all OrderMasters");
        //return orderMasterRepository.findAll(pageable);
        return orderMasterRepository.findByUserIsCurrentUser(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderMasterV1> findOne(Long id) {
        log.debug("Request to get OrderMaster : {}", id);
        //also security can be add it here to find just the Order that is yours.
        //try to do it for put in practice your Spring Boot JPA knowledge
        return orderMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderMaster : {}", id);
        //also security can be add it here to don't allow delete ordes that don't below to you
        //try to do it for put in practice your Spring Boot JPA knowledge
        orderMasterRepository.deleteById(id);
    }
}
