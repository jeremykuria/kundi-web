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
import ke.natujenge.alumni.portal.domain.Pledge;
import ke.natujenge.alumni.portal.domain.enumeration.ReminderCycle;
import ke.natujenge.alumni.portal.domain.enumeration.Status;
import ke.natujenge.alumni.portal.repository.PledgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PledgeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PledgeResourceIT {

    private static final Float DEFAULT_TARGET_AMOUNT = 1F;
    private static final Float UPDATED_TARGET_AMOUNT = 2F;

    private static final Float DEFAULT_PAID_AMOUNT = 1F;
    private static final Float UPDATED_PAID_AMOUNT = 2F;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.FULFILLED;

    private static final Integer DEFAULT_NO_OF_REMINDERS = 1;
    private static final Integer UPDATED_NO_OF_REMINDERS = 2;

    private static final ReminderCycle DEFAULT_REMINDER_CYCLE = ReminderCycle.DAILY;
    private static final ReminderCycle UPDATED_REMINDER_CYCLE = ReminderCycle.WEEKLY;

    private static final String DEFAULT_CONFIGS = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pledges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPledgeMockMvc;

    private Pledge pledge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pledge createEntity(EntityManager em) {
        Pledge pledge = new Pledge()
            .targetAmount(DEFAULT_TARGET_AMOUNT)
            .paidAmount(DEFAULT_PAID_AMOUNT)
            .status(DEFAULT_STATUS)
            .noOfReminders(DEFAULT_NO_OF_REMINDERS)
            .reminderCycle(DEFAULT_REMINDER_CYCLE)
            .configs(DEFAULT_CONFIGS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return pledge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pledge createUpdatedEntity(EntityManager em) {
        Pledge pledge = new Pledge()
            .targetAmount(UPDATED_TARGET_AMOUNT)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .status(UPDATED_STATUS)
            .noOfReminders(UPDATED_NO_OF_REMINDERS)
            .reminderCycle(UPDATED_REMINDER_CYCLE)
            .configs(UPDATED_CONFIGS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return pledge;
    }

    @BeforeEach
    public void initTest() {
        pledge = createEntity(em);
    }

    @Test
    @Transactional
    void createPledge() throws Exception {
        int databaseSizeBeforeCreate = pledgeRepository.findAll().size();
        // Create the Pledge
        restPledgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pledge)))
            .andExpect(status().isCreated());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeCreate + 1);
        Pledge testPledge = pledgeList.get(pledgeList.size() - 1);
        assertThat(testPledge.getTargetAmount()).isEqualTo(DEFAULT_TARGET_AMOUNT);
        assertThat(testPledge.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testPledge.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPledge.getNoOfReminders()).isEqualTo(DEFAULT_NO_OF_REMINDERS);
        assertThat(testPledge.getReminderCycle()).isEqualTo(DEFAULT_REMINDER_CYCLE);
        assertThat(testPledge.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testPledge.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPledge.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPledge.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testPledge.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createPledgeWithExistingId() throws Exception {
        // Create the Pledge with an existing ID
        pledge.setId(1L);

        int databaseSizeBeforeCreate = pledgeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPledgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pledge)))
            .andExpect(status().isBadRequest());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPledges() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get all the pledgeList
        restPledgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pledge.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetAmount").value(hasItem(DEFAULT_TARGET_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].noOfReminders").value(hasItem(DEFAULT_NO_OF_REMINDERS)))
            .andExpect(jsonPath("$.[*].reminderCycle").value(hasItem(DEFAULT_REMINDER_CYCLE.toString())))
            .andExpect(jsonPath("$.[*].configs").value(hasItem(DEFAULT_CONFIGS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getPledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        // Get the pledge
        restPledgeMockMvc
            .perform(get(ENTITY_API_URL_ID, pledge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pledge.getId().intValue()))
            .andExpect(jsonPath("$.targetAmount").value(DEFAULT_TARGET_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.noOfReminders").value(DEFAULT_NO_OF_REMINDERS))
            .andExpect(jsonPath("$.reminderCycle").value(DEFAULT_REMINDER_CYCLE.toString()))
            .andExpect(jsonPath("$.configs").value(DEFAULT_CONFIGS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingPledge() throws Exception {
        // Get the pledge
        restPledgeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();

        // Update the pledge
        Pledge updatedPledge = pledgeRepository.findById(pledge.getId()).get();
        // Disconnect from session so that the updates on updatedPledge are not directly saved in db
        em.detach(updatedPledge);
        updatedPledge
            .targetAmount(UPDATED_TARGET_AMOUNT)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .status(UPDATED_STATUS)
            .noOfReminders(UPDATED_NO_OF_REMINDERS)
            .reminderCycle(UPDATED_REMINDER_CYCLE)
            .configs(UPDATED_CONFIGS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restPledgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPledge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPledge))
            )
            .andExpect(status().isOk());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
        Pledge testPledge = pledgeList.get(pledgeList.size() - 1);
        assertThat(testPledge.getTargetAmount()).isEqualTo(UPDATED_TARGET_AMOUNT);
        assertThat(testPledge.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testPledge.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPledge.getNoOfReminders()).isEqualTo(UPDATED_NO_OF_REMINDERS);
        assertThat(testPledge.getReminderCycle()).isEqualTo(UPDATED_REMINDER_CYCLE);
        assertThat(testPledge.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testPledge.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPledge.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPledge.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testPledge.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPledge() throws Exception {
        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();
        pledge.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPledgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pledge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pledge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPledge() throws Exception {
        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();
        pledge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPledgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pledge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPledge() throws Exception {
        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();
        pledge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPledgeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pledge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePledgeWithPatch() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();

        // Update the pledge using partial update
        Pledge partialUpdatedPledge = new Pledge();
        partialUpdatedPledge.setId(pledge.getId());

        partialUpdatedPledge
            .status(UPDATED_STATUS)
            .noOfReminders(UPDATED_NO_OF_REMINDERS)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);

        restPledgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPledge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPledge))
            )
            .andExpect(status().isOk());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
        Pledge testPledge = pledgeList.get(pledgeList.size() - 1);
        assertThat(testPledge.getTargetAmount()).isEqualTo(DEFAULT_TARGET_AMOUNT);
        assertThat(testPledge.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testPledge.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPledge.getNoOfReminders()).isEqualTo(UPDATED_NO_OF_REMINDERS);
        assertThat(testPledge.getReminderCycle()).isEqualTo(DEFAULT_REMINDER_CYCLE);
        assertThat(testPledge.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testPledge.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPledge.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPledge.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testPledge.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePledgeWithPatch() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();

        // Update the pledge using partial update
        Pledge partialUpdatedPledge = new Pledge();
        partialUpdatedPledge.setId(pledge.getId());

        partialUpdatedPledge
            .targetAmount(UPDATED_TARGET_AMOUNT)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .status(UPDATED_STATUS)
            .noOfReminders(UPDATED_NO_OF_REMINDERS)
            .reminderCycle(UPDATED_REMINDER_CYCLE)
            .configs(UPDATED_CONFIGS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restPledgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPledge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPledge))
            )
            .andExpect(status().isOk());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
        Pledge testPledge = pledgeList.get(pledgeList.size() - 1);
        assertThat(testPledge.getTargetAmount()).isEqualTo(UPDATED_TARGET_AMOUNT);
        assertThat(testPledge.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testPledge.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPledge.getNoOfReminders()).isEqualTo(UPDATED_NO_OF_REMINDERS);
        assertThat(testPledge.getReminderCycle()).isEqualTo(UPDATED_REMINDER_CYCLE);
        assertThat(testPledge.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testPledge.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPledge.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPledge.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testPledge.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPledge() throws Exception {
        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();
        pledge.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPledgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pledge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pledge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPledge() throws Exception {
        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();
        pledge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPledgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pledge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPledge() throws Exception {
        int databaseSizeBeforeUpdate = pledgeRepository.findAll().size();
        pledge.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPledgeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pledge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pledge in the database
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePledge() throws Exception {
        // Initialize the database
        pledgeRepository.saveAndFlush(pledge);

        int databaseSizeBeforeDelete = pledgeRepository.findAll().size();

        // Delete the pledge
        restPledgeMockMvc
            .perform(delete(ENTITY_API_URL_ID, pledge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pledge> pledgeList = pledgeRepository.findAll();
        assertThat(pledgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
