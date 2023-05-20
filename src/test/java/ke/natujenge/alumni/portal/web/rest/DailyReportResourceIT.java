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
import ke.natujenge.alumni.portal.domain.DailyReport;
import ke.natujenge.alumni.portal.repository.DailyReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DailyReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DailyReportResourceIT {

    private static final String DEFAULT_REPORT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_SUCCESFUL_TRX = "AAAAAAAAAA";
    private static final String UPDATED_SUCCESFUL_TRX = "BBBBBBBBBB";

    private static final String DEFAULT_FAILED_TRX = "AAAAAAAAAA";
    private static final String UPDATED_FAILED_TRX = "BBBBBBBBBB";

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

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

    private static final String ENTITY_API_URL = "/api/daily-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDailyReportMockMvc;

    private DailyReport dailyReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyReport createEntity(EntityManager em) {
        DailyReport dailyReport = new DailyReport()
            .reportDate(DEFAULT_REPORT_DATE)
            .succesfulTrx(DEFAULT_SUCCESFUL_TRX)
            .failedTrx(DEFAULT_FAILED_TRX)
            .amount(DEFAULT_AMOUNT)
            .configs(DEFAULT_CONFIGS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return dailyReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyReport createUpdatedEntity(EntityManager em) {
        DailyReport dailyReport = new DailyReport()
            .reportDate(UPDATED_REPORT_DATE)
            .succesfulTrx(UPDATED_SUCCESFUL_TRX)
            .failedTrx(UPDATED_FAILED_TRX)
            .amount(UPDATED_AMOUNT)
            .configs(UPDATED_CONFIGS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return dailyReport;
    }

    @BeforeEach
    public void initTest() {
        dailyReport = createEntity(em);
    }

    @Test
    @Transactional
    void createDailyReport() throws Exception {
        int databaseSizeBeforeCreate = dailyReportRepository.findAll().size();
        // Create the DailyReport
        restDailyReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReport)))
            .andExpect(status().isCreated());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeCreate + 1);
        DailyReport testDailyReport = dailyReportList.get(dailyReportList.size() - 1);
        assertThat(testDailyReport.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
        assertThat(testDailyReport.getSuccesfulTrx()).isEqualTo(DEFAULT_SUCCESFUL_TRX);
        assertThat(testDailyReport.getFailedTrx()).isEqualTo(DEFAULT_FAILED_TRX);
        assertThat(testDailyReport.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDailyReport.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testDailyReport.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDailyReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDailyReport.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testDailyReport.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createDailyReportWithExistingId() throws Exception {
        // Create the DailyReport with an existing ID
        dailyReport.setId(1L);

        int databaseSizeBeforeCreate = dailyReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyReportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReport)))
            .andExpect(status().isBadRequest());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDailyReports() throws Exception {
        // Initialize the database
        dailyReportRepository.saveAndFlush(dailyReport);

        // Get all the dailyReportList
        restDailyReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE)))
            .andExpect(jsonPath("$.[*].succesfulTrx").value(hasItem(DEFAULT_SUCCESFUL_TRX)))
            .andExpect(jsonPath("$.[*].failedTrx").value(hasItem(DEFAULT_FAILED_TRX)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].configs").value(hasItem(DEFAULT_CONFIGS)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getDailyReport() throws Exception {
        // Initialize the database
        dailyReportRepository.saveAndFlush(dailyReport);

        // Get the dailyReport
        restDailyReportMockMvc
            .perform(get(ENTITY_API_URL_ID, dailyReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyReport.getId().intValue()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE))
            .andExpect(jsonPath("$.succesfulTrx").value(DEFAULT_SUCCESFUL_TRX))
            .andExpect(jsonPath("$.failedTrx").value(DEFAULT_FAILED_TRX))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.configs").value(DEFAULT_CONFIGS))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingDailyReport() throws Exception {
        // Get the dailyReport
        restDailyReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDailyReport() throws Exception {
        // Initialize the database
        dailyReportRepository.saveAndFlush(dailyReport);

        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();

        // Update the dailyReport
        DailyReport updatedDailyReport = dailyReportRepository.findById(dailyReport.getId()).get();
        // Disconnect from session so that the updates on updatedDailyReport are not directly saved in db
        em.detach(updatedDailyReport);
        updatedDailyReport
            .reportDate(UPDATED_REPORT_DATE)
            .succesfulTrx(UPDATED_SUCCESFUL_TRX)
            .failedTrx(UPDATED_FAILED_TRX)
            .amount(UPDATED_AMOUNT)
            .configs(UPDATED_CONFIGS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restDailyReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDailyReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDailyReport))
            )
            .andExpect(status().isOk());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
        DailyReport testDailyReport = dailyReportList.get(dailyReportList.size() - 1);
        assertThat(testDailyReport.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testDailyReport.getSuccesfulTrx()).isEqualTo(UPDATED_SUCCESFUL_TRX);
        assertThat(testDailyReport.getFailedTrx()).isEqualTo(UPDATED_FAILED_TRX);
        assertThat(testDailyReport.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDailyReport.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testDailyReport.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDailyReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDailyReport.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testDailyReport.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingDailyReport() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();
        dailyReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dailyReport.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDailyReport() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();
        dailyReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dailyReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDailyReport() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();
        dailyReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dailyReport)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDailyReportWithPatch() throws Exception {
        // Initialize the database
        dailyReportRepository.saveAndFlush(dailyReport);

        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();

        // Update the dailyReport using partial update
        DailyReport partialUpdatedDailyReport = new DailyReport();
        partialUpdatedDailyReport.setId(dailyReport.getId());

        partialUpdatedDailyReport
            .reportDate(UPDATED_REPORT_DATE)
            .succesfulTrx(UPDATED_SUCCESFUL_TRX)
            .amount(UPDATED_AMOUNT)
            .configs(UPDATED_CONFIGS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restDailyReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyReport))
            )
            .andExpect(status().isOk());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
        DailyReport testDailyReport = dailyReportList.get(dailyReportList.size() - 1);
        assertThat(testDailyReport.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testDailyReport.getSuccesfulTrx()).isEqualTo(UPDATED_SUCCESFUL_TRX);
        assertThat(testDailyReport.getFailedTrx()).isEqualTo(DEFAULT_FAILED_TRX);
        assertThat(testDailyReport.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDailyReport.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testDailyReport.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDailyReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDailyReport.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testDailyReport.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateDailyReportWithPatch() throws Exception {
        // Initialize the database
        dailyReportRepository.saveAndFlush(dailyReport);

        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();

        // Update the dailyReport using partial update
        DailyReport partialUpdatedDailyReport = new DailyReport();
        partialUpdatedDailyReport.setId(dailyReport.getId());

        partialUpdatedDailyReport
            .reportDate(UPDATED_REPORT_DATE)
            .succesfulTrx(UPDATED_SUCCESFUL_TRX)
            .failedTrx(UPDATED_FAILED_TRX)
            .amount(UPDATED_AMOUNT)
            .configs(UPDATED_CONFIGS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restDailyReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDailyReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDailyReport))
            )
            .andExpect(status().isOk());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
        DailyReport testDailyReport = dailyReportList.get(dailyReportList.size() - 1);
        assertThat(testDailyReport.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
        assertThat(testDailyReport.getSuccesfulTrx()).isEqualTo(UPDATED_SUCCESFUL_TRX);
        assertThat(testDailyReport.getFailedTrx()).isEqualTo(UPDATED_FAILED_TRX);
        assertThat(testDailyReport.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDailyReport.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testDailyReport.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDailyReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDailyReport.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testDailyReport.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingDailyReport() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();
        dailyReport.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dailyReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDailyReport() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();
        dailyReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dailyReport))
            )
            .andExpect(status().isBadRequest());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDailyReport() throws Exception {
        int databaseSizeBeforeUpdate = dailyReportRepository.findAll().size();
        dailyReport.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDailyReportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dailyReport))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DailyReport in the database
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDailyReport() throws Exception {
        // Initialize the database
        dailyReportRepository.saveAndFlush(dailyReport);

        int databaseSizeBeforeDelete = dailyReportRepository.findAll().size();

        // Delete the dailyReport
        restDailyReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, dailyReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyReport> dailyReportList = dailyReportRepository.findAll();
        assertThat(dailyReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
