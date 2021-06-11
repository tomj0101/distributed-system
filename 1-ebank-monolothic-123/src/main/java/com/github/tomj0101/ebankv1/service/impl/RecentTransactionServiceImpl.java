package com.github.tomj0101.ebankv1.service.impl;

import com.github.tomj0101.ebankv1.domain.RecentTransaction;
import com.github.tomj0101.ebankv1.repository.RecentTransactionRepository;
import com.github.tomj0101.ebankv1.service.RecentTransactionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RecentTransaction}.
 */
@Service
@Transactional
public class RecentTransactionServiceImpl implements RecentTransactionService {

    private final Logger log = LoggerFactory.getLogger(RecentTransactionServiceImpl.class);

    private final RecentTransactionRepository recentTransactionRepository;

    public RecentTransactionServiceImpl(RecentTransactionRepository recentTransactionRepository) {
        this.recentTransactionRepository = recentTransactionRepository;
    }

    @Override
    public RecentTransaction save(RecentTransaction recentTransaction) {
        log.debug("Request to save RecentTransaction : {}", recentTransaction);
        return recentTransactionRepository.save(recentTransaction);
    }

    @Override
    public Optional<RecentTransaction> partialUpdate(RecentTransaction recentTransaction) {
        log.debug("Request to partially update RecentTransaction : {}", recentTransaction);

        return recentTransactionRepository
            .findById(recentTransaction.getId())
            .map(
                existingRecentTransaction -> {
                    if (recentTransaction.getDescription() != null) {
                        existingRecentTransaction.setDescription(recentTransaction.getDescription());
                    }
                    if (recentTransaction.getAmount() != null) {
                        existingRecentTransaction.setAmount(recentTransaction.getAmount());
                    }
                    if (recentTransaction.getStatus() != null) {
                        existingRecentTransaction.setStatus(recentTransaction.getStatus());
                    }
                    if (recentTransaction.gettCreated() != null) {
                        existingRecentTransaction.settCreated(recentTransaction.gettCreated());
                    }

                    return existingRecentTransaction;
                }
            )
            .map(recentTransactionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecentTransaction> findAll(Pageable pageable) {
        log.debug("Request to get all RecentTransactions");
        return recentTransactionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecentTransaction> findOne(Long id) {
        log.debug("Request to get RecentTransaction : {}", id);
        return recentTransactionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecentTransaction : {}", id);
        recentTransactionRepository.deleteById(id);
    }
}
