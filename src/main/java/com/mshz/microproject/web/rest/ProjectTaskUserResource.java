package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskUser;
import com.mshz.microproject.service.ProjectTaskUserService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskUserCriteria;
import com.mshz.microproject.service.ProjectTaskUserQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskUser}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskUserResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskUserResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskUserService projectTaskUserService;

    private final ProjectTaskUserQueryService projectTaskUserQueryService;

    public ProjectTaskUserResource(ProjectTaskUserService projectTaskUserService, ProjectTaskUserQueryService projectTaskUserQueryService) {
        this.projectTaskUserService = projectTaskUserService;
        this.projectTaskUserQueryService = projectTaskUserQueryService;
    }

    /**
     * {@code POST  /project-task-users} : Create a new projectTaskUser.
     *
     * @param projectTaskUser the projectTaskUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskUser, or with status {@code 400 (Bad Request)} if the projectTaskUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-users")
    public ResponseEntity<ProjectTaskUser> createProjectTaskUser(@Valid @RequestBody ProjectTaskUser projectTaskUser) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskUser : {}", projectTaskUser);
        if (projectTaskUser.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskUser result = projectTaskUserService.save(projectTaskUser);
        return ResponseEntity.created(new URI("/api/project-task-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-users} : Updates an existing projectTaskUser.
     *
     * @param projectTaskUser the projectTaskUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskUser,
     * or with status {@code 400 (Bad Request)} if the projectTaskUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-users")
    public ResponseEntity<ProjectTaskUser> updateProjectTaskUser(@Valid @RequestBody ProjectTaskUser projectTaskUser) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskUser : {}", projectTaskUser);
        if (projectTaskUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskUser result = projectTaskUserService.save(projectTaskUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-users} : get all the projectTaskUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskUsers in body.
     */
    @GetMapping("/project-task-users")
    public ResponseEntity<List<ProjectTaskUser>> getAllProjectTaskUsers(ProjectTaskUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskUsers by criteria: {}", criteria);
        Page<ProjectTaskUser> page = projectTaskUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-users/count} : count all the projectTaskUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-users/count")
    public ResponseEntity<Long> countProjectTaskUsers(ProjectTaskUserCriteria criteria) {
        log.debug("REST request to count ProjectTaskUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-users/:id} : get the "id" projectTaskUser.
     *
     * @param id the id of the projectTaskUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-users/{id}")
    public ResponseEntity<ProjectTaskUser> getProjectTaskUser(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskUser : {}", id);
        Optional<ProjectTaskUser> projectTaskUser = projectTaskUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskUser);
    }

    /**
     * {@code DELETE  /project-task-users/:id} : delete the "id" projectTaskUser.
     *
     * @param id the id of the projectTaskUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-users/{id}")
    public ResponseEntity<Void> deleteProjectTaskUser(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskUser : {}", id);
        projectTaskUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
