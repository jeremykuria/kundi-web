package ke.natujenge.alumni.portal.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ke.natujenge.alumni.portal.domain.Association;
import ke.natujenge.alumni.portal.repository.AssociationRepository;
import ke.natujenge.alumni.portal.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ke.natujenge.alumni.portal.domain.Association}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AssociationResource {

    private final Logger log = LoggerFactory.getLogger(AssociationResource.class);

    private static final String ENTITY_NAME = "association";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssociationRepository associationRepository;

    public AssociationResource(AssociationRepository associationRepository) {
        this.associationRepository = associationRepository;
    }

    /**
     * {@code POST  /associations} : Create a new association.
     *
     * @param association the association to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new association, or with status {@code 400 (Bad Request)} if the association has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/associations")
    public ResponseEntity<Association> createAssociation(@RequestBody Association association) throws URISyntaxException {
        log.debug("REST request to save Association : {}", association);
        if (association.getId() != null) {
            throw new BadRequestAlertException("A new association cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Association result = associationRepository.save(association);
        return ResponseEntity
            .created(new URI("/api/associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /associations/:id} : Updates an existing association.
     *
     * @param id the id of the association to save.
     * @param association the association to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated association,
     * or with status {@code 400 (Bad Request)} if the association is not valid,
     * or with status {@code 500 (Internal Server Error)} if the association couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/associations/{id}")
    public ResponseEntity<Association> updateAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Association association
    ) throws URISyntaxException {
        log.debug("REST request to update Association : {}, {}", id, association);
        if (association.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, association.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!associationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Association result = associationRepository.save(association);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, association.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /associations/:id} : Partial updates given fields of an existing association, field will ignore if it is null
     *
     * @param id the id of the association to save.
     * @param association the association to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated association,
     * or with status {@code 400 (Bad Request)} if the association is not valid,
     * or with status {@code 404 (Not Found)} if the association is not found,
     * or with status {@code 500 (Internal Server Error)} if the association couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/associations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Association> partialUpdateAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Association association
    ) throws URISyntaxException {
        log.debug("REST request to partial update Association partially : {}, {}", id, association);
        if (association.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, association.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!associationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Association> result = associationRepository
            .findById(association.getId())
            .map(existingAssociation -> {
                if (association.getName() != null) {
                    existingAssociation.setName(association.getName());
                }
                if (association.getBrief() != null) {
                    existingAssociation.setBrief(association.getBrief());
                }
                if (association.getDescription() != null) {
                    existingAssociation.setDescription(association.getDescription());
                }
                if (association.getPhoneNumber() != null) {
                    existingAssociation.setPhoneNumber(association.getPhoneNumber());
                }
                if (association.getContentStatus() != null) {
                    existingAssociation.setContentStatus(association.getContentStatus());
                }
                if (association.getPaybill() != null) {
                    existingAssociation.setPaybill(association.getPaybill());
                }
                if (association.getAssociationType() != null) {
                    existingAssociation.setAssociationType(association.getAssociationType());
                }
                if (association.getConfigs() != null) {
                    existingAssociation.setConfigs(association.getConfigs());
                }
                if (association.getCreatedBy() != null) {
                    existingAssociation.setCreatedBy(association.getCreatedBy());
                }
                if (association.getCreatedOn() != null) {
                    existingAssociation.setCreatedOn(association.getCreatedOn());
                }
                if (association.getLastUpdatedOn() != null) {
                    existingAssociation.setLastUpdatedOn(association.getLastUpdatedOn());
                }
                if (association.getLastUpdatedBy() != null) {
                    existingAssociation.setLastUpdatedBy(association.getLastUpdatedBy());
                }

                return existingAssociation;
            })
            .map(associationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, association.getId().toString())
        );
    }

    /**
     * {@code GET  /associations} : get all the associations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of associations in body.
     */
    @GetMapping("/associations")
    public List<Association> getAllAssociations() {
        log.debug("REST request to get all Associations");
        return associationRepository.findAll();
    }

    /**
     * {@code GET  /associations/:id} : get the "id" association.
     *
     * @param id the id of the association to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the association, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/associations/{id}")
    public ResponseEntity<Association> getAssociation(@PathVariable Long id) {
        log.debug("REST request to get Association : {}", id);
        Optional<Association> association = associationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(association);
    }

    /**
     * {@code DELETE  /associations/:id} : delete the "id" association.
     *
     * @param id the id of the association to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/associations/{id}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable Long id) {
        log.debug("REST request to delete Association : {}", id);
        associationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
