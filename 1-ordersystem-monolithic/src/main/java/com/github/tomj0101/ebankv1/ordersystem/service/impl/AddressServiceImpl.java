package com.github.tomj0101.ebankv1.ordersystem.service.impl;

import com.github.tomj0101.ebankv1.ordersystem.domain.AddressV1;
import com.github.tomj0101.ebankv1.ordersystem.domain.UserV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.AddressRepository;
import com.github.tomj0101.ebankv1.ordersystem.repository.UserRepository;
import com.github.tomj0101.ebankv1.ordersystem.security.SecurityUtils;
import com.github.tomj0101.ebankv1.ordersystem.service.AddressService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AddressV1}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddressV1 save(AddressV1 addressV1) {
        log.debug("Request to save Address : {}", addressV1);
        /*getting the user from the current login user*/
        UserV1 user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        addressV1.setUser(user);
        return addressRepository.save(addressV1);
    }

    @Override
    public Optional<AddressV1> partialUpdate(AddressV1 addressV1) {
        log.debug("Request to partially update Address : {}", addressV1);

        return addressRepository
            .findById(addressV1.getId())
            .map(
                existingAddress -> {
                    if (addressV1.getStreetAddress() != null) {
                        existingAddress.setStreetAddress(addressV1.getStreetAddress());
                    }
                    if (addressV1.getPostalCode() != null) {
                        existingAddress.setPostalCode(addressV1.getPostalCode());
                    }
                    if (addressV1.getCity() != null) {
                        existingAddress.setCity(addressV1.getCity());
                    }
                    if (addressV1.getStateProvince() != null) {
                        existingAddress.setStateProvince(addressV1.getStateProvince());
                    }

                    return existingAddress;
                }
            )
            .map(addressRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressV1> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressV1> findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
    }
}
