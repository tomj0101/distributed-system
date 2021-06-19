package com.github.tomj0101.ebankv1.ordersystem.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.tomj0101.ebankv1.ordersystem.IntegrationTest;
import com.github.tomj0101.ebankv1.ordersystem.domain.OrderMasterV1;
import com.github.tomj0101.ebankv1.ordersystem.repository.OrderMasterRepository;
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

/**
 * Integration tests for the {@link OrderMasterResource} REST controller.
 */
@IntegrationTest
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

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderMasterMockMvc;

    private OrderMasterV1 orderMasterV1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderMasterV1 createEntity(EntityManager em) {
        OrderMasterV1 orderMasterV1 = new OrderMasterV1()
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .total(DEFAULT_TOTAL)
            .email(DEFAULT_EMAIL)
            .registerDate(DEFAULT_REGISTER_DATE);
        return orderMasterV1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderMasterV1 createUpdatedEntity(EntityManager em) {
        OrderMasterV1 orderMasterV1 = new OrderMasterV1()
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .total(UPDATED_TOTAL)
            .email(UPDATED_EMAIL)
            .registerDate(UPDATED_REGISTER_DATE);
        return orderMasterV1;
    }

    @BeforeEach
    public void initTest() {
        orderMasterV1 = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderMaster() throws Exception {
        int databaseSizeBeforeCreate = orderMasterRepository.findAll().size();
        // Create the OrderMaster
        restOrderMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isCreated());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeCreate + 1);
        OrderMasterV1 testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
    }

    @Test
    @Transactional
    void createOrderMasterWithExistingId() throws Exception {
        // Create the OrderMaster with an existing ID
        orderMasterV1.setId(1L);

        int databaseSizeBeforeCreate = orderMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderMasterRepository.findAll().size();
        // set the field null
        orderMasterV1.setEmail(null);

        // Create the OrderMaster, which fails.

        restOrderMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isBadRequest());

        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderMasters() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMasterV1);

        // Get all the orderMasterList
        restOrderMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderMasterV1.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())));
    }

    @Test
    @Transactional
    void getOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMasterV1);

        // Get the orderMaster
        restOrderMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, orderMasterV1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderMasterV1.getId().intValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.registerDate").value(DEFAULT_REGISTER_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOrderMaster() throws Exception {
        // Get the orderMaster
        restOrderMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMasterV1);

        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Update the orderMaster
        OrderMasterV1 updatedOrderMasterV1 = orderMasterRepository.findById(orderMasterV1.getId()).get();
        // Disconnect from session so that the updates on updatedOrderMasterV1 are not directly saved in db
        em.detach(updatedOrderMasterV1);
        updatedOrderMasterV1
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .total(UPDATED_TOTAL)
            .email(UPDATED_EMAIL)
            .registerDate(UPDATED_REGISTER_DATE);

        restOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrderMasterV1.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOrderMasterV1))
            )
            .andExpect(status().isOk());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
        OrderMasterV1 testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMasterV1.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderMasterV1.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMasterV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMasterV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderMasterWithPatch() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMasterV1);

        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Update the orderMaster using partial update
        OrderMasterV1 partialUpdatedOrderMasterV1 = new OrderMasterV1();
        partialUpdatedOrderMasterV1.setId(orderMasterV1.getId());

        partialUpdatedOrderMasterV1.total(UPDATED_TOTAL).email(UPDATED_EMAIL);

        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderMasterV1.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderMasterV1))
            )
            .andExpect(status().isOk());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
        OrderMasterV1 testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrderMasterWithPatch() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMasterV1);

        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();

        // Update the orderMaster using partial update
        OrderMasterV1 partialUpdatedOrderMasterV1 = new OrderMasterV1();
        partialUpdatedOrderMasterV1.setId(orderMasterV1.getId());

        partialUpdatedOrderMasterV1
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .total(UPDATED_TOTAL)
            .email(UPDATED_EMAIL)
            .registerDate(UPDATED_REGISTER_DATE);

        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderMasterV1.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderMasterV1))
            )
            .andExpect(status().isOk());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
        OrderMasterV1 testOrderMaster = orderMasterList.get(orderMasterList.size() - 1);
        assertThat(testOrderMaster.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testOrderMaster.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrderMaster.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrderMaster.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMasterV1.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderMasterV1.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMasterV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderMaster() throws Exception {
        int databaseSizeBeforeUpdate = orderMasterRepository.findAll().size();
        orderMasterV1.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderMasterV1))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderMaster in the database
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderMaster() throws Exception {
        // Initialize the database
        orderMasterRepository.saveAndFlush(orderMasterV1);

        int databaseSizeBeforeDelete = orderMasterRepository.findAll().size();

        // Delete the orderMaster
        restOrderMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderMasterV1.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderMasterV1> orderMasterList = orderMasterRepository.findAll();
        assertThat(orderMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
