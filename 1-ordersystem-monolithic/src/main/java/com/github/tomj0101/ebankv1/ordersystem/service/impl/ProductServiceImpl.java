package com.github.tomj0101.ebankv1.ordersystem.service.impl;

import com.github.tomj0101.ebankv1.ordersystem.domain.ProductV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.ProductRepository;
import com.github.tomj0101.ebankv1.ordersystem.service.ProductService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductV1}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductV1 save(ProductV1 productV1) {
        log.debug("Request to save Product : {}", productV1);
        return productRepository.save(productV1);
    }

    @Override
    public Optional<ProductV1> partialUpdate(ProductV1 productV1) {
        log.debug("Request to partially update Product : {}", productV1);

        return productRepository
            .findById(productV1.getId())
            .map(
                existingProduct -> {
                    if (productV1.getName() != null) {
                        existingProduct.setName(productV1.getName());
                    }
                    if (productV1.getDescription() != null) {
                        existingProduct.setDescription(productV1.getDescription());
                    }
                    if (productV1.getProductImages() != null) {
                        existingProduct.setProductImages(productV1.getProductImages());
                    }
                    if (productV1.getProductImagesContentType() != null) {
                        existingProduct.setProductImagesContentType(productV1.getProductImagesContentType());
                    }
                    if (productV1.getPrice() != null) {
                        existingProduct.setPrice(productV1.getPrice());
                    }
                    if (productV1.getCondition() != null) {
                        existingProduct.setCondition(productV1.getCondition());
                    }
                    if (productV1.getActive() != null) {
                        existingProduct.setActive(productV1.getActive());
                    }
                    if (productV1.getRegisterDate() != null) {
                        existingProduct.setRegisterDate(productV1.getRegisterDate());
                    }

                    return existingProduct;
                }
            )
            .map(productRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductV1> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductV1> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
