package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTaskStatusTrakingFile;
import com.mshz.microproject.service.ProjectTaskStatusTrakingFileService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskStatusTrakingFileCriteria;
import com.mshz.microproject.service.ProjectTaskStatusTrakingFileQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTaskStatusTrakingFile}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskStatusTrakingFileResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskStatusTrakingFileResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTaskStatusTrakingFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskStatusTrakingFileService projectTaskStatusTrakingFileService;

    private final ProjectTaskStatusTrakingFileQueryService projectTaskStatusTrakingFileQueryService;

    public ProjectTaskStatusTrakingFileResource(ProjectTaskStatusTrakingFileService projectTaskStatusTrakingFileService, ProjectTaskStatusTrakingFileQueryService projectTaskStatusTrakingFileQueryService) {
        this.projectTaskStatusTrakingFileService = projectTaskStatusTrakingFileService;
        this.projectTaskStatusTrakingFileQueryService = projectTaskStatusTrakingFileQueryService;
    }

    /**
     * {@code POST  /project-task-status-traking-files} : Create a new projectTaskStatusTrakingFile.
     *
     * @param projectTaskStatusTrakingFile the projectTaskStatusTrakingFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTaskStatusTrakingFile, or with status {@code 400 (Bad Request)} if the projectTaskStatusTrakingFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-task-status-traking-files")
    public ResponseEntity<ProjectTaskStatusTrakingFile> createProjectTaskStatusTrakingFile(@Valid @RequestBody ProjectTaskStatusTrakingFile projectTaskStatusTrakingFile) throws URISyntaxException {
        log.debug("REST request to save ProjectTaskStatusTrakingFile : {}", projectTaskStatusTrakingFile);
        if (projectTaskStatusTrakingFile.getId() != null) {
            throw new BadRequestAlertException("A new projectTaskStatusTrakingFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTaskStatusTrakingFile result = projectTaskStatusTrakingFileService.save(projectTaskStatusTrakingFile);
        return ResponseEntity.created(new URI("/api/project-task-status-traking-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-task-status-traking-files} : Updates an existing projectTaskStatusTrakingFile.
     *
     * @param projectTaskStatusTrakingFile the projectTaskStatusTrakingFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTaskStatusTrakingFile,
     * or with status {@code 400 (Bad Request)} if the projectTaskStatusTrakingFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTaskStatusTrakingFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-task-status-traking-files")
    public ResponseEntity<ProjectTaskStatusTrakingFile> updateProjectTaskStatusTrakingFile(@Valid @RequestBody ProjectTaskStatusTrakingFile projectTaskStatusTrakingFile) throws URISyntaxException {
        log.debug("REST request to update ProjectTaskStatusTrakingFile : {}", projectTaskStatusTrakingFile);
        if (projectTaskStatusTrakingFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTaskStatusTrakingFile result = projectTaskStatusTrakingFileService.save(projectTaskStatusTrakingFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTaskStatusTrakingFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-task-status-traking-files} : get all the projectTaskStatusTrakingFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTaskStatusTrakingFiles in body.
     */
    @GetMapping("/project-task-status-traking-files")
    public ResponseEntity<List<ProjectTaskStatusTrakingFile>> getAllProjectTaskStatusTrakingFiles(ProjectTaskStatusTrakingFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTaskStatusTrakingFiles by criteria: {}", criteria);
        Page<ProjectTaskStatusTrakingFile> page = projectTaskStatusTrakingFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-task-status-traking-files/count} : count all the projectTaskStatusTrakingFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-task-status-traking-files/count")
    public ResponseEntity<Long> countProjectTaskStatusTrakingFiles(ProjectTaskStatusTrakingFileCriteria criteria) {
        log.debug("REST request to count ProjectTaskStatusTrakingFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskStatusTrakingFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-task-status-traking-files/:id} : get the "id" projectTaskStatusTrakingFile.
     *
     * @param id the id of the projectTaskStatusTrakingFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTaskStatusTrakingFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-task-status-traking-files/{id}")
    public ResponseEntity<ProjectTaskStatusTrakingFile> getProjectTaskStatusTrakingFile(@PathVariable Long id) {
        log.debug("REST request to get ProjectTaskStatusTrakingFile : {}", id);
        Optional<ProjectTaskStatusTrakingFile> projectTaskStatusTrakingFile = projectTaskStatusTrakingFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTaskStatusTrakingFile);
    }

    /**
     * {@code DELETE  /project-task-status-traking-files/:id} : delete the "id" projectTaskStatusTrakingFile.
     *
     * @param id the id of the projectTaskStatusTrakingFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-task-status-traking-files/{id}")
    public ResponseEntity<Void> deleteProjectTaskStatusTrakingFile(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTaskStatusTrakingFile : {}", id);
        projectTaskStatusTrakingFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
