package com.github.tomj0101.ebankv1.ordersystemv2.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Status;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.StatusRepository;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.search.StatusSearchRepository;
import com.github.tomj0101.ebankv1.ordersystemv2.service.StatusService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Status}.
 */
@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    private final StatusSearchRepository statusSearchRepository;

    public StatusServiceImpl(StatusRepository statusRepository, StatusSearchRepository statusSearchRepository) {
        this.statusRepository = statusRepository;
        this.statusSearchRepository = statusSearchRepository;
    }

    @Override
    public Status save(Status status) {
        log.debug("Request to save Status : {}", status);
        Status result = statusRepository.save(status);
        statusSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Status> partialUpdate(Status status) {
        log.debug("Request to partially update Status : {}", status);

        return statusRepository
            .findById(status.getId())
            .map(
                existingStatus -> {
                    if (status.getName() != null) {
                        existingStatus.setName(status.getName());
                    }
                    if (status.getDescription() != null) {
                        existingStatus.setDescription(status.getDescription());
                    }
                    if (status.getRegisterDate() != null) {
                        existingStatus.setRegisterDate(status.getRegisterDate());
                    }

                    return existingStatus;
                }
            )
            .map(statusRepository::save)
            .map(
                savedStatus -> {
                    statusSearchRepository.save(savedStatus);

                    return savedStatus;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Status> findAll() {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Status> findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
        statusSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Status> search(String query) {
        log.debug("Request to search Statuses for query {}", query);
        return StreamSupport
            .stream(statusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
