package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectCommentFile;
import com.mshz.microproject.service.ProjectCommentFileService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectCommentFileCriteria;
import com.mshz.microproject.service.ProjectCommentFileQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectCommentFile}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCommentFileResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentFileResource.class);

    private static final String ENTITY_NAME = "microprojectProjectCommentFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCommentFileService projectCommentFileService;

    private final ProjectCommentFileQueryService projectCommentFileQueryService;

    public ProjectCommentFileResource(ProjectCommentFileService projectCommentFileService, ProjectCommentFileQueryService projectCommentFileQueryService) {
        this.projectCommentFileService = projectCommentFileService;
        this.projectCommentFileQueryService = projectCommentFileQueryService;
    }

    /**
     * {@code POST  /project-comment-files} : Create a new projectCommentFile.
     *
     * @param projectCommentFile the projectCommentFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCommentFile, or with status {@code 400 (Bad Request)} if the projectCommentFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-comment-files")
    public ResponseEntity<ProjectCommentFile> createProjectCommentFile(@Valid @RequestBody ProjectCommentFile projectCommentFile) throws URISyntaxException {
        log.debug("REST request to save ProjectCommentFile : {}", projectCommentFile);
        if (projectCommentFile.getId() != null) {
            throw new BadRequestAlertException("A new projectCommentFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectCommentFile result = projectCommentFileService.save(projectCommentFile);
        return ResponseEntity.created(new URI("/api/project-comment-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-comment-files} : Updates an existing projectCommentFile.
     *
     * @param projectCommentFile the projectCommentFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCommentFile,
     * or with status {@code 400 (Bad Request)} if the projectCommentFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCommentFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-comment-files")
    public ResponseEntity<ProjectCommentFile> updateProjectCommentFile(@Valid @RequestBody ProjectCommentFile projectCommentFile) throws URISyntaxException {
        log.debug("REST request to update ProjectCommentFile : {}", projectCommentFile);
        if (projectCommentFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectCommentFile result = projectCommentFileService.save(projectCommentFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectCommentFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-comment-files} : get all the projectCommentFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectCommentFiles in body.
     */
    @GetMapping("/project-comment-files")
    public ResponseEntity<List<ProjectCommentFile>> getAllProjectCommentFiles(ProjectCommentFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectCommentFiles by criteria: {}", criteria);
        Page<ProjectCommentFile> page = projectCommentFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-comment-files/count} : count all the projectCommentFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-comment-files/count")
    public ResponseEntity<Long> countProjectCommentFiles(ProjectCommentFileCriteria criteria) {
        log.debug("REST request to count ProjectCommentFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectCommentFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-comment-files/:id} : get the "id" projectCommentFile.
     *
     * @param id the id of the projectCommentFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCommentFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-comment-files/{id}")
    public ResponseEntity<ProjectCommentFile> getProjectCommentFile(@PathVariable Long id) {
        log.debug("REST request to get ProjectCommentFile : {}", id);
        Optional<ProjectCommentFile> projectCommentFile = projectCommentFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCommentFile);
    }

    /**
     * {@code DELETE  /project-comment-files/:id} : delete the "id" projectCommentFile.
     *
     * @param id the id of the projectCommentFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-comment-files/{id}")
    public ResponseEntity<Void> deleteProjectCommentFile(@PathVariable Long id) {
        log.debug("REST request to delete ProjectCommentFile : {}", id);
        projectCommentFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
