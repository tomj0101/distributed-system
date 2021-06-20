package com.github.tomj0101.ebankv1.ordersystemv2.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.Address;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.AddressRepository;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.search.AddressSearchRepository;
import com.github.tomj0101.ebankv1.ordersystemv2.service.AddressService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressSearchRepository addressSearchRepository;

    public AddressServiceImpl(AddressRepository addressRepository, AddressSearchRepository addressSearchRepository) {
        this.addressRepository = addressRepository;
        this.addressSearchRepository = addressSearchRepository;
    }

    @Override
    public Address save(Address address) {
        log.debug("Request to save Address : {}", address);
        Address result = addressRepository.save(address);
        addressSearchRepository.save(result);
        return result;
    }

    @Override
    public Optional<Address> partialUpdate(Address address) {
        log.debug("Request to partially update Address : {}", address);

        return addressRepository
            .findById(address.getId())
            .map(
                existingAddress -> {
                    if (address.getStreetAddress() != null) {
                        existingAddress.setStreetAddress(address.getStreetAddress());
                    }
                    if (address.getPostalCode() != null) {
                        existingAddress.setPostalCode(address.getPostalCode());
                    }
                    if (address.getCity() != null) {
                        existingAddress.setCity(address.getCity());
                    }
                    if (address.getStateProvince() != null) {
                        existingAddress.setStateProvince(address.getStateProvince());
                    }

                    return existingAddress;
                }
            )
            .map(addressRepository::save)
            .map(
                savedAddress -> {
                    addressSearchRepository.save(savedAddress);

                    return savedAddress;
                }
            );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Address> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
        addressSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Address> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Addresses for query {}", query);
        return addressSearchRepository.search(queryStringQuery(query), pageable);
    }
}
