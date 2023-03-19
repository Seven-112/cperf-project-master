package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectItemCheckJustificationFile;
import com.mshz.microproject.service.ProjectItemCheckJustificationFileService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectItemCheckJustificationFileCriteria;
import com.mshz.microproject.service.ProjectItemCheckJustificationFileQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectItemCheckJustificationFile}.
 */
@RestController
@RequestMapping("/api")
public class ProjectItemCheckJustificationFileResource {

    private final Logger log = LoggerFactory.getLogger(ProjectItemCheckJustificationFileResource.class);

    private static final String ENTITY_NAME = "microprojectProjectItemCheckJustificationFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectItemCheckJustificationFileService projectItemCheckJustificationFileService;

    private final ProjectItemCheckJustificationFileQueryService projectItemCheckJustificationFileQueryService;

    public ProjectItemCheckJustificationFileResource(ProjectItemCheckJustificationFileService projectItemCheckJustificationFileService, ProjectItemCheckJustificationFileQueryService projectItemCheckJustificationFileQueryService) {
        this.projectItemCheckJustificationFileService = projectItemCheckJustificationFileService;
        this.projectItemCheckJustificationFileQueryService = projectItemCheckJustificationFileQueryService;
    }

    /**
     * {@code POST  /project-item-check-justification-files} : Create a new projectItemCheckJustificationFile.
     *
     * @param projectItemCheckJustificationFile the projectItemCheckJustificationFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectItemCheckJustificationFile, or with status {@code 400 (Bad Request)} if the projectItemCheckJustificationFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-item-check-justification-files")
    public ResponseEntity<ProjectItemCheckJustificationFile> createProjectItemCheckJustificationFile(@Valid @RequestBody ProjectItemCheckJustificationFile projectItemCheckJustificationFile) throws URISyntaxException {
        log.debug("REST request to save ProjectItemCheckJustificationFile : {}", projectItemCheckJustificationFile);
        if (projectItemCheckJustificationFile.getId() != null) {
            throw new BadRequestAlertException("A new projectItemCheckJustificationFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectItemCheckJustificationFile result = projectItemCheckJustificationFileService.save(projectItemCheckJustificationFile);
        return ResponseEntity.created(new URI("/api/project-item-check-justification-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-item-check-justification-files} : Updates an existing projectItemCheckJustificationFile.
     *
     * @param projectItemCheckJustificationFile the projectItemCheckJustificationFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectItemCheckJustificationFile,
     * or with status {@code 400 (Bad Request)} if the projectItemCheckJustificationFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectItemCheckJustificationFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-item-check-justification-files")
    public ResponseEntity<ProjectItemCheckJustificationFile> updateProjectItemCheckJustificationFile(@Valid @RequestBody ProjectItemCheckJustificationFile projectItemCheckJustificationFile) throws URISyntaxException {
        log.debug("REST request to update ProjectItemCheckJustificationFile : {}", projectItemCheckJustificationFile);
        if (projectItemCheckJustificationFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectItemCheckJustificationFile result = projectItemCheckJustificationFileService.save(projectItemCheckJustificationFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectItemCheckJustificationFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-item-check-justification-files} : get all the projectItemCheckJustificationFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectItemCheckJustificationFiles in body.
     */
    @GetMapping("/project-item-check-justification-files")
    public ResponseEntity<List<ProjectItemCheckJustificationFile>> getAllProjectItemCheckJustificationFiles(ProjectItemCheckJustificationFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectItemCheckJustificationFiles by criteria: {}", criteria);
        Page<ProjectItemCheckJustificationFile> page = projectItemCheckJustificationFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-item-check-justification-files/count} : count all the projectItemCheckJustificationFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-item-check-justification-files/count")
    public ResponseEntity<Long> countProjectItemCheckJustificationFiles(ProjectItemCheckJustificationFileCriteria criteria) {
        log.debug("REST request to count ProjectItemCheckJustificationFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectItemCheckJustificationFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-item-check-justification-files/:id} : get the "id" projectItemCheckJustificationFile.
     *
     * @param id the id of the projectItemCheckJustificationFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectItemCheckJustificationFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-item-check-justification-files/{id}")
    public ResponseEntity<ProjectItemCheckJustificationFile> getProjectItemCheckJustificationFile(@PathVariable Long id) {
        log.debug("REST request to get ProjectItemCheckJustificationFile : {}", id);
        Optional<ProjectItemCheckJustificationFile> projectItemCheckJustificationFile = projectItemCheckJustificationFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectItemCheckJustificationFile);
    }

    /**
     * {@code DELETE  /project-item-check-justification-files/:id} : delete the "id" projectItemCheckJustificationFile.
     *
     * @param id the id of the projectItemCheckJustificationFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-item-check-justification-files/{id}")
    public ResponseEntity<Void> deleteProjectItemCheckJustificationFile(@PathVariable Long id) {
        log.debug("REST request to delete ProjectItemCheckJustificationFile : {}", id);
        projectItemCheckJustificationFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
