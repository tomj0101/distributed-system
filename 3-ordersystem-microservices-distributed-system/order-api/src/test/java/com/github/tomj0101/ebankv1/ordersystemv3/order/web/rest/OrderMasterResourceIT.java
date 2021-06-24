package com.github.tomj0101.ebankv1.ordersystemv3.order.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.tomj0101.ebankv1.ordersystemv3.order.IntegrationTest;
import com.github.tomj0101.ebankv1.ordersystemv3.order.domain.OrderMaster;
import com.github.tomj0101.ebankv1.ordersystemv3.order.repository.OrderMasterRepository;
import com.github.tomj0101.ebankv1.ordersystemv3.order.repository.search.OrderMasterSearchRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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

/**
 * Integration tests for the {@link OrderMasterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrderMasterResourceIT {

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final String DEFAULT_EMAIL = "Ye@,C.)uzt";
    private static final String UPDATED_EMAIL = "(V%p@HqTGE._yHy";

    private static final Instant DEFAULT_REGISTER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/order-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/order-masters";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /**
     * This repository is mocked in the com.github.tomj0101.ebankv1.ordersystemv3.order.repository.search test package.
     *
     * @see com.github.tomj0101.ebankv1.ordersystemv3.order.repository.search.OrderMasterSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrderMasterSearchRepository mockOrderMasterSearchRepository;

    @Autowired
    private MockMvc restOrderMasterMockMvc;

    private OrderMaster orderMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderMaster createEntity() {
        OrderMaster orderMaster = new OrderMaster()
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .total(DEFAULT_TOTAL)
            .email(DEFAULT_EMAIL)
            .registerDate(DEFAULT_REGISTER_DATE);
        return orderMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderMaster createUpdatedEntity() {
        OrderMaster orderMaster = new OrderMaster()
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .total(UPDATED_TOTAL)
            .email(UPDATED_EMAIL)
            .registerDate(UPDATED_REGISTER_DATE);
        return orderMaster;
    }

    @BeforeEach
    public void initTest() {
        orderMasterRepository.deleteAll();
        orderMaster = createEntity();
    }

    @Test
    void createOrderMaster() throws Exception {
        int databaseSizeBeforeCreate = orderMasterRepository.findAll().size();
        // Create the OrderMaster
        restOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderMaster)))
            .andExpect(status().isCreated());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeCreate + 1);
        OrderMaster testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(1)).save(testOrderMaster);
    }

    @Test
    void createOrderMasterWithExistingId() throws Exception {
        // Create the OrderMaster with an existing ID
        orderMaster.setId(1L);

        int databaseSizeBeforeCreate = orderMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderMaster)))
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(0)).save(orderMaster);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderMasterRepository.findAll().size();
        // set the field null
        orderMaster.setEmail(null);

        // Create the OrderMaster, which fails.

        restOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderMaster)))
            .andExpect(status().isBadRequest());

        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllOrderMasters() throws Exception {
        // Initialize the database
        orderMasterRepository.save(orderMaster);

        // Get all the orderMasterList
        restOrderMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())));
    }

    @Test
    void getOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.save(orderMaster);

        // Get the orderMaster
        restOrderMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, orderMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderMaster.getId().intValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.registerDate").value(DEFAULT_REGISTER_DATE.toString()));
    }

    @Test
    void getNonExistingOrderMaster() throws Exception {
        // Get the orderMaster
        restOrderMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.save(orderMaster);

        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Update the orderMaster
        OrderMaster updatedOrderMaster = orderMasterRepository.findById(orderMaster.getId()).get();
        updatedOrderMaster
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .total(UPDATED_TOTAL)
            .email(UPDATED_EMAIL)
            .registerDate(UPDATED_REGISTER_DATE);

        restOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderMaster))
            )
            .andExpect(status().isOk());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
        OrderMaster testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository).save(testOrderMaster);
    }

    @Test
    void putNonExistingOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(0)).save(orderMaster);
    }

    @Test
    void putWithIdMismatchOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(0)).save(orderMaster);
    }

    @Test
    void putWithMissingIdPathParamOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(0)).save(orderMaster);
    }

    @Test
    void partialUpdateOrderMasterWithPatch() throws Exception {
        // Initialize the database
        orderMasterRepository.save(orderMaster);

        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Update the orderMaster using partial update
        OrderMaster partialUpdatedOrderMaster = new OrderMaster();
        partialUpdatedOrderMaster.setId(orderMaster.getId());

        partialUpdatedOrderMaster.total(UPDATED_TOTAL).email(UPDATED_EMAIL);

        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderMaster))
            )
            .andExpect(status().isOk());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
        OrderMaster testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
    }

    @Test
    void fullUpdateOrderMasterWithPatch() throws Exception {
        // Initialize the database
        orderMasterRepository.save(orderMaster);

        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Update the orderMaster using partial update
        OrderMaster partialUpdatedOrderMaster = new OrderMaster();
        partialUpdatedOrderMaster.setId(orderMaster.getId());

        partialUpdatedOrderMaster
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .total(UPDATED_TOTAL)
            .email(UPDATED_EMAIL)
            .registerDate(UPDATED_REGISTER_DATE);

        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderMaster))
            )
            .andExpect(status().isOk());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
        OrderMaster testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
    }

    @Test
    void patchNonExistingOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(0)).save(orderMaster);
    }

    @Test
    void patchWithIdMismatchOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(0)).save(orderMaster);
    }

    @Test
    void patchWithMissingIdPathParamOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderMaster in the database
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(0)).save(orderMaster);
    }

    @Test
    void deleteOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.save(orderMaster);

        int databaseSizeBeforeDelete = orderMasterRepository.findAll().size();

        // Delete the orderMaster
        restOrderMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderMaster> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrderMaster in Elasticsearch
        verify(mockOrderMasterSearchRepository, times(1)).deleteById(orderMaster.getId());
    }

    @Test
    void searchOrderMaster() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        orderMasterRepository.save(orderMaster);
        when(mockOrderMasterSearchRepository.search(queryStringQuery("id:" + orderMaster.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(orderMaster), PageRequest.of(0, 1), 1));

        // Search the orderMaster
        restOrderMasterMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + orderMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())));
    }
}
