package ke.natujenge.alumni.portal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ke.natujenge.alumni.portal.IntegrationTest;
import ke.natujenge.alumni.portal.domain.Association;
import ke.natujenge.alumni.portal.domain.enumeration.AssociationType;
import ke.natujenge.alumni.portal.repository.AssociationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AssociationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssociationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRIEF = "AAAAAAAAAA";
    private static final String UPDATED_BRIEF = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYBILL = "AAAAAAAAAA";
    private static final String UPDATED_PAYBILL = "BBBBBBBBBB";

    private static final AssociationType DEFAULT_ASSOCIATION_TYPE = AssociationType.PRIMARYSCHOOL;
    private static final AssociationType UPDATED_ASSOCIATION_TYPE = AssociationType.HIGHSCHOOL;

    private static final String DEFAULT_CONFIGS = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/associations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssociationMockMvc;

    private Association association;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Association createEntity(EntityManager em) {
        Association association = new Association()
            .name(DEFAULT_NAME)
            .brief(DEFAULT_BRIEF)
            .description(DEFAULT_DESCRIPTION)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .contentStatus(DEFAULT_CONTENT_STATUS)
            .paybill(DEFAULT_PAYBILL)
            .associationType(DEFAULT_ASSOCIATION_TYPE)
            .configs(DEFAULT_CONFIGS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return association;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Association createUpdatedEntity(EntityManager em) {
        Association association = new Association()
            .name(UPDATED_NAME)
            .brief(UPDATED_BRIEF)
            .description(UPDATED_DESCRIPTION)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .contentStatus(UPDATED_CONTENT_STATUS)
            .paybill(UPDATED_PAYBILL)
            .associationType(UPDATED_ASSOCIATION_TYPE)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return association;
    }

    @BeforeEach
    public void initTest() {
        association = createEntity(em);
    }

    @Test
    @Transactional
    void createAssociation() throws Exception {
        int databaseSizeBeforeCreate = associationRepository.findAll().size();
        // Create the Association
        restAssociationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(association)))
            .andExpect(status().isCreated());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeCreate + 1);
        Association testAssociation = associationList.get(associationList.size() - 1);
        assertThat(testAssociation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssociation.getBrief()).isEqualTo(DEFAULT_BRIEF);
        assertThat(testAssociation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssociation.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAssociation.getContentStatus()).isEqualTo(DEFAULT_CONTENT_STATUS);
        assertThat(testAssociation.getPaybill()).isEqualTo(DEFAULT_PAYBILL);
        assertThat(testAssociation.getAssociationType()).isEqualTo(DEFAULT_ASSOCIATION_TYPE);
        assertThat(testAssociation.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testAssociation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAssociation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAssociation.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testAssociation.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createAssociationWithExistingId() throws Exception {
        // Create the Association with an existing ID
        association.setId(1L);

        int databaseSizeBeforeCreate = associationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssociationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(association)))
            .andExpect(status().isBadRequest());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssociations() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        // Get all the associationList
        restAssociationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(association.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].brief").value(hasItem(DEFAULT_BRIEF)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].contentStatus").value(hasItem(DEFAULT_CONTENT_STATUS)))
            .andExpect(jsonPath("$.[*].paybill").value(hasItem(DEFAULT_PAYBILL)))
            .andExpect(jsonPath("$.[*].associationType").value(hasItem(DEFAULT_ASSOCIATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].configs").value(hasItem(DEFAULT_CONFIGS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getAssociation() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        // Get the association
        restAssociationMockMvc
            .perform(get(ENTITY_API_URL_ID, association.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(association.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.brief").value(DEFAULT_BRIEF))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.contentStatus").value(DEFAULT_CONTENT_STATUS))
            .andExpect(jsonPath("$.paybill").value(DEFAULT_PAYBILL))
            .andExpect(jsonPath("$.associationType").value(DEFAULT_ASSOCIATION_TYPE.toString()))
            .andExpect(jsonPath("$.configs").value(DEFAULT_CONFIGS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingAssociation() throws Exception {
        // Get the association
        restAssociationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssociation() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        int databaseSizeBeforeUpdate = associationRepository.findAll().size();

        // Update the association
        Association updatedAssociation = associationRepository.findById(association.getId()).get();
        // Disconnect from session so that the updates on updatedAssociation are not directly saved in db
        em.detach(updatedAssociation);
        updatedAssociation
            .name(UPDATED_NAME)
            .brief(UPDATED_BRIEF)
            .description(UPDATED_DESCRIPTION)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .contentStatus(UPDATED_CONTENT_STATUS)
            .paybill(UPDATED_PAYBILL)
            .associationType(UPDATED_ASSOCIATION_TYPE)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssociation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAssociation))
            )
            .andExpect(status().isOk());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
        Association testAssociation = associationList.get(associationList.size() - 1);
        assertThat(testAssociation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssociation.getBrief()).isEqualTo(UPDATED_BRIEF);
        assertThat(testAssociation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssociation.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAssociation.getContentStatus()).isEqualTo(UPDATED_CONTENT_STATUS);
        assertThat(testAssociation.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testAssociation.getAssociationType()).isEqualTo(UPDATED_ASSOCIATION_TYPE);
        assertThat(testAssociation.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testAssociation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssociation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAssociation.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testAssociation.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAssociation() throws Exception {
        int databaseSizeBeforeUpdate = associationRepository.findAll().size();
        association.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, association.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(association))
            )
            .andExpect(status().isBadRequest());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssociation() throws Exception {
        int databaseSizeBeforeUpdate = associationRepository.findAll().size();
        association.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(association))
            )
            .andExpect(status().isBadRequest());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssociation() throws Exception {
        int databaseSizeBeforeUpdate = associationRepository.findAll().size();
        association.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(association)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssociationWithPatch() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        int databaseSizeBeforeUpdate = associationRepository.findAll().size();

        // Update the association using partial update
        Association partialUpdatedAssociation = new Association();
        partialUpdatedAssociation.setId(association.getId());

        partialUpdatedAssociation
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .paybill(UPDATED_PAYBILL)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);

        restAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssociation))
            )
            .andExpect(status().isOk());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
        Association testAssociation = associationList.get(associationList.size() - 1);
        assertThat(testAssociation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssociation.getBrief()).isEqualTo(DEFAULT_BRIEF);
        assertThat(testAssociation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssociation.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAssociation.getContentStatus()).isEqualTo(DEFAULT_CONTENT_STATUS);
        assertThat(testAssociation.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testAssociation.getAssociationType()).isEqualTo(DEFAULT_ASSOCIATION_TYPE);
        assertThat(testAssociation.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testAssociation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssociation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAssociation.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testAssociation.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAssociationWithPatch() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        int databaseSizeBeforeUpdate = associationRepository.findAll().size();

        // Update the association using partial update
        Association partialUpdatedAssociation = new Association();
        partialUpdatedAssociation.setId(association.getId());

        partialUpdatedAssociation
            .name(UPDATED_NAME)
            .brief(UPDATED_BRIEF)
            .description(UPDATED_DESCRIPTION)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .contentStatus(UPDATED_CONTENT_STATUS)
            .paybill(UPDATED_PAYBILL)
            .associationType(UPDATED_ASSOCIATION_TYPE)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssociation))
            )
            .andExpect(status().isOk());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
        Association testAssociation = associationList.get(associationList.size() - 1);
        assertThat(testAssociation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssociation.getBrief()).isEqualTo(UPDATED_BRIEF);
        assertThat(testAssociation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssociation.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAssociation.getContentStatus()).isEqualTo(UPDATED_CONTENT_STATUS);
        assertThat(testAssociation.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testAssociation.getAssociationType()).isEqualTo(UPDATED_ASSOCIATION_TYPE);
        assertThat(testAssociation.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testAssociation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssociation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAssociation.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testAssociation.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAssociation() throws Exception {
        int databaseSizeBeforeUpdate = associationRepository.findAll().size();
        association.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, association.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(association))
            )
            .andExpect(status().isBadRequest());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssociation() throws Exception {
        int databaseSizeBeforeUpdate = associationRepository.findAll().size();
        association.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(association))
            )
            .andExpect(status().isBadRequest());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssociation() throws Exception {
        int databaseSizeBeforeUpdate = associationRepository.findAll().size();
        association.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(association))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Association in the database
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssociation() throws Exception {
        // Initialize the database
        associationRepository.saveAndFlush(association);

        int databaseSizeBeforeDelete = associationRepository.findAll().size();

        // Delete the association
        restAssociationMockMvc
            .perform(delete(ENTITY_API_URL_ID, association.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Association> associationList = associationRepository.findAll();
        assertThat(associationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
