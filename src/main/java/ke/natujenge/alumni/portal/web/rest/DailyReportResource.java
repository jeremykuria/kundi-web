package ke.natujenge.alumni.portal.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ke.natujenge.alumni.portal.domain.DailyReport;
import ke.natujenge.alumni.portal.repository.DailyReportRepository;
import ke.natujenge.alumni.portal.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ke.natujenge.alumni.portal.domain.DailyReport}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DailyReportResource {

    private final Logger log = LoggerFactory.getLogger(DailyReportResource.class);

    private static final String ENTITY_NAME = "dailyReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyReportRepository dailyReportRepository;

    public DailyReportResource(DailyReportRepository dailyReportRepository) {
        this.dailyReportRepository = dailyReportRepository;
    }

    /**
     * {@code POST  /daily-reports} : Create a new dailyReport.
     *
     * @param dailyReport the dailyReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyReport, or with status {@code 400 (Bad Request)} if the dailyReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-reports")
    public ResponseEntity<DailyReport> createDailyReport(@RequestBody DailyReport dailyReport) throws URISyntaxException {
        log.debug("REST request to save DailyReport : {}", dailyReport);
        if (dailyReport.getId() != null) {
            throw new BadRequestAlertException("A new dailyReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyReport result = dailyReportRepository.save(dailyReport);
        return ResponseEntity
            .created(new URI("/api/daily-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-reports/:id} : Updates an existing dailyReport.
     *
     * @param id the id of the dailyReport to save.
     * @param dailyReport the dailyReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyReport,
     * or with status {@code 400 (Bad Request)} if the dailyReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-reports/{id}")
    public ResponseEntity<DailyReport> updateDailyReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DailyReport dailyReport
    ) throws URISyntaxException {
        log.debug("REST request to update DailyReport : {}, {}", id, dailyReport);
        if (dailyReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DailyReport result = dailyReportRepository.save(dailyReport);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /daily-reports/:id} : Partial updates given fields of an existing dailyReport, field will ignore if it is null
     *
     * @param id the id of the dailyReport to save.
     * @param dailyReport the dailyReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyReport,
     * or with status {@code 400 (Bad Request)} if the dailyReport is not valid,
     * or with status {@code 404 (Not Found)} if the dailyReport is not found,
     * or with status {@code 500 (Internal Server Error)} if the dailyReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/daily-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DailyReport> partialUpdateDailyReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DailyReport dailyReport
    ) throws URISyntaxException {
        log.debug("REST request to partial update DailyReport partially : {}, {}", id, dailyReport);
        if (dailyReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyReport.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DailyReport> result = dailyReportRepository
            .findById(dailyReport.getId())
            .map(existingDailyReport -> {
                if (dailyReport.getReportDate() != null) {
                    existingDailyReport.setReportDate(dailyReport.getReportDate());
                }
                if (dailyReport.getSuccesfulTrx() != null) {
                    existingDailyReport.setSuccesfulTrx(dailyReport.getSuccesfulTrx());
                }
                if (dailyReport.getFailedTrx() != null) {
                    existingDailyReport.setFailedTrx(dailyReport.getFailedTrx());
                }
                if (dailyReport.getAmount() != null) {
                    existingDailyReport.setAmount(dailyReport.getAmount());
                }
                if (dailyReport.getConfigs() != null) {
                    existingDailyReport.setConfigs(dailyReport.getConfigs());
                }
                if (dailyReport.getCreatedOn() != null) {
                    existingDailyReport.setCreatedOn(dailyReport.getCreatedOn());
                }
                if (dailyReport.getCreatedBy() != null) {
                    existingDailyReport.setCreatedBy(dailyReport.getCreatedBy());
                }
                if (dailyReport.getLastUpdatedOn() != null) {
                    existingDailyReport.setLastUpdatedOn(dailyReport.getLastUpdatedOn());
                }
                if (dailyReport.getLastUpdatedBy() != null) {
                    existingDailyReport.setLastUpdatedBy(dailyReport.getLastUpdatedBy());
                }

                return existingDailyReport;
            })
            .map(dailyReportRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyReport.getId().toString())
        );
    }

    /**
     * {@code GET  /daily-reports} : get all the dailyReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyReports in body.
     */
    @GetMapping("/daily-reports")
    public ResponseEntity<List<DailyReport>> getAllDailyReports(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DailyReports");
        Page<DailyReport> page = dailyReportRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /daily-reports/:id} : get the "id" dailyReport.
     *
     * @param id the id of the dailyReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-reports/{id}")
    public ResponseEntity<DailyReport> getDailyReport(@PathVariable Long id) {
        log.debug("REST request to get DailyReport : {}", id);
        Optional<DailyReport> dailyReport = dailyReportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dailyReport);
    }

    /**
     * {@code DELETE  /daily-reports/:id} : delete the "id" dailyReport.
     *
     * @param id the id of the dailyReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-reports/{id}")
    public ResponseEntity<Void> deleteDailyReport(@PathVariable Long id) {
        log.debug("REST request to delete DailyReport : {}", id);
        dailyReportRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
