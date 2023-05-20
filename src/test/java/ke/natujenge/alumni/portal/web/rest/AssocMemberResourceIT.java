package ke.natujenge.alumni.portal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ke.natujenge.alumni.portal.IntegrationTest;
import ke.natujenge.alumni.portal.domain.AssocMember;
import ke.natujenge.alumni.portal.domain.enumeration.Role;
import ke.natujenge.alumni.portal.repository.AssocMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AssocMemberResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssocMemberResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_REG_NO = "AAAAAAAAAA";
    private static final String UPDATED_REG_NO = "BBBBBBBBBB";

    private static final Role DEFAULT_ROLE = Role.ADMIN;
    private static final Role UPDATED_ROLE = Role.USER;

    private static final String DEFAULT_CONFIGS = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_ON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/assoc-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AssocMemberRepository assocMemberRepository;

    @Mock
    private AssocMemberRepository assocMemberRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssocMemberMockMvc;

    private AssocMember assocMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssocMember createEntity(EntityManager em) {
        AssocMember assocMember = new AssocMember()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .year(DEFAULT_YEAR)
            .regNo(DEFAULT_REG_NO)
            .role(DEFAULT_ROLE)
            .configs(DEFAULT_CONFIGS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return assocMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssocMember createUpdatedEntity(EntityManager em) {
        AssocMember assocMember = new AssocMember()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .year(UPDATED_YEAR)
            .regNo(UPDATED_REG_NO)
            .role(UPDATED_ROLE)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        return assocMember;
    }

    @BeforeEach
    public void initTest() {
        assocMember = createEntity(em);
    }

    @Test
    @Transactional
    void createAssocMember() throws Exception {
        int databaseSizeBeforeCreate = assocMemberRepository.findAll().size();
        // Create the AssocMember
        restAssocMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assocMember)))
            .andExpect(status().isCreated());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeCreate + 1);
        AssocMember testAssocMember = assocMemberList.get(assocMemberList.size() - 1);
        assertThat(testAssocMember.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssocMember.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAssocMember.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAssocMember.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAssocMember.getRegNo()).isEqualTo(DEFAULT_REG_NO);
        assertThat(testAssocMember.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testAssocMember.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testAssocMember.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAssocMember.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAssocMember.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testAssocMember.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    void createAssocMemberWithExistingId() throws Exception {
        // Create the AssocMember with an existing ID
        assocMember.setId(1L);

        int databaseSizeBeforeCreate = assocMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssocMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assocMember)))
            .andExpect(status().isBadRequest());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssocMembers() throws Exception {
        // Initialize the database
        assocMemberRepository.saveAndFlush(assocMember);

        // Get all the assocMemberList
        restAssocMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assocMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].regNo").value(hasItem(DEFAULT_REG_NO)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].configs").value(hasItem(DEFAULT_CONFIGS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssocMembersWithEagerRelationshipsIsEnabled() throws Exception {
        when(assocMemberRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssocMemberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assocMemberRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssocMembersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assocMemberRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssocMemberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(assocMemberRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAssocMember() throws Exception {
        // Initialize the database
        assocMemberRepository.saveAndFlush(assocMember);

        // Get the assocMember
        restAssocMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, assocMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assocMember.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.regNo").value(DEFAULT_REG_NO))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.configs").value(DEFAULT_CONFIGS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON));
    }

    @Test
    @Transactional
    void getNonExistingAssocMember() throws Exception {
        // Get the assocMember
        restAssocMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssocMember() throws Exception {
        // Initialize the database
        assocMemberRepository.saveAndFlush(assocMember);

        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();

        // Update the assocMember
        AssocMember updatedAssocMember = assocMemberRepository.findById(assocMember.getId()).get();
        // Disconnect from session so that the updates on updatedAssocMember are not directly saved in db
        em.detach(updatedAssocMember);
        updatedAssocMember
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .year(UPDATED_YEAR)
            .regNo(UPDATED_REG_NO)
            .role(UPDATED_ROLE)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);

        restAssocMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssocMember.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAssocMember))
            )
            .andExpect(status().isOk());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
        AssocMember testAssocMember = assocMemberList.get(assocMemberList.size() - 1);
        assertThat(testAssocMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssocMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAssocMember.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAssocMember.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAssocMember.getRegNo()).isEqualTo(UPDATED_REG_NO);
        assertThat(testAssocMember.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testAssocMember.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testAssocMember.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssocMember.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAssocMember.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testAssocMember.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingAssocMember() throws Exception {
        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();
        assocMember.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssocMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assocMember.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assocMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssocMember() throws Exception {
        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();
        assocMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssocMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assocMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssocMember() throws Exception {
        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();
        assocMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssocMemberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assocMember)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssocMemberWithPatch() throws Exception {
        // Initialize the database
        assocMemberRepository.saveAndFlush(assocMember);

        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();

        // Update the assocMember using partial update
        AssocMember partialUpdatedAssocMember = new AssocMember();
        partialUpdatedAssocMember.setId(assocMember.getId());

        partialUpdatedAssocMember.email(UPDATED_EMAIL).phone(UPDATED_PHONE).year(UPDATED_YEAR).lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restAssocMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssocMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssocMember))
            )
            .andExpect(status().isOk());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
        AssocMember testAssocMember = assocMemberList.get(assocMemberList.size() - 1);
        assertThat(testAssocMember.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssocMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAssocMember.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAssocMember.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAssocMember.getRegNo()).isEqualTo(DEFAULT_REG_NO);
        assertThat(testAssocMember.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testAssocMember.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testAssocMember.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAssocMember.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAssocMember.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testAssocMember.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateAssocMemberWithPatch() throws Exception {
        // Initialize the database
        assocMemberRepository.saveAndFlush(assocMember);

        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();

        // Update the assocMember using partial update
        AssocMember partialUpdatedAssocMember = new AssocMember();
        partialUpdatedAssocMember.setId(assocMember.getId());

        partialUpdatedAssocMember
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .year(UPDATED_YEAR)
            .regNo(UPDATED_REG_NO)
            .role(UPDATED_ROLE)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);

        restAssocMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssocMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssocMember))
            )
            .andExpect(status().isOk());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
        AssocMember testAssocMember = assocMemberList.get(assocMemberList.size() - 1);
        assertThat(testAssocMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssocMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAssocMember.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAssocMember.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAssocMember.getRegNo()).isEqualTo(UPDATED_REG_NO);
        assertThat(testAssocMember.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testAssocMember.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testAssocMember.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssocMember.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAssocMember.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testAssocMember.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingAssocMember() throws Exception {
        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();
        assocMember.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssocMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assocMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assocMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssocMember() throws Exception {
        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();
        assocMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssocMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assocMember))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssocMember() throws Exception {
        int databaseSizeBeforeUpdate = assocMemberRepository.findAll().size();
        assocMember.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssocMemberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assocMember))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssocMember in the database
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssocMember() throws Exception {
        // Initialize the database
        assocMemberRepository.saveAndFlush(assocMember);

        int databaseSizeBeforeDelete = assocMemberRepository.findAll().size();

        // Delete the assocMember
        restAssocMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, assocMember.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssocMember> assocMemberList = assocMemberRepository.findAll();
        assertThat(assocMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
