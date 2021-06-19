package com.github.tomj0101.ebankv1.ordersystem.service.impl;

import com.github.tomj0101.ebankv1.ordersystem.domain.StatusV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.StatusRepository;
import com.github.tomj0101.ebankv1.ordersystem.service.StatusService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatusV1}.
 */
@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public StatusV1 save(StatusV1 statusV1) {
        log.debug("Request to save Status : {}", statusV1);
        statusV1.setRegisterDate(Instant.now());
        return statusRepository.save(statusV1);
    }

    @Override
    public Optional<StatusV1> partialUpdate(StatusV1 statusV1) {
        log.debug("Request to partially update Status : {}", statusV1);

        return statusRepository
            .findById(statusV1.getId())
            .map(
                existingStatus -> {
                    if (statusV1.getName() != null) {
                        existingStatus.setName(statusV1.getName());
                    }
                    if (statusV1.getDescription() != null) {
                        existingStatus.setDescription(statusV1.getDescription());
                    }
                    if (statusV1.getRegisterDate() != null) {
                        existingStatus.setRegisterDate(statusV1.getRegisterDate());
                    }

                    return existingStatus;
                }
            )
            .map(statusRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusV1> findAll() {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatusV1> findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
    }
}
