package com.github.tomj0101.ebankv1.service.impl;

import com.github.tomj0101.ebankv1.domain.CustomerSupport;
import com.github.tomj0101.ebankv1.repository.CustomerSupportRepository;
import com.github.tomj0101.ebankv1.service.CustomerSupportService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomerSupport}.
 */
@Service
@Transactional
public class CustomerSupportServiceImpl implements CustomerSupportService {

    private final Logger log = LoggerFactory.getLogger(CustomerSupportServiceImpl.class);

    private final CustomerSupportRepository customerSupportRepository;

    public CustomerSupportServiceImpl(CustomerSupportRepository customerSupportRepository) {
        this.customerSupportRepository = customerSupportRepository;
    }

    @Override
    public CustomerSupport save(CustomerSupport customerSupport) {
        log.debug("Request to save CustomerSupport : {}", customerSupport);
        return customerSupportRepository.save(customerSupport);
    }

    @Override
    public Optional<CustomerSupport> partialUpdate(CustomerSupport customerSupport) {
        log.debug("Request to partially update CustomerSupport : {}", customerSupport);

        return customerSupportRepository
            .findById(customerSupport.getId())
            .map(
                existingCustomerSupport -> {
                    if (customerSupport.getDescription() != null) {
                        existingCustomerSupport.setDescription(customerSupport.getDescription());
                    }
                    if (customerSupport.getcCreated() != null) {
                        existingCustomerSupport.setcCreated(customerSupport.getcCreated());
                    }
                    if (customerSupport.getSeverity() != null) {
                        existingCustomerSupport.setSeverity(customerSupport.getSeverity());
                    }
                    if (customerSupport.getStatus() != null) {
                        existingCustomerSupport.setStatus(customerSupport.getStatus());
                    }

                    return existingCustomerSupport;
                }
            )
            .map(customerSupportRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerSupport> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerSupports");
        return customerSupportRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerSupport> findOne(Long id) {
        log.debug("Request to get CustomerSupport : {}", id);
        return customerSupportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerSupport : {}", id);
        customerSupportRepository.deleteById(id);
    }
}
