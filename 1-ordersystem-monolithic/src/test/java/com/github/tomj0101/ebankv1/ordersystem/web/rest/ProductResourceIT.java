package com.github.tomj0101.ebankv1.ordersystem.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.tomj0101.ebankv1.ordersystem.IntegrationTest;
import com.github.tomj0101.ebankv1.ordersystem.domain.ProductV1;
import com.github.tomj0101.ebankv1.ordersystem.domain.enumeration.Condition;
import com.github.tomj0101.ebankv1.ordersystem.repository.ProductRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
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

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private ProductV1 productV1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductV1 createEntity(EntityManager em) {
        ProductV1 productV1 = new ProductV1()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .productImages(DEFAULT_PRODUCT_IMAGES)
            .productImagesContentType(DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE)
            .price(DEFAULT_PRICE)
            .condition(DEFAULT_CONDITION)
            .active(DEFAULT_ACTIVE)
            .registerDate(DEFAULT_REGISTER_DATE);
        return productV1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductV1 createUpdatedEntity(EntityManager em) {
        ProductV1 productV1 = new ProductV1()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .productImages(UPDATED_PRODUCT_IMAGES)
            .productImagesContentType(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE)
            .price(UPDATED_PRICE)
            .condition(UPDATED_CONDITION)
            .active(UPDATED_ACTIVE)
            .registerDate(UPDATED_REGISTER_DATE);
        return productV1;
    }

    @BeforeEach
    public void initTest() {
        productV1 = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        ProductV1 testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getProductImages()).isEqualTo(DEFAULT_PRODUCT_IMAGES);
        assertThat(testProduct.getProductImagesContentType()).isEqualTo(DEFAULT_PRODUCT_IMAGES_CONTENT_TYPE);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testProduct.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testProduct.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        productV1.setId(1L);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        productV1.setName(null);

        // Create the Product, which fails.

        restProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isBadRequest());

        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(productV1);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productV1.getId().intValue())))
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
        productRepository.saveAndFlush(productV1);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, productV1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productV1.getId().intValue()))
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
        productRepository.saveAndFlush(productV1);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        ProductV1 updatedProductV1 = productRepository.findById(productV1.getId()).get();
        // Disconnect from session so that the updates on updatedProductV1 are not directly saved in db
        em.detach(updatedProductV1);
        updatedProductV1
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
                put(ENTITY_API_URL_ID, updatedProductV1.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductV1))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        ProductV1 testProduct = productList.get(productList.size() - 1);
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
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        productV1.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productV1.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        productV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        productV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(productV1);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        ProductV1 partialUpdatedProductV1 = new ProductV1();
        partialUpdatedProductV1.setId(productV1.getId());

        partialUpdatedProductV1
            .productImages(UPDATED_PRODUCT_IMAGES)
            .productImagesContentType(UPDATED_PRODUCT_IMAGES_CONTENT_TYPE)
            .condition(UPDATED_CONDITION)
            .active(UPDATED_ACTIVE);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductV1.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductV1))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        ProductV1 testProduct = productList.get(productList.size() - 1);
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
        productRepository.saveAndFlush(productV1);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        ProductV1 partialUpdatedProductV1 = new ProductV1();
        partialUpdatedProductV1.setId(productV1.getId());

        partialUpdatedProductV1
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
                patch(ENTITY_API_URL_ID, partialUpdatedProductV1.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductV1))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        ProductV1 testProduct = productList.get(productList.size() - 1);
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
        productV1.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productV1.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        productV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        productV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productV1))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(productV1);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, productV1.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductV1> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
