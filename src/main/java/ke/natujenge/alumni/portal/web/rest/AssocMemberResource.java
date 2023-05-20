package ke.natujenge.alumni.portal.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ke.natujenge.alumni.portal.domain.AssocMember;
import ke.natujenge.alumni.portal.repository.AssocMemberRepository;
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
 * REST controller for managing {@link ke.natujenge.alumni.portal.domain.AssocMember}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AssocMemberResource {

    private final Logger log = LoggerFactory.getLogger(AssocMemberResource.class);

    private static final String ENTITY_NAME = "assocMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssocMemberRepository assocMemberRepository;

    public AssocMemberResource(AssocMemberRepository assocMemberRepository) {
        this.assocMemberRepository = assocMemberRepository;
    }

    /**
     * {@code POST  /assoc-members} : Create a new assocMember.
     *
     * @param assocMember the assocMember to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assocMember, or with status {@code 400 (Bad Request)} if the assocMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assoc-members")
    public ResponseEntity<AssocMember> createAssocMember(@RequestBody AssocMember assocMember) throws URISyntaxException {
        log.debug("REST request to save AssocMember : {}", assocMember);
        if (assocMember.getId() != null) {
            throw new BadRequestAlertException("A new assocMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssocMember result = assocMemberRepository.save(assocMember);
        return ResponseEntity
            .created(new URI("/api/assoc-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assoc-members/:id} : Updates an existing assocMember.
     *
     * @param id the id of the assocMember to save.
     * @param assocMember the assocMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assocMember,
     * or with status {@code 400 (Bad Request)} if the assocMember is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assocMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assoc-members/{id}")
    public ResponseEntity<AssocMember> updateAssocMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssocMember assocMember
    ) throws URISyntaxException {
        log.debug("REST request to update AssocMember : {}, {}", id, assocMember);
        if (assocMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assocMember.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assocMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssocMember result = assocMemberRepository.save(assocMember);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assocMember.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /assoc-members/:id} : Partial updates given fields of an existing assocMember, field will ignore if it is null
     *
     * @param id the id of the assocMember to save.
     * @param assocMember the assocMember to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assocMember,
     * or with status {@code 400 (Bad Request)} if the assocMember is not valid,
     * or with status {@code 404 (Not Found)} if the assocMember is not found,
     * or with status {@code 500 (Internal Server Error)} if the assocMember couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/assoc-members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssocMember> partialUpdateAssocMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssocMember assocMember
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssocMember partially : {}, {}", id, assocMember);
        if (assocMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assocMember.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assocMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssocMember> result = assocMemberRepository
            .findById(assocMember.getId())
            .map(existingAssocMember -> {
                if (assocMember.getName() != null) {
                    existingAssocMember.setName(assocMember.getName());
                }
                if (assocMember.getEmail() != null) {
                    existingAssocMember.setEmail(assocMember.getEmail());
                }
                if (assocMember.getPhone() != null) {
                    existingAssocMember.setPhone(assocMember.getPhone());
                }
                if (assocMember.getYear() != null) {
                    existingAssocMember.setYear(assocMember.getYear());
                }
                if (assocMember.getRegNo() != null) {
                    existingAssocMember.setRegNo(assocMember.getRegNo());
                }
                if (assocMember.getRole() != null) {
                    existingAssocMember.setRole(assocMember.getRole());
                }
                if (assocMember.getConfigs() != null) {
                    existingAssocMember.setConfigs(assocMember.getConfigs());
                }
                if (assocMember.getCreatedBy() != null) {
                    existingAssocMember.setCreatedBy(assocMember.getCreatedBy());
                }
                if (assocMember.getCreatedOn() != null) {
                    existingAssocMember.setCreatedOn(assocMember.getCreatedOn());
                }
                if (assocMember.getLastUpdatedBy() != null) {
                    existingAssocMember.setLastUpdatedBy(assocMember.getLastUpdatedBy());
                }
                if (assocMember.getLastUpdatedOn() != null) {
                    existingAssocMember.setLastUpdatedOn(assocMember.getLastUpdatedOn());
                }

                return existingAssocMember;
            })
            .map(assocMemberRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assocMember.getId().toString())
        );
    }

    /**
     * {@code GET  /assoc-members} : get all the assocMembers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assocMembers in body.
     */
    @GetMapping("/assoc-members")
    public ResponseEntity<List<AssocMember>> getAllAssocMembers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of AssocMembers");
        Page<AssocMember> page;
        if (eagerload) {
            page = assocMemberRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = assocMemberRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assoc-members/:id} : get the "id" assocMember.
     *
     * @param id the id of the assocMember to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assocMember, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assoc-members/{id}")
    public ResponseEntity<AssocMember> getAssocMember(@PathVariable Long id) {
        log.debug("REST request to get AssocMember : {}", id);
        Optional<AssocMember> assocMember = assocMemberRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(assocMember);
    }

    /**
     * {@code DELETE  /assoc-members/:id} : delete the "id" assocMember.
     *
     * @param id the id of the assocMember to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assoc-members/{id}")
    public ResponseEntity<Void> deleteAssocMember(@PathVariable Long id) {
        log.debug("REST request to delete AssocMember : {}", id);
        assocMemberRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
