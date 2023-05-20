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
import ke.natujenge.alumni.portal.domain.Project;
import ke.natujenge.alumni.portal.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NO_OF_PLEDGE_REMINDER = "AAAAAAAAAA";
    private static final String UPDATED_NO_OF_PLEDGE_REMINDER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRIBUTED_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRIBUTED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PAYBILL = "AAAAAAAAAA";
    private static final String UPDATED_PAYBILL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMockMvc;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .name(DEFAULT_NAME)
            .targetAmount(DEFAULT_TARGET_AMOUNT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .noOfPledgeReminder(DEFAULT_NO_OF_PLEDGE_REMINDER)
            .contributedAmount(DEFAULT_CONTRIBUTED_AMOUNT)
            .paybill(DEFAULT_PAYBILL)
            .configs(DEFAULT_CONFIGS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY);
        return project;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .name(UPDATED_NAME)
            .targetAmount(UPDATED_TARGET_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .noOfPledgeReminder(UPDATED_NO_OF_PLEDGE_REMINDER)
            .contributedAmount(UPDATED_CONTRIBUTED_AMOUNT)
            .paybill(UPDATED_PAYBILL)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();
        // Create the Project
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getTargetAmount()).isEqualTo(DEFAULT_TARGET_AMOUNT);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProject.getNoOfPledgeReminder()).isEqualTo(DEFAULT_NO_OF_PLEDGE_REMINDER);
        assertThat(testProject.getContributedAmount()).isEqualTo(DEFAULT_CONTRIBUTED_AMOUNT);
        assertThat(testProject.getPaybill()).isEqualTo(DEFAULT_PAYBILL);
        assertThat(testProject.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testProject.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProject.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProject.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testProject.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void createProjectWithExistingId() throws Exception {
        // Create the Project with an existing ID
        project.setId(1L);

        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].targetAmount").value(hasItem(DEFAULT_TARGET_AMOUNT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.[*].noOfPledgeReminder").value(hasItem(DEFAULT_NO_OF_PLEDGE_REMINDER)))
            .andExpect(jsonPath("$.[*].contributedAmount").value(hasItem(DEFAULT_CONTRIBUTED_AMOUNT)))
            .andExpect(jsonPath("$.[*].paybill").value(hasItem(DEFAULT_PAYBILL)))
            .andExpect(jsonPath("$.[*].configs").value(hasItem(DEFAULT_CONFIGS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.targetAmount").value(DEFAULT_TARGET_AMOUNT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE))
            .andExpect(jsonPath("$.noOfPledgeReminder").value(DEFAULT_NO_OF_PLEDGE_REMINDER))
            .andExpect(jsonPath("$.contributedAmount").value(DEFAULT_CONTRIBUTED_AMOUNT))
            .andExpect(jsonPath("$.paybill").value(DEFAULT_PAYBILL))
            .andExpect(jsonPath("$.configs").value(DEFAULT_CONFIGS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .name(UPDATED_NAME)
            .targetAmount(UPDATED_TARGET_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .noOfPledgeReminder(UPDATED_NO_OF_PLEDGE_REMINDER)
            .contributedAmount(UPDATED_CONTRIBUTED_AMOUNT)
            .paybill(UPDATED_PAYBILL)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getTargetAmount()).isEqualTo(UPDATED_TARGET_AMOUNT);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getNoOfPledgeReminder()).isEqualTo(UPDATED_NO_OF_PLEDGE_REMINDER);
        assertThat(testProject.getContributedAmount()).isEqualTo(UPDATED_CONTRIBUTED_AMOUNT);
        assertThat(testProject.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testProject.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testProject.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProject.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testProject.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, project.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .name(UPDATED_NAME)
            .endDate(UPDATED_END_DATE)
            .noOfPledgeReminder(UPDATED_NO_OF_PLEDGE_REMINDER)
            .paybill(UPDATED_PAYBILL)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getTargetAmount()).isEqualTo(DEFAULT_TARGET_AMOUNT);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getNoOfPledgeReminder()).isEqualTo(UPDATED_NO_OF_PLEDGE_REMINDER);
        assertThat(testProject.getContributedAmount()).isEqualTo(DEFAULT_CONTRIBUTED_AMOUNT);
        assertThat(testProject.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testProject.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testProject.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProject.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testProject.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .name(UPDATED_NAME)
            .targetAmount(UPDATED_TARGET_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .noOfPledgeReminder(UPDATED_NO_OF_PLEDGE_REMINDER)
            .contributedAmount(UPDATED_CONTRIBUTED_AMOUNT)
            .paybill(UPDATED_PAYBILL)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getTargetAmount()).isEqualTo(UPDATED_TARGET_AMOUNT);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getNoOfPledgeReminder()).isEqualTo(UPDATED_NO_OF_PLEDGE_REMINDER);
        assertThat(testProject.getContributedAmount()).isEqualTo(UPDATED_CONTRIBUTED_AMOUNT);
        assertThat(testProject.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testProject.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testProject.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProject.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testProject.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, project.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(project))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, project.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
