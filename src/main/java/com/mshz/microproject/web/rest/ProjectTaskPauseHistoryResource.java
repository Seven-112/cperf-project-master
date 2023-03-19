package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskPauseHistory;
import com.mshz.microproject.service.ProjectTaskPauseHistoryService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskPauseHistoryCriteria;
import com.mshz.microproject.service.ProjectTaskPauseHistoryQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskPauseHistory}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskPauseHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskPauseHistoryResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskPauseHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskPauseHistoryService projectTaskPauseHistoryService;

    private final ProjectTaskPauseHistoryQueryService projectTaskPauseHistoryQueryService;

    public ProjectTaskPauseHistoryResource(ProjectTaskPauseHistoryService projectTaskPauseHistoryService, ProjectTaskPauseHistoryQueryService projectTaskPauseHistoryQueryService) {
        this.projectTaskPauseHistoryService = projectTaskPauseHistoryService;
        this.projectTaskPauseHistoryQueryService = projectTaskPauseHistoryQueryService;
    }

    /**
     * {@code POST  /project-task-pause-histories} : Create a new projectTaskPauseHistory.
     *
     * @param projectTaskPauseHistory the projectTaskPauseHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskPauseHistory, or with status {@code 400 (Bad Request)} if the projectTaskPauseHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-pause-histories")
    public ResponseEntity<ProjectTaskPauseHistory> createProjectTaskPauseHistory(@Valid @RequestBody ProjectTaskPauseHistory projectTaskPauseHistory) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskPauseHistory : {}", projectTaskPauseHistory);
        if (projectTaskPauseHistory.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskPauseHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskPauseHistory result = projectTaskPauseHistoryService.save(projectTaskPauseHistory);
        return ResponseEntity.created(new URI("/api/project-task-pause-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-pause-histories} : Updates an existing projectTaskPauseHistory.
     *
     * @param projectTaskPauseHistory the projectTaskPauseHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskPauseHistory,
     * or with status {@code 400 (Bad Request)} if the projectTaskPauseHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskPauseHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-pause-histories")
    public ResponseEntity<ProjectTaskPauseHistory> updateProjectTaskPauseHistory(@Valid @RequestBody ProjectTaskPauseHistory projectTaskPauseHistory) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskPauseHistory : {}", projectTaskPauseHistory);
        if (projectTaskPauseHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskPauseHistory result = projectTaskPauseHistoryService.save(projectTaskPauseHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskPauseHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-pause-histories} : get all the projectTaskPauseHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskPauseHistories in body.
     */
    @GetMapping("/project-task-pause-histories")
    public ResponseEntity<List<ProjectTaskPauseHistory>> getAllProjectTaskPauseHistories(ProjectTaskPauseHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskPauseHistories by criteria: {}", criteria);
        Page<ProjectTaskPauseHistory> page = projectTaskPauseHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-pause-histories/count} : count all the projectTaskPauseHistories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-pause-histories/count")
    public ResponseEntity<Long> countProjectTaskPauseHistories(ProjectTaskPauseHistoryCriteria criteria) {
        log.debug("REST request to count ProjectTaskPauseHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskPauseHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-pause-histories/:id} : get the "id" projectTaskPauseHistory.
     *
     * @param id the id of the projectTaskPauseHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskPauseHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-pause-histories/{id}")
    public ResponseEntity<ProjectTaskPauseHistory> getProjectTaskPauseHistory(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskPauseHistory : {}", id);
        Optional<ProjectTaskPauseHistory> projectTaskPauseHistory = projectTaskPauseHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskPauseHistory);
    }

    /**
     * {@code DELETE  /project-task-pause-histories/:id} : delete the "id" projectTaskPauseHistory.
     *
     * @param id the id of the projectTaskPauseHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-pause-histories/{id}")
    public ResponseEntity<Void> deleteProjectTaskPauseHistory(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskPauseHistory : {}", id);
        projectTaskPauseHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
