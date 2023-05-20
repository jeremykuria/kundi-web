package ke.natujenge.alumni.portal.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ke.natujenge.alumni.portal.domain.Pledge;
import ke.natujenge.alumni.portal.repository.PledgeRepository;
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
 * REST controller for managing {@link ke.natujenge.alumni.portal.domain.Pledge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PledgeResource {

    private final Logger log = LoggerFactory.getLogger(PledgeResource.class);

    private static final String ENTITY_NAME = "pledge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PledgeRepository pledgeRepository;

    public PledgeResource(PledgeRepository pledgeRepository) {
        this.pledgeRepository = pledgeRepository;
    }

    /**
     * {@code POST  /pledges} : Create a new pledge.
     *
     * @param pledge the pledge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pledge, or with status {@code 400 (Bad Request)} if the pledge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pledges")
    public ResponseEntity<Pledge> createPledge(@RequestBody Pledge pledge) throws URISyntaxException {
        log.debug("REST request to save Pledge : {}", pledge);
        if (pledge.getId() != null) {
            throw new BadRequestAlertException("A new pledge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pledge result = pledgeRepository.save(pledge);
        return ResponseEntity
            .created(new URI("/api/pledges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pledges/:id} : Updates an existing pledge.
     *
     * @param id the id of the pledge to save.
     * @param pledge the pledge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pledge,
     * or with status {@code 400 (Bad Request)} if the pledge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pledge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pledges/{id}")
    public ResponseEntity<Pledge> updatePledge(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pledge pledge)
        throws URISyntaxException {
        log.debug("REST request to update Pledge : {}, {}", id, pledge);
        if (pledge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pledge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pledgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pledge result = pledgeRepository.save(pledge);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pledge.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pledges/:id} : Partial updates given fields of an existing pledge, field will ignore if it is null
     *
     * @param id the id of the pledge to save.
     * @param pledge the pledge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pledge,
     * or with status {@code 400 (Bad Request)} if the pledge is not valid,
     * or with status {@code 404 (Not Found)} if the pledge is not found,
     * or with status {@code 500 (Internal Server Error)} if the pledge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pledges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pledge> partialUpdatePledge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Pledge pledge
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pledge partially : {}, {}", id, pledge);
        if (pledge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pledge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pledgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pledge> result = pledgeRepository
            .findById(pledge.getId())
            .map(existingPledge -> {
                if (pledge.getTargetAmount() != null) {
                    existingPledge.setTargetAmount(pledge.getTargetAmount());
                }
                if (pledge.getPaidAmount() != null) {
                    existingPledge.setPaidAmount(pledge.getPaidAmount());
                }
                if (pledge.getStatus() != null) {
                    existingPledge.setStatus(pledge.getStatus());
                }
                if (pledge.getNoOfReminders() != null) {
                    existingPledge.setNoOfReminders(pledge.getNoOfReminders());
                }
                if (pledge.getReminderCycle() != null) {
                    existingPledge.setReminderCycle(pledge.getReminderCycle());
                }
                if (pledge.getConfigs() != null) {
                    existingPledge.setConfigs(pledge.getConfigs());
                }
                if (pledge.getCreatedOn() != null) {
                    existingPledge.setCreatedOn(pledge.getCreatedOn());
                }
                if (pledge.getCreatedBy() != null) {
                    existingPledge.setCreatedBy(pledge.getCreatedBy());
                }
                if (pledge.getLastUpdatedOn() != null) {
                    existingPledge.setLastUpdatedOn(pledge.getLastUpdatedOn());
                }
                if (pledge.getLastUpdatedBy() != null) {
                    existingPledge.setLastUpdatedBy(pledge.getLastUpdatedBy());
                }

                return existingPledge;
            })
            .map(pledgeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pledge.getId().toString())
        );
    }

    /**
     * {@code GET  /pledges} : get all the pledges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pledges in body.
     */
    @GetMapping("/pledges")
    public ResponseEntity<List<Pledge>> getAllPledges(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Pledges");
        Page<Pledge> page = pledgeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pledges/:id} : get the "id" pledge.
     *
     * @param id the id of the pledge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pledge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pledges/{id}")
    public ResponseEntity<Pledge> getPledge(@PathVariable Long id) {
        log.debug("REST request to get Pledge : {}", id);
        Optional<Pledge> pledge = pledgeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pledge);
    }

    /**
     * {@code DELETE  /pledges/:id} : delete the "id" pledge.
     *
     * @param id the id of the pledge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pledges/{id}")
    public ResponseEntity<Void> deletePledge(@PathVariable Long id) {
        log.debug("REST request to delete Pledge : {}", id);
        pledgeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
