package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectStartableTask;
import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;
import com.mshz.microproject.service.ProjectStartableTaskService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectStartableTaskCriteria;
import com.mshz.microproject.service.ProjectStartableTaskQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectStartableTask}.
 */
@RestController
@RequestMapping("/api")
public class ProjectStartableTaskResource {

    private final Logger log = LoggerFactory.getLogger(ProjectStartableTaskResource.class);

    private static final String ENTITY_NAME = "microprojectProjectStartableTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectStartableTaskService projectStartableTaskService;

    private final ProjectStartableTaskQueryService projectStartableTaskQueryService;

    public ProjectStartableTaskResource(ProjectStartableTaskService projectStartableTaskService, ProjectStartableTaskQueryService projectStartableTaskQueryService) {
        this.projectStartableTaskService = projectStartableTaskService;
        this.projectStartableTaskQueryService = projectStartableTaskQueryService;
    }

    /**
     * {@code POST  /project-startable-tasks} : Create a new projectStartableTask.
     *
     * @param projectStartableTask the projectStartableTask to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectStartableTask, or with status {@code 400 (Bad Request)} if the projectStartableTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-startable-tasks")
    public ResponseEntity<ProjectStartableTask> createProjectStartableTask(@RequestBody ProjectStartableTask projectStartableTask) throws URISyntaxException {
        log.debug("REST request to save ProjectStartableTask : {}", projectStartableTask);
        if (projectStartableTask.getId() != null) {
            throw new BadRequestAlertException("A new projectStartableTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectStartableTask result = projectStartableTaskService.save(projectStartableTask);
        return ResponseEntity.created(new URI("/api/project-startable-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-startable-tasks} : Updates an existing projectStartableTask.
     *
     * @param projectStartableTask the projectStartableTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectStartableTask,
     * or with status {@code 400 (Bad Request)} if the projectStartableTask is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectStartableTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-startable-tasks")
    public ResponseEntity<ProjectStartableTask> updateProjectStartableTask(@RequestBody ProjectStartableTask projectStartableTask) throws URISyntaxException {
        log.debug("REST request to update ProjectStartableTask : {}", projectStartableTask);
        if (projectStartableTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectStartableTask result = projectStartableTaskService.save(projectStartableTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectStartableTask.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-startable-tasks} : get all the projectStartableTasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectStartableTasks in body.
     */
    @GetMapping("/project-startable-tasks")
    public ResponseEntity<List<ProjectStartableTask>> getAllProjectStartableTasks(ProjectStartableTaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectStartableTasks by criteria: {}", criteria);
        Page<ProjectStartableTask> page = projectStartableTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-startable-tasks/count} : count all the projectStartableTasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-startable-tasks/count")
    public ResponseEntity<Long> countProjectStartableTasks(ProjectStartableTaskCriteria criteria) {
        log.debug("REST request to count ProjectStartableTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectStartableTaskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-startable-tasks/:id} : get the "id" projectStartableTask.
     *
     * @param id the id of the projectStartableTask to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectStartableTask, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-startable-tasks/{id}")
    public ResponseEntity<ProjectStartableTask> getProjectStartableTask(@PathVariable Long id) {
        log.debug("REST request to get ProjectStartableTask : {}", id);
        Optional<ProjectStartableTask> projectStartableTask = projectStartableTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectStartableTask);
    }

    /**
     * {@code DELETE  /project-startable-tasks/:id} : delete the "id" projectStartableTask.
     *
     * @param id the id of the projectStartableTask to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-startable-tasks/{id}")
    public ResponseEntity<Void> deleteProjectStartableTask(@PathVariable Long id) {
        log.debug("REST request to delete ProjectStartableTask : {}", id);
        projectStartableTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
