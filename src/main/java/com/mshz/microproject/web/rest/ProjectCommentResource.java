package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectComment;
import com.mshz.microproject.service.ProjectCommentService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectCommentCriteria;
import com.mshz.microproject.service.ProjectCommentQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectComment}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCommentResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentResource.class);

    private static final String ENTITY_NAME = "microprojectProjectComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCommentService projectCommentService;

    private final ProjectCommentQueryService projectCommentQueryService;

    public ProjectCommentResource(ProjectCommentService projectCommentService, ProjectCommentQueryService projectCommentQueryService) {
        this.projectCommentService = projectCommentService;
        this.projectCommentQueryService = projectCommentQueryService;
    }

    /**
     * {@code POST  /project-comments} : Create a new projectComment.
     *
     * @param projectComment the projectComment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectComment, or with status {@code 400 (Bad Request)} if the projectComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-comments")
    public ResponseEntity<ProjectComment> createProjectComment(@Valid @RequestBody ProjectComment projectComment) throws URISyntaxException {
        log.debug("REST request to save ProjectComment : {}", projectComment);
        if (projectComment.getId() != null) {
            throw new BadRequestAlertException("A new projectComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectComment result = projectCommentService.save(projectComment);
        return ResponseEntity.created(new URI("/api/project-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-comments} : Updates an existing projectComment.
     *
     * @param projectComment the projectComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectComment,
     * or with status {@code 400 (Bad Request)} if the projectComment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectComment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-comments")
    public ResponseEntity<ProjectComment> updateProjectComment(@Valid @RequestBody ProjectComment projectComment) throws URISyntaxException {
        log.debug("REST request to update ProjectComment : {}", projectComment);
        if (projectComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectComment result = projectCommentService.save(projectComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectComment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-comments} : get all the projectComments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectComments in body.
     */
    @GetMapping("/project-comments")
    public ResponseEntity<List<ProjectComment>> getAllProjectComments(ProjectCommentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectComments by criteria: {}", criteria);
        Page<ProjectComment> page = projectCommentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-comments/count} : count all the projectComments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-comments/count")
    public ResponseEntity<Long> countProjectComments(ProjectCommentCriteria criteria) {
        log.debug("REST request to count ProjectComments by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectCommentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-comments/:id} : get the "id" projectComment.
     *
     * @param id the id of the projectComment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectComment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-comments/{id}")
    public ResponseEntity<ProjectComment> getProjectComment(@PathVariable Long id) {
        log.debug("REST request to get ProjectComment : {}", id);
        Optional<ProjectComment> projectComment = projectCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectComment);
    }

    /**
     * {@code DELETE  /project-comments/:id} : delete the "id" projectComment.
     *
     * @param id the id of the projectComment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-comments/{id}")
    public ResponseEntity<Void> deleteProjectComment(@PathVariable Long id) {
        log.debug("REST request to delete ProjectComment : {}", id);
        projectCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
