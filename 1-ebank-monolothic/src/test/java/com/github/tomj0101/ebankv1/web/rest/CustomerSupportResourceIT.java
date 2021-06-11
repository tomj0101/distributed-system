package com.github.tomj0101.ebankv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.tomj0101.ebankv1.IntegrationTest;
import com.github.tomj0101.ebankv1.domain.CustomerSupport;
import com.github.tomj0101.ebankv1.domain.enumeration.SupportSeverity;
import com.github.tomj0101.ebankv1.domain.enumeration.SupportStatus;
import com.github.tomj0101.ebankv1.repository.CustomerSupportRepository;
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
 * Integration tests for the {@link CustomerSupportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerSupportResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_C_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_C_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SupportSeverity DEFAULT_SEVERITY = SupportSeverity.LOW;
    private static final SupportSeverity UPDATED_SEVERITY = SupportSeverity.MEDIUM;

    private static final SupportStatus DEFAULT_STATUS = SupportStatus.OPEN;
    private static final SupportStatus UPDATED_STATUS = SupportStatus.INPROGRESS;

    private static final String ENTITY_API_URL = "/api/customer-supports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerSupportRepository customerSupportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerSupportMockMvc;

    private CustomerSupport customerSupport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerSupport createEntity(EntityManager em) {
        CustomerSupport customerSupport = new CustomerSupport()
            .description(DEFAULT_DESCRIPTION)
            .cCreated(DEFAULT_C_CREATED)
            .severity(DEFAULT_SEVERITY)
            .status(DEFAULT_STATUS);
        return customerSupport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerSupport createUpdatedEntity(EntityManager em) {
        CustomerSupport customerSupport = new CustomerSupport()
            .description(UPDATED_DESCRIPTION)
            .cCreated(UPDATED_C_CREATED)
            .severity(UPDATED_SEVERITY)
            .status(UPDATED_STATUS);
        return customerSupport;
    }

    @BeforeEach
    public void initTest() {
        customerSupport = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerSupport() throws Exception {
        int databaseSizeBeforeCreate = customerSupportRepository.findAll().size();
        // Create the CustomerSupport
        restCustomerSupportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isCreated());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerSupport testCustomerSupport = customerSupportList.get(customerSupportList.size() - 1);
        assertThat(testCustomerSupport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomerSupport.getcCreated()).isEqualTo(DEFAULT_C_CREATED);
        assertThat(testCustomerSupport.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testCustomerSupport.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createCustomerSupportWithExistingId() throws Exception {
        // Create the CustomerSupport with an existing ID
        customerSupport.setId(1L);

        int databaseSizeBeforeCreate = customerSupportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerSupportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSupportRepository.findAll().size();
        // set the field null
        customerSupport.setDescription(null);

        // Create the CustomerSupport, which fails.

        restCustomerSupportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isBadRequest());

        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkcCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSupportRepository.findAll().size();
        // set the field null
        customerSupport.setcCreated(null);

        // Create the CustomerSupport, which fails.

        restCustomerSupportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isBadRequest());

        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomerSupports() throws Exception {
        // Initialize the database
        customerSupportRepository.saveAndFlush(customerSupport);

        // Get all the customerSupportList
        restCustomerSupportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerSupport.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].cCreated").value(hasItem(DEFAULT_C_CREATED.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getCustomerSupport() throws Exception {
        // Initialize the database
        customerSupportRepository.saveAndFlush(customerSupport);

        // Get the customerSupport
        restCustomerSupportMockMvc
            .perform(get(ENTITY_API_URL_ID, customerSupport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerSupport.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.cCreated").value(DEFAULT_C_CREATED.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCustomerSupport() throws Exception {
        // Get the customerSupport
        restCustomerSupportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomerSupport() throws Exception {
        // Initialize the database
        customerSupportRepository.saveAndFlush(customerSupport);

        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();

        // Update the customerSupport
        CustomerSupport updatedCustomerSupport = customerSupportRepository.findById(customerSupport.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerSupport are not directly saved in db
        em.detach(updatedCustomerSupport);
        updatedCustomerSupport
            .description(UPDATED_DESCRIPTION)
            .cCreated(UPDATED_C_CREATED)
            .severity(UPDATED_SEVERITY)
            .status(UPDATED_STATUS);

        restCustomerSupportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomerSupport.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerSupport))
            )
            .andExpect(status().isOk());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
        CustomerSupport testCustomerSupport = customerSupportList.get(customerSupportList.size() - 1);
        assertThat(testCustomerSupport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomerSupport.getcCreated()).isEqualTo(UPDATED_C_CREATED);
        assertThat(testCustomerSupport.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testCustomerSupport.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCustomerSupport() throws Exception {
        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();
        customerSupport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerSupportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerSupport.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerSupport() throws Exception {
        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();
        customerSupport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerSupportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerSupport() throws Exception {
        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();
        customerSupport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerSupportMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerSupportWithPatch() throws Exception {
        // Initialize the database
        customerSupportRepository.saveAndFlush(customerSupport);

        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();

        // Update the customerSupport using partial update
        CustomerSupport partialUpdatedCustomerSupport = new CustomerSupport();
        partialUpdatedCustomerSupport.setId(customerSupport.getId());

        partialUpdatedCustomerSupport.cCreated(UPDATED_C_CREATED).severity(UPDATED_SEVERITY);

        restCustomerSupportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerSupport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerSupport))
            )
            .andExpect(status().isOk());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
        CustomerSupport testCustomerSupport = customerSupportList.get(customerSupportList.size() - 1);
        assertThat(testCustomerSupport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomerSupport.getcCreated()).isEqualTo(UPDATED_C_CREATED);
        assertThat(testCustomerSupport.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testCustomerSupport.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCustomerSupportWithPatch() throws Exception {
        // Initialize the database
        customerSupportRepository.saveAndFlush(customerSupport);

        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();

        // Update the customerSupport using partial update
        CustomerSupport partialUpdatedCustomerSupport = new CustomerSupport();
        partialUpdatedCustomerSupport.setId(customerSupport.getId());

        partialUpdatedCustomerSupport
            .description(UPDATED_DESCRIPTION)
            .cCreated(UPDATED_C_CREATED)
            .severity(UPDATED_SEVERITY)
            .status(UPDATED_STATUS);

        restCustomerSupportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerSupport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerSupport))
            )
            .andExpect(status().isOk());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
        CustomerSupport testCustomerSupport = customerSupportList.get(customerSupportList.size() - 1);
        assertThat(testCustomerSupport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomerSupport.getcCreated()).isEqualTo(UPDATED_C_CREATED);
        assertThat(testCustomerSupport.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testCustomerSupport.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerSupport() throws Exception {
        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();
        customerSupport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerSupportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerSupport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerSupport() throws Exception {
        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();
        customerSupport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerSupportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerSupport() throws Exception {
        int databaseSizeBeforeUpdate = customerSupportRepository.findAll().size();
        customerSupport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerSupportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerSupport))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerSupport in the database
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerSupport() throws Exception {
        // Initialize the database
        customerSupportRepository.saveAndFlush(customerSupport);

        int databaseSizeBeforeDelete = customerSupportRepository.findAll().size();

        // Delete the customerSupport
        restCustomerSupportMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerSupport.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerSupport> customerSupportList = customerSupportRepository.findAll();
        assertThat(customerSupportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
