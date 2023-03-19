package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectEventTrigger;
import com.mshz.microproject.service.ProjectEventTriggerService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectEventTriggerCriteria;
import com.mshz.microproject.service.ProjectEventTriggerQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectEventTrigger}.
 */
@RestController
@RequestMapping("/api")
public class ProjectEventTriggerResource {

    private final Logger log = LoggerFactory.getLogger(ProjectEventTriggerResource.class);

    private static final String ENTITY_NAME = "microprojectProjectEventTrigger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectEventTriggerService projectEventTriggerService;

    private final ProjectEventTriggerQueryService projectEventTriggerQueryService;

    public ProjectEventTriggerResource(ProjectEventTriggerService projectEventTriggerService, ProjectEventTriggerQueryService projectEventTriggerQueryService) {
        this.projectEventTriggerService = projectEventTriggerService;
        this.projectEventTriggerQueryService = projectEventTriggerQueryService;
    }

    /**
     * {@code POST  /project-event-triggers} : Create a new projectEventTrigger.
     *
     * @param projectEventTrigger the projectEventTrigger to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectEventTrigger, or with status {@code 400 (Bad Request)} if the projectEventTrigger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-event-triggers")
    public ResponseEntity<ProjectEventTrigger> createProjectEventTrigger(@Valid @RequestBody ProjectEventTrigger projectEventTrigger) throws URISyntaxException {
        log.debug("REST request to save ProjectEventTrigger : {}", projectEventTrigger);
        if (projectEventTrigger.getId() != null) {
            throw new BadRequestAlertException("A new projectEventTrigger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectEventTrigger result = projectEventTriggerService.save(projectEventTrigger);
        return ResponseEntity.created(new URI("/api/project-event-triggers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-event-triggers} : Updates an existing projectEventTrigger.
     *
     * @param projectEventTrigger the projectEventTrigger to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectEventTrigger,
     * or with status {@code 400 (Bad Request)} if the projectEventTrigger is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectEventTrigger couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-event-triggers")
    public ResponseEntity<ProjectEventTrigger> updateProjectEventTrigger(@Valid @RequestBody ProjectEventTrigger projectEventTrigger) throws URISyntaxException {
        log.debug("REST request to update ProjectEventTrigger : {}", projectEventTrigger);
        if (projectEventTrigger.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectEventTrigger result = projectEventTriggerService.save(projectEventTrigger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectEventTrigger.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-event-triggers} : get all the projectEventTriggers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectEventTriggers in body.
     */
    @GetMapping("/project-event-triggers")
    public ResponseEntity<List<ProjectEventTrigger>> getAllProjectEventTriggers(ProjectEventTriggerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectEventTriggers by criteria: {}", criteria);
        Page<ProjectEventTrigger> page = projectEventTriggerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-event-triggers/count} : count all the projectEventTriggers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-event-triggers/count")
    public ResponseEntity<Long> countProjectEventTriggers(ProjectEventTriggerCriteria criteria) {
        log.debug("REST request to count ProjectEventTriggers by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectEventTriggerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-event-triggers/:id} : get the "id" projectEventTrigger.
     *
     * @param id the id of the projectEventTrigger to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectEventTrigger, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-event-triggers/{id}")
    public ResponseEntity<ProjectEventTrigger> getProjectEventTrigger(@PathVariable Long id) {
        log.debug("REST request to get ProjectEventTrigger : {}", id);
        Optional<ProjectEventTrigger> projectEventTrigger = projectEventTriggerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectEventTrigger);
    }

    /**
     * {@code DELETE  /project-event-triggers/:id} : delete the "id" projectEventTrigger.
     *
     * @param id the id of the projectEventTrigger to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-event-triggers/{id}")
    public ResponseEntity<Void> deleteProjectEventTrigger(@PathVariable Long id) {
        log.debug("REST request to delete ProjectEventTrigger : {}", id);
        projectEventTriggerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
