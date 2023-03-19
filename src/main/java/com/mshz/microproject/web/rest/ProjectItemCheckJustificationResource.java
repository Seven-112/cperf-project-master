package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectItemCheckJustification;
import com.mshz.microproject.service.ProjectItemCheckJustificationService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectItemCheckJustificationCriteria;
import com.mshz.microproject.service.ProjectItemCheckJustificationQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectItemCheckJustification}.
 */
@RestController
@RequestMapping("/api")
public class ProjectItemCheckJustificationResource {

    private final Logger log = LoggerFactory.getLogger(ProjectItemCheckJustificationResource.class);

    private static final String ENTITY_NAME = "microprojectProjectItemCheckJustification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectItemCheckJustificationService projectItemCheckJustificationService;

    private final ProjectItemCheckJustificationQueryService projectItemCheckJustificationQueryService;

    public ProjectItemCheckJustificationResource(ProjectItemCheckJustificationService projectItemCheckJustificationService, ProjectItemCheckJustificationQueryService projectItemCheckJustificationQueryService) {
        this.projectItemCheckJustificationService = projectItemCheckJustificationService;
        this.projectItemCheckJustificationQueryService = projectItemCheckJustificationQueryService;
    }

    /**
     * {@code POST  /project-item-check-justifications} : Create a new projectItemCheckJustification.
     *
     * @param projectItemCheckJustification the projectItemCheckJustification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectItemCheckJustification, or with status {@code 400 (Bad Request)} if the projectItemCheckJustification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-item-check-justifications")
    public ResponseEntity<ProjectItemCheckJustification> createProjectItemCheckJustification(@Valid @RequestBody ProjectItemCheckJustification projectItemCheckJustification) throws URISyntaxException {
        log.debug("REST request to save ProjectItemCheckJustification : {}", projectItemCheckJustification);
        if (projectItemCheckJustification.getId() != null) {
            throw new BadRequestAlertException("A new projectItemCheckJustification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectItemCheckJustification result = projectItemCheckJustificationService.save(projectItemCheckJustification);
        return ResponseEntity.created(new URI("/api/project-item-check-justifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-item-check-justifications} : Updates an existing projectItemCheckJustification.
     *
     * @param projectItemCheckJustification the projectItemCheckJustification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectItemCheckJustification,
     * or with status {@code 400 (Bad Request)} if the projectItemCheckJustification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectItemCheckJustification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-item-check-justifications")
    public ResponseEntity<ProjectItemCheckJustification> updateProjectItemCheckJustification(@Valid @RequestBody ProjectItemCheckJustification projectItemCheckJustification) throws URISyntaxException {
        log.debug("REST request to update ProjectItemCheckJustification : {}", projectItemCheckJustification);
        if (projectItemCheckJustification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectItemCheckJustification result = projectItemCheckJustificationService.save(projectItemCheckJustification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectItemCheckJustification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-item-check-justifications} : get all the projectItemCheckJustifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectItemCheckJustifications in body.
     */
    @GetMapping("/project-item-check-justifications")
    public ResponseEntity<List<ProjectItemCheckJustification>> getAllProjectItemCheckJustifications(ProjectItemCheckJustificationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectItemCheckJustifications by criteria: {}", criteria);
        Page<ProjectItemCheckJustification> page = projectItemCheckJustificationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-item-check-justifications/count} : count all the projectItemCheckJustifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-item-check-justifications/count")
    public ResponseEntity<Long> countProjectItemCheckJustifications(ProjectItemCheckJustificationCriteria criteria) {
        log.debug("REST request to count ProjectItemCheckJustifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectItemCheckJustificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-item-check-justifications/:id} : get the "id" projectItemCheckJustification.
     *
     * @param id the id of the projectItemCheckJustification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectItemCheckJustification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-item-check-justifications/{id}")
    public ResponseEntity<ProjectItemCheckJustification> getProjectItemCheckJustification(@PathVariable Long id) {
        log.debug("REST request to get ProjectItemCheckJustification : {}", id);
        Optional<ProjectItemCheckJustification> projectItemCheckJustification = projectItemCheckJustificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectItemCheckJustification);
    }

    /**
     * {@code DELETE  /project-item-check-justifications/:id} : delete the "id" projectItemCheckJustification.
     *
     * @param id the id of the projectItemCheckJustification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-item-check-justifications/{id}")
    public ResponseEntity<Void> deleteProjectItemCheckJustification(@PathVariable Long id) {
        log.debug("REST request to delete ProjectItemCheckJustification : {}", id);
        projectItemCheckJustificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
