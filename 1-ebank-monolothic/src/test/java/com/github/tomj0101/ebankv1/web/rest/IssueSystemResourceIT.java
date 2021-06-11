package com.github.tomj0101.ebankv1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.tomj0101.ebankv1.IntegrationTest;
import com.github.tomj0101.ebankv1.domain.IssueSystem;
import com.github.tomj0101.ebankv1.repository.IssueSystemRepository;
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
 * Integration tests for the {@link IssueSystemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IssueSystemResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_I_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_I_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/issue-systems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IssueSystemRepository issueSystemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIssueSystemMockMvc;

    private IssueSystem issueSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueSystem createEntity(EntityManager em) {
        IssueSystem issueSystem = new IssueSystem().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION).iCreated(DEFAULT_I_CREATED);
        return issueSystem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IssueSystem createUpdatedEntity(EntityManager em) {
        IssueSystem issueSystem = new IssueSystem().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).iCreated(UPDATED_I_CREATED);
        return issueSystem;
    }

    @BeforeEach
    public void initTest() {
        issueSystem = createEntity(em);
    }

    @Test
    @Transactional
    void createIssueSystem() throws Exception {
        int databaseSizeBeforeCreate = issueSystemRepository.findAll().size();
        // Create the IssueSystem
        restIssueSystemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isCreated());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeCreate + 1);
        IssueSystem testIssueSystem = issueSystemList.get(issueSystemList.size() - 1);
        assertThat(testIssueSystem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIssueSystem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIssueSystem.getiCreated()).isEqualTo(DEFAULT_I_CREATED);
    }

    @Test
    @Transactional
    void createIssueSystemWithExistingId() throws Exception {
        // Create the IssueSystem with an existing ID
        issueSystem.setId(1L);

        int databaseSizeBeforeCreate = issueSystemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIssueSystemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIssueSystems() throws Exception {
        // Initialize the database
        issueSystemRepository.saveAndFlush(issueSystem);

        // Get all the issueSystemList
        restIssueSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(issueSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].iCreated").value(hasItem(DEFAULT_I_CREATED.toString())));
    }

    @Test
    @Transactional
    void getIssueSystem() throws Exception {
        // Initialize the database
        issueSystemRepository.saveAndFlush(issueSystem);

        // Get the issueSystem
        restIssueSystemMockMvc
            .perform(get(ENTITY_API_URL_ID, issueSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(issueSystem.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.iCreated").value(DEFAULT_I_CREATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIssueSystem() throws Exception {
        // Get the issueSystem
        restIssueSystemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIssueSystem() throws Exception {
        // Initialize the database
        issueSystemRepository.saveAndFlush(issueSystem);

        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();

        // Update the issueSystem
        IssueSystem updatedIssueSystem = issueSystemRepository.findById(issueSystem.getId()).get();
        // Disconnect from session so that the updates on updatedIssueSystem are not directly saved in db
        em.detach(updatedIssueSystem);
        updatedIssueSystem.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).iCreated(UPDATED_I_CREATED);

        restIssueSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIssueSystem.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIssueSystem))
            )
            .andExpect(status().isOk());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
        IssueSystem testIssueSystem = issueSystemList.get(issueSystemList.size() - 1);
        assertThat(testIssueSystem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIssueSystem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIssueSystem.getiCreated()).isEqualTo(UPDATED_I_CREATED);
    }

    @Test
    @Transactional
    void putNonExistingIssueSystem() throws Exception {
        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();
        issueSystem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, issueSystem.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIssueSystem() throws Exception {
        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();
        issueSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssueSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIssueSystem() throws Exception {
        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();
        issueSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssueSystemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIssueSystemWithPatch() throws Exception {
        // Initialize the database
        issueSystemRepository.saveAndFlush(issueSystem);

        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();

        // Update the issueSystem using partial update
        IssueSystem partialUpdatedIssueSystem = new IssueSystem();
        partialUpdatedIssueSystem.setId(issueSystem.getId());

        partialUpdatedIssueSystem.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).iCreated(UPDATED_I_CREATED);

        restIssueSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssueSystem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueSystem))
            )
            .andExpect(status().isOk());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
        IssueSystem testIssueSystem = issueSystemList.get(issueSystemList.size() - 1);
        assertThat(testIssueSystem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIssueSystem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIssueSystem.getiCreated()).isEqualTo(UPDATED_I_CREATED);
    }

    @Test
    @Transactional
    void fullUpdateIssueSystemWithPatch() throws Exception {
        // Initialize the database
        issueSystemRepository.saveAndFlush(issueSystem);

        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();

        // Update the issueSystem using partial update
        IssueSystem partialUpdatedIssueSystem = new IssueSystem();
        partialUpdatedIssueSystem.setId(issueSystem.getId());

        partialUpdatedIssueSystem.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).iCreated(UPDATED_I_CREATED);

        restIssueSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIssueSystem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIssueSystem))
            )
            .andExpect(status().isOk());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
        IssueSystem testIssueSystem = issueSystemList.get(issueSystemList.size() - 1);
        assertThat(testIssueSystem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIssueSystem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIssueSystem.getiCreated()).isEqualTo(UPDATED_I_CREATED);
    }

    @Test
    @Transactional
    void patchNonExistingIssueSystem() throws Exception {
        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();
        issueSystem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIssueSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, issueSystem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIssueSystem() throws Exception {
        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();
        issueSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssueSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isBadRequest());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIssueSystem() throws Exception {
        int databaseSizeBeforeUpdate = issueSystemRepository.findAll().size();
        issueSystem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIssueSystemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(issueSystem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IssueSystem in the database
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIssueSystem() throws Exception {
        // Initialize the database
        issueSystemRepository.saveAndFlush(issueSystem);

        int databaseSizeBeforeDelete = issueSystemRepository.findAll().size();

        // Delete the issueSystem
        restIssueSystemMockMvc
            .perform(delete(ENTITY_API_URL_ID, issueSystem.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IssueSystem> issueSystemList = issueSystemRepository.findAll();
        assertThat(issueSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
