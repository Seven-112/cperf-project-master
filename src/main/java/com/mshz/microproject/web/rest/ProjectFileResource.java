package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectFile;
import com.mshz.microproject.service.ProjectFileService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectFileCriteria;
import com.mshz.microproject.service.ProjectFileQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectFile}.
 */
@RestController
@RequestMapping("/api")
public class ProjectFileResource {

    private final Logger log = LoggerFactory.getLogger(ProjectFileResource.class);

    private static final String ENTITY_NAME = "microprojectProjectFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectFileService projectFileService;

    private final ProjectFileQueryService projectFileQueryService;

    public ProjectFileResource(ProjectFileService projectFileService, ProjectFileQueryService projectFileQueryService) {
        this.projectFileService = projectFileService;
        this.projectFileQueryService = projectFileQueryService;
    }

    /**
     * {@code POST  /project-files} : Create a new projectFile.
     *
     * @param projectFile the projectFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectFile, or with status {@code 400 (Bad Request)} if the projectFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-files")
    public ResponseEntity<ProjectFile> createProjectFile(@RequestBody ProjectFile projectFile) throws URISyntaxException {
        log.debug("REST request to save ProjectFile : {}", projectFile);
        if (projectFile.getId() != null) {
            throw new BadRequestAlertException("A new projectFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectFile result = projectFileService.save(projectFile);
        return ResponseEntity.created(new URI("/api/project-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-files} : Updates an existing projectFile.
     *
     * @param projectFile the projectFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectFile,
     * or with status {@code 400 (Bad Request)} if the projectFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-files")
    public ResponseEntity<ProjectFile> updateProjectFile(@RequestBody ProjectFile projectFile) throws URISyntaxException {
        log.debug("REST request to update ProjectFile : {}", projectFile);
        if (projectFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectFile result = projectFileService.save(projectFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-files} : get all the projectFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectFiles in body.
     */
    @GetMapping("/project-files")
    public ResponseEntity<List<ProjectFile>> getAllProjectFiles(ProjectFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectFiles by criteria: {}", criteria);
        Page<ProjectFile> page = projectFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-files/count} : count all the projectFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-files/count")
    public ResponseEntity<Long> countProjectFiles(ProjectFileCriteria criteria) {
        log.debug("REST request to count ProjectFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-files/:id} : get the "id" projectFile.
     *
     * @param id the id of the projectFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-files/{id}")
    public ResponseEntity<ProjectFile> getProjectFile(@PathVariable Long id) {
        log.debug("REST request to get ProjectFile : {}", id);
        Optional<ProjectFile> projectFile = projectFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectFile);
    }

    /**
     * {@code DELETE  /project-files/:id} : delete the "id" projectFile.
     *
     * @param id the id of the projectFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-files/{id}")
    public ResponseEntity<Void> deleteProjectFile(@PathVariable Long id) {
        log.debug("REST request to delete ProjectFile : {}", id);
        projectFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
