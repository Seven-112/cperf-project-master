package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskValidationControl;
import com.mshz.microproject.service.ProjectTaskValidationControlService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskValidationControlCriteria;
import com.mshz.microproject.service.ProjectTaskValidationControlQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskValidationControl}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskValidationControlResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskValidationControlResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskValidationControl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskValidationControlService projectTaskValidationControlService;

    private final ProjectTaskValidationControlQueryService projectTaskValidationControlQueryService;

    public ProjectTaskValidationControlResource(ProjectTaskValidationControlService projectTaskValidationControlService, ProjectTaskValidationControlQueryService projectTaskValidationControlQueryService) {
        this.projectTaskValidationControlService = projectTaskValidationControlService;
        this.projectTaskValidationControlQueryService = projectTaskValidationControlQueryService;
    }

    /**
     * {@code POST  /project-task-validation-controls} : Create a new projectTaskValidationControl.
     *
     * @param projectTaskValidationControl the projectTaskValidationControl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskValidationControl, or with status {@code 400 (Bad Request)} if the projectTaskValidationControl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-validation-controls")
    public ResponseEntity<ProjectTaskValidationControl> createProjectTaskValidationControl(@Valid @RequestBody ProjectTaskValidationControl projectTaskValidationControl) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskValidationControl : {}", projectTaskValidationControl);
        if (projectTaskValidationControl.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskValidationControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskValidationControl result = projectTaskValidationControlService.save(projectTaskValidationControl);
        return ResponseEntity.created(new URI("/api/project-task-validation-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-validation-controls} : Updates an existing projectTaskValidationControl.
     *
     * @param projectTaskValidationControl the projectTaskValidationControl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskValidationControl,
     * or with status {@code 400 (Bad Request)} if the projectTaskValidationControl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskValidationControl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-validation-controls")
    public ResponseEntity<ProjectTaskValidationControl> updateProjectTaskValidationControl(@Valid @RequestBody ProjectTaskValidationControl projectTaskValidationControl) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskValidationControl : {}", projectTaskValidationControl);
        if (projectTaskValidationControl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskValidationControl result = projectTaskValidationControlService.save(projectTaskValidationControl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskValidationControl.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-validation-controls} : get all the projectTaskValidationControls.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskValidationControls in body.
     */
    @GetMapping("/project-task-validation-controls")
    public ResponseEntity<List<ProjectTaskValidationControl>> getAllProjectTaskValidationControls(ProjectTaskValidationControlCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskValidationControls by criteria: {}", criteria);
        Page<ProjectTaskValidationControl> page = projectTaskValidationControlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-validation-controls/count} : count all the projectTaskValidationControls.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-validation-controls/count")
    public ResponseEntity<Long> countProjectTaskValidationControls(ProjectTaskValidationControlCriteria criteria) {
        log.debug("REST request to count ProjectTaskValidationControls by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskValidationControlQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-validation-controls/:id} : get the "id" projectTaskValidationControl.
     *
     * @param id the id of the projectTaskValidationControl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskValidationControl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-validation-controls/{id}")
    public ResponseEntity<ProjectTaskValidationControl> getProjectTaskValidationControl(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskValidationControl : {}", id);
        Optional<ProjectTaskValidationControl> projectTaskValidationControl = projectTaskValidationControlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskValidationControl);
    }

    /**
     * {@code DELETE  /project-task-validation-controls/:id} : delete the "id" projectTaskValidationControl.
     *
     * @param id the id of the projectTaskValidationControl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-validation-controls/{id}")
    public ResponseEntity<Void> deleteProjectTaskValidationControl(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskValidationControl : {}", id);
        projectTaskValidationControlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
