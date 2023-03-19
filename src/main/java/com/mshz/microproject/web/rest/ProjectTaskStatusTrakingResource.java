package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskStatusTraking;
import com.mshz.microproject.service.ProjectTaskStatusTrakingService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskStatusTrakingCriteria;
import com.mshz.microproject.service.ProjectTaskStatusTrakingQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskStatusTraking}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskStatusTrakingResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskStatusTrakingResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskStatusTraking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskStatusTrakingService projectTaskStatusTrakingService;

    private final ProjectTaskStatusTrakingQueryService projectTaskStatusTrakingQueryService;

    public ProjectTaskStatusTrakingResource(ProjectTaskStatusTrakingService projectTaskStatusTrakingService, ProjectTaskStatusTrakingQueryService projectTaskStatusTrakingQueryService) {
        this.projectTaskStatusTrakingService = projectTaskStatusTrakingService;
        this.projectTaskStatusTrakingQueryService = projectTaskStatusTrakingQueryService;
    }

    /**
     * {@code POST  /project-task-status-trakings} : Create a new projectTaskStatusTraking.
     *
     * @param projectTaskStatusTraking the projectTaskStatusTraking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskStatusTraking, or with status {@code 400 (Bad Request)} if the projectTaskStatusTraking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-status-trakings")
    public ResponseEntity<ProjectTaskStatusTraking> createProjectTaskStatusTraking(@Valid @RequestBody ProjectTaskStatusTraking projectTaskStatusTraking) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskStatusTraking : {}", projectTaskStatusTraking);
        if (projectTaskStatusTraking.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskStatusTraking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskStatusTraking result = projectTaskStatusTrakingService.save(projectTaskStatusTraking);
        return ResponseEntity.created(new URI("/api/project-task-status-trakings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-status-trakings} : Updates an existing projectTaskStatusTraking.
     *
     * @param projectTaskStatusTraking the projectTaskStatusTraking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskStatusTraking,
     * or with status {@code 400 (Bad Request)} if the projectTaskStatusTraking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskStatusTraking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-status-trakings")
    public ResponseEntity<ProjectTaskStatusTraking> updateProjectTaskStatusTraking(@Valid @RequestBody ProjectTaskStatusTraking projectTaskStatusTraking) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskStatusTraking : {}", projectTaskStatusTraking);
        if (projectTaskStatusTraking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskStatusTraking result = projectTaskStatusTrakingService.save(projectTaskStatusTraking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskStatusTraking.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-status-trakings} : get all the projectTaskStatusTrakings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskStatusTrakings in body.
     */
    @GetMapping("/project-task-status-trakings")
    public ResponseEntity<List<ProjectTaskStatusTraking>> getAllProjectTaskStatusTrakings(ProjectTaskStatusTrakingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskStatusTrakings by criteria: {}", criteria);
        Page<ProjectTaskStatusTraking> page = projectTaskStatusTrakingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-status-trakings/count} : count all the projectTaskStatusTrakings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-status-trakings/count")
    public ResponseEntity<Long> countProjectTaskStatusTrakings(ProjectTaskStatusTrakingCriteria criteria) {
        log.debug("REST request to count ProjectTaskStatusTrakings by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskStatusTrakingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-status-trakings/:id} : get the "id" projectTaskStatusTraking.
     *
     * @param id the id of the projectTaskStatusTraking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskStatusTraking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-status-trakings/{id}")
    public ResponseEntity<ProjectTaskStatusTraking> getProjectTaskStatusTraking(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskStatusTraking : {}", id);
        Optional<ProjectTaskStatusTraking> projectTaskStatusTraking = projectTaskStatusTrakingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskStatusTraking);
    }

    /**
     * {@code DELETE  /project-task-status-trakings/:id} : delete the "id" projectTaskStatusTraking.
     *
     * @param id the id of the projectTaskStatusTraking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-status-trakings/{id}")
    public ResponseEntity<Void> deleteProjectTaskStatusTraking(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskStatusTraking : {}", id);
        projectTaskStatusTrakingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
