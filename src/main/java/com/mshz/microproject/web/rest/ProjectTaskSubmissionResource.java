package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskSubmission;
import com.mshz.microproject.service.ProjectTaskSubmissionService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskSubmissionCriteria;
import com.mshz.microproject.service.ProjectTaskSubmissionQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskSubmission}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskSubmissionResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskSubmission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskSubmissionService projectTaskSubmissionService;

    private final ProjectTaskSubmissionQueryService projectTaskSubmissionQueryService;

    public ProjectTaskSubmissionResource(ProjectTaskSubmissionService projectTaskSubmissionService, ProjectTaskSubmissionQueryService projectTaskSubmissionQueryService) {
        this.projectTaskSubmissionService = projectTaskSubmissionService;
        this.projectTaskSubmissionQueryService = projectTaskSubmissionQueryService;
    }

    /**
     * {@code POST  /project-task-submissions} : Create a new projectTaskSubmission.
     *
     * @param projectTaskSubmission the projectTaskSubmission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskSubmission, or with status {@code 400 (Bad Request)} if the projectTaskSubmission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-submissions")
    public ResponseEntity<ProjectTaskSubmission> createProjectTaskSubmission(@Valid @RequestBody ProjectTaskSubmission projectTaskSubmission) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskSubmission : {}", projectTaskSubmission);
        if (projectTaskSubmission.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskSubmission result = projectTaskSubmissionService.save(projectTaskSubmission);
        return ResponseEntity.created(new URI("/api/project-task-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-submissions} : Updates an existing projectTaskSubmission.
     *
     * @param projectTaskSubmission the projectTaskSubmission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskSubmission,
     * or with status {@code 400 (Bad Request)} if the projectTaskSubmission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskSubmission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-submissions")
    public ResponseEntity<ProjectTaskSubmission> updateProjectTaskSubmission(@Valid @RequestBody ProjectTaskSubmission projectTaskSubmission) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskSubmission : {}", projectTaskSubmission);
        if (projectTaskSubmission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskSubmission result = projectTaskSubmissionService.save(projectTaskSubmission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskSubmission.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-submissions} : get all the projectTaskSubmissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskSubmissions in body.
     */
    @GetMapping("/project-task-submissions")
    public ResponseEntity<List<ProjectTaskSubmission>> getAllProjectTaskSubmissions(ProjectTaskSubmissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskSubmissions by criteria: {}", criteria);
        Page<ProjectTaskSubmission> page = projectTaskSubmissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-submissions/count} : count all the projectTaskSubmissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-submissions/count")
    public ResponseEntity<Long> countProjectTaskSubmissions(ProjectTaskSubmissionCriteria criteria) {
        log.debug("REST request to count ProjectTaskSubmissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskSubmissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-submissions/:id} : get the "id" projectTaskSubmission.
     *
     * @param id the id of the projectTaskSubmission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskSubmission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-submissions/{id}")
    public ResponseEntity<ProjectTaskSubmission> getProjectTaskSubmission(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskSubmission : {}", id);
        Optional<ProjectTaskSubmission> projectTaskSubmission = projectTaskSubmissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskSubmission);
    }

    /**
     * {@code DELETE  /project-task-submissions/:id} : delete the "id" projectTaskSubmission.
     *
     * @param id the id of the projectTaskSubmission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-submissions/{id}")
    public ResponseEntity<Void> deleteProjectTaskSubmission(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskSubmission : {}", id);
        projectTaskSubmissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
