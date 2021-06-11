package com.github.tomj0101.ebankv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.tomj0101.ebankv1.IntegrationTest;
import com.github.tomj0101.ebankv1.domain.RecentTransaction;
import com.github.tomj0101.ebankv1.domain.enumeration.TransactionStatus;
import com.github.tomj0101.ebankv1.repository.RecentTransactionRepository;
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
 * Integration tests for the {@link RecentTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecentTransactionResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final TransactionStatus DEFAULT_STATUS = TransactionStatus.PENDING;
    private static final TransactionStatus UPDATED_STATUS = TransactionStatus.COMPLETED;

    private static final Instant DEFAULT_T_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_T_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/recent-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecentTransactionRepository recentTransactionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecentTransactionMockMvc;

    private RecentTransaction recentTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecentTransaction createEntity(EntityManager em) {
        RecentTransaction recentTransaction = new RecentTransaction()
            .description(DEFAULT_DESCRIPTION)
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS)
            .tCreated(DEFAULT_T_CREATED);
        return recentTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecentTransaction createUpdatedEntity(EntityManager em) {
        RecentTransaction recentTransaction = new RecentTransaction()
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .tCreated(UPDATED_T_CREATED);
        return recentTransaction;
    }

    @BeforeEach
    public void initTest() {
        recentTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createRecentTransaction() throws Exception {
        int databaseSizeBeforeCreate = recentTransactionRepository.findAll().size();
        // Create the RecentTransaction
        restRecentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isCreated());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        RecentTransaction testRecentTransaction = recentTransactionList.get(recentTransactionList.size() - 1);
        assertThat(testRecentTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecentTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRecentTransaction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRecentTransaction.gettCreated()).isEqualTo(DEFAULT_T_CREATED);
    }

    @Test
    @Transactional
    void createRecentTransactionWithExistingId() throws Exception {
        // Create the RecentTransaction with an existing ID
        recentTransaction.setId(1L);

        int databaseSizeBeforeCreate = recentTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = recentTransactionRepository.findAll().size();
        // set the field null
        recentTransaction.setDescription(null);

        // Create the RecentTransaction, which fails.

        restRecentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = recentTransactionRepository.findAll().size();
        // set the field null
        recentTransaction.setAmount(null);

        // Create the RecentTransaction, which fails.

        restRecentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = recentTransactionRepository.findAll().size();
        // set the field null
        recentTransaction.setStatus(null);

        // Create the RecentTransaction, which fails.

        restRecentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checktCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = recentTransactionRepository.findAll().size();
        // set the field null
        recentTransaction.settCreated(null);

        // Create the RecentTransaction, which fails.

        restRecentTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecentTransactions() throws Exception {
        // Initialize the database
        recentTransactionRepository.saveAndFlush(recentTransaction);

        // Get all the recentTransactionList
        restRecentTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recentTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tCreated").value(hasItem(DEFAULT_T_CREATED.toString())));
    }

    @Test
    @Transactional
    void getRecentTransaction() throws Exception {
        // Initialize the database
        recentTransactionRepository.saveAndFlush(recentTransaction);

        // Get the recentTransaction
        restRecentTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, recentTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recentTransaction.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.tCreated").value(DEFAULT_T_CREATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRecentTransaction() throws Exception {
        // Get the recentTransaction
        restRecentTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRecentTransaction() throws Exception {
        // Initialize the database
        recentTransactionRepository.saveAndFlush(recentTransaction);

        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();

        // Update the recentTransaction
        RecentTransaction updatedRecentTransaction = recentTransactionRepository.findById(recentTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedRecentTransaction are not directly saved in db
        em.detach(updatedRecentTransaction);
        updatedRecentTransaction.description(UPDATED_DESCRIPTION).amount(UPDATED_AMOUNT).status(UPDATED_STATUS).tCreated(UPDATED_T_CREATED);

        restRecentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecentTransaction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecentTransaction))
            )
            .andExpect(status().isOk());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
        RecentTransaction testRecentTransaction = recentTransactionList.get(recentTransactionList.size() - 1);
        assertThat(testRecentTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecentTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRecentTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecentTransaction.gettCreated()).isEqualTo(UPDATED_T_CREATED);
    }

    @Test
    @Transactional
    void putNonExistingRecentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();
        recentTransaction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recentTransaction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();
        recentTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();
        recentTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecentTransactionWithPatch() throws Exception {
        // Initialize the database
        recentTransactionRepository.saveAndFlush(recentTransaction);

        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();

        // Update the recentTransaction using partial update
        RecentTransaction partialUpdatedRecentTransaction = new RecentTransaction();
        partialUpdatedRecentTransaction.setId(recentTransaction.getId());

        partialUpdatedRecentTransaction.status(UPDATED_STATUS).tCreated(UPDATED_T_CREATED);

        restRecentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecentTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecentTransaction))
            )
            .andExpect(status().isOk());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
        RecentTransaction testRecentTransaction = recentTransactionList.get(recentTransactionList.size() - 1);
        assertThat(testRecentTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecentTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRecentTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecentTransaction.gettCreated()).isEqualTo(UPDATED_T_CREATED);
    }

    @Test
    @Transactional
    void fullUpdateRecentTransactionWithPatch() throws Exception {
        // Initialize the database
        recentTransactionRepository.saveAndFlush(recentTransaction);

        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();

        // Update the recentTransaction using partial update
        RecentTransaction partialUpdatedRecentTransaction = new RecentTransaction();
        partialUpdatedRecentTransaction.setId(recentTransaction.getId());

        partialUpdatedRecentTransaction
            .description(UPDATED_DESCRIPTION)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .tCreated(UPDATED_T_CREATED);

        restRecentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecentTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecentTransaction))
            )
            .andExpect(status().isOk());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
        RecentTransaction testRecentTransaction = recentTransactionList.get(recentTransactionList.size() - 1);
        assertThat(testRecentTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecentTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRecentTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecentTransaction.gettCreated()).isEqualTo(UPDATED_T_CREATED);
    }

    @Test
    @Transactional
    void patchNonExistingRecentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();
        recentTransaction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recentTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();
        recentTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecentTransaction() throws Exception {
        int databaseSizeBeforeUpdate = recentTransactionRepository.findAll().size();
        recentTransaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecentTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recentTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecentTransaction in the database
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecentTransaction() throws Exception {
        // Initialize the database
        recentTransactionRepository.saveAndFlush(recentTransaction);

        int databaseSizeBeforeDelete = recentTransactionRepository.findAll().size();

        // Delete the recentTransaction
        restRecentTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, recentTransaction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecentTransaction> recentTransactionList = recentTransactionRepository.findAll();
        assertThat(recentTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
