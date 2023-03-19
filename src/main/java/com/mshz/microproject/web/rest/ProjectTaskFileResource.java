package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskFile;
import com.mshz.microproject.service.ProjectTaskFileService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskFileCriteria;
import com.mshz.microproject.service.ProjectTaskFileQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskFile}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskFileResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskFileResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskFileService projectTaskFileService;

    private final ProjectTaskFileQueryService projectTaskFileQueryService;

    public ProjectTaskFileResource(ProjectTaskFileService projectTaskFileService, ProjectTaskFileQueryService projectTaskFileQueryService) {
        this.projectTaskFileService = projectTaskFileService;
        this.projectTaskFileQueryService = projectTaskFileQueryService;
    }

    /**
     * {@code POST  /project-task-files} : Create a new projectTaskFile.
     *
     * @param projectTaskFile the projectTaskFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskFile, or with status {@code 400 (Bad Request)} if the projectTaskFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-files")
    public ResponseEntity<ProjectTaskFile> createProjectTaskFile(@RequestBody ProjectTaskFile projectTaskFile) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskFile : {}", projectTaskFile);
        if (projectTaskFile.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskFile result = projectTaskFileService.save(projectTaskFile);
        return ResponseEntity.created(new URI("/api/project-task-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-files} : Updates an existing projectTaskFile.
     *
     * @param projectTaskFile the projectTaskFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskFile,
     * or with status {@code 400 (Bad Request)} if the projectTaskFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-files")
    public ResponseEntity<ProjectTaskFile> updateProjectTaskFile(@RequestBody ProjectTaskFile projectTaskFile) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskFile : {}", projectTaskFile);
        if (projectTaskFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskFile result = projectTaskFileService.save(projectTaskFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-files} : get all the projectTaskFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskFiles in body.
     */
    @GetMapping("/project-task-files")
    public ResponseEntity<List<ProjectTaskFile>> getAllProjectTaskFiles(ProjectTaskFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskFiles by criteria: {}", criteria);
        Page<ProjectTaskFile> page = projectTaskFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-files/count} : count all the projectTaskFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-files/count")
    public ResponseEntity<Long> countProjectTaskFiles(ProjectTaskFileCriteria criteria) {
        log.debug("REST request to count ProjectTaskFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-files/:id} : get the "id" projectTaskFile.
     *
     * @param id the id of the projectTaskFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-files/{id}")
    public ResponseEntity<ProjectTaskFile> getProjectTaskFile(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskFile : {}", id);
        Optional<ProjectTaskFile> projectTaskFile = projectTaskFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskFile);
    }

    /**
     * {@code DELETE  /project-task-files/:id} : delete the "id" projectTaskFile.
     *
     * @param id the id of the projectTaskFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-files/{id}")
    public ResponseEntity<Void> deleteProjectTaskFile(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskFile : {}", id);
        projectTaskFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
