package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskItem;
import com.mshz.microproject.service.ProjectTaskItemService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskItemCriteria;
import com.mshz.microproject.service.ProjectTaskItemQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskItem}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskItemResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskItemResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskItemService projectTaskItemService;

    private final ProjectTaskItemQueryService projectTaskItemQueryService;

    public ProjectTaskItemResource(ProjectTaskItemService projectTaskItemService, ProjectTaskItemQueryService projectTaskItemQueryService) {
        this.projectTaskItemService = projectTaskItemService;
        this.projectTaskItemQueryService = projectTaskItemQueryService;
    }

    /**
     * {@code POST  /project-task-items} : Create a new projectTaskItem.
     *
     * @param projectTaskItem the projectTaskItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskItem, or with status {@code 400 (Bad Request)} if the projectTaskItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-items")
    public ResponseEntity<ProjectTaskItem> createProjectTaskItem(@Valid @RequestBody ProjectTaskItem projectTaskItem) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskItem : {}", projectTaskItem);
        if (projectTaskItem.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskItem result = projectTaskItemService.save(projectTaskItem);
        return ResponseEntity.created(new URI("/api/project-task-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-items} : Updates an existing projectTaskItem.
     *
     * @param projectTaskItem the projectTaskItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskItem,
     * or with status {@code 400 (Bad Request)} if the projectTaskItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-items")
    public ResponseEntity<ProjectTaskItem> updateProjectTaskItem(@Valid @RequestBody ProjectTaskItem projectTaskItem) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskItem : {}", projectTaskItem);
        if (projectTaskItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskItem result = projectTaskItemService.save(projectTaskItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-items} : get all the projectTaskItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskItems in body.
     */
    @GetMapping("/project-task-items")
    public ResponseEntity<List<ProjectTaskItem>> getAllProjectTaskItems(ProjectTaskItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskItems by criteria: {}", criteria);
        Page<ProjectTaskItem> page = projectTaskItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-items/count} : count all the projectTaskItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-items/count")
    public ResponseEntity<Long> countProjectTaskItems(ProjectTaskItemCriteria criteria) {
        log.debug("REST request to count ProjectTaskItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-items/:id} : get the "id" projectTaskItem.
     *
     * @param id the id of the projectTaskItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-items/{id}")
    public ResponseEntity<ProjectTaskItem> getProjectTaskItem(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskItem : {}", id);
        Optional<ProjectTaskItem> projectTaskItem = projectTaskItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskItem);
    }

    /**
     * {@code DELETE  /project-task-items/:id} : delete the "id" projectTaskItem.
     *
     * @param id the id of the projectTaskItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-items/{id}")
    public ResponseEntity<Void> deleteProjectTaskItem(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskItem : {}", id);
        projectTaskItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
