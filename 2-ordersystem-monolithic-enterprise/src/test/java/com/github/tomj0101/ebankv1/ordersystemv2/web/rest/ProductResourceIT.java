package com.github.tomj0101.ebankv1.ordersystemv2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.tomj0101.ebankv1.ordersystemv2.IntegrationTest;
import com.github.tomj0101.ebankv1.ordersystemv2.domain.Product;
import com.github.tomj0101.ebankv1.ordersystemv2.domain.enumeration.Condition;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.ProductRepository;
import com.github.tomj0101.ebankv1.ordersystemv2.repository.search.ProductSearchRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PRODUCT_IMAGES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRODUCT_IMAGES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRODUCT_IMAGES_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Condition DEFAULT_CONDITION = Condition.NEW;
    private static final Condition UPDATED_CONDITION = Condition.USED;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_REGISTER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/products";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    /**
     * This repository is mocked in the com.github.tomj0101.ebankv1.ordersystemv2.repository.search test package.
     *
     * @see com.github.tomj0101.ebankv1.ordersystemv2.repository.search.ProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductSearchRepository mockProductSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .productImages(DEFAULT_PRODUCT_IMAGES)
            .productImagesContentType(DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE)
            .price(DEFAULT_PRICE)
            .condition(DEFAULT_CONDITION)
            .active(DEFAULT_ACTIVE)
            .registerDate(DEFAULT_REGISTER_DATE);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .productImages(UPDATED_PRODUCT_IMAGES)
            .productImagesContentType(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE)
            .price(UPDATED_PRICE)
            .condition(UPDATED_CONDITION)
            .active(UPDATED_ACTIVE)
            .registerDate(UPDATED_REGISTER_DATE);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getProductImages()).isEqualTo(DEFAULT_PRODUCT_IMAGES);
        assertThat(testProduct.getProductImagesContentType()).isEqualTo(DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testProduct.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testProduct.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setName(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].productImagesContentType").value(hasItem(DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productImages").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_IMAGES))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.productImagesContentType").value(DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE))
            .andExpect(jsonPath("$.productImages").value(Base64Utils.encodeToString(DEFAULT_PRODUCT_IMAGES)))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.registerDate").value(DEFAULT_REGISTER_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .productImages(UPDATED_PRODUCT_IMAGES)
            .productImagesContentType(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE)
            .price(UPDATED_PRICE)
            .condition(UPDATED_CONDITION)
            .active(UPDATED_ACTIVE)
            .registerDate(UPDATED_REGISTER_DATE);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getProductImages()).isEqualTo(UPDATED_PRODUCT_IMAGES);
        assertThat(testProduct.getProductImagesContentType()).isEqualTo(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testProduct.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProduct.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository).save(testProduct);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, product.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .productImages(UPDATED_PRODUCT_IMAGES)
            .productImagesContentType(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE)
            .condition(UPDATED_CONDITION)
            .active(UPDATED_ACTIVE);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getProductImages()).isEqualTo(UPDATED_PRODUCT_IMAGES);
        assertThat(testProduct.getProductImagesContentType()).isEqualTo(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testProduct.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProduct.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .productImages(UPDATED_PRODUCT_IMAGES)
            .productImagesContentType(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE)
            .price(UPDATED_PRICE)
            .condition(UPDATED_CONDITION)
            .active(UPDATED_ACTIVE)
            .registerDate(UPDATED_REGISTER_DATE);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getProductImages()).isEqualTo(UPDATED_PRODUCT_IMAGES);
        assertThat(testProduct.getProductImagesContentType()).isEqualTo(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testProduct.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProduct.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, product.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).deleteById(product.getId());
    }

    @Test
    @Transactional
    void searchProduct() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        productRepository.saveAndFlush(product);
        when(mockProductSearchRepository.search(queryStringQuery("id:" + product.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(product), PageRequest.of(0, 1), 1));

        // Search the product
        restProductMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].productImagesContentType").value(hasItem(DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].productImages").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRODUCT_IMAGES))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())));
    }
}
