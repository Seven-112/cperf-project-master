package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectCondNode;
import com.mshz.microproject.service.ProjectCondNodeService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectCondNodeCriteria;
import com.mshz.microproject.service.ProjectCondNodeQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectCondNode}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCondNodeResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCondNodeResource.class);

    private static final String ENTITY_NAME = "microprojectProjectCondNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCondNodeService projectCondNodeService;

    private final ProjectCondNodeQueryService projectCondNodeQueryService;

    public ProjectCondNodeResource(ProjectCondNodeService projectCondNodeService, ProjectCondNodeQueryService projectCondNodeQueryService) {
        this.projectCondNodeService = projectCondNodeService;
        this.projectCondNodeQueryService = projectCondNodeQueryService;
    }

    /**
     * {@code POST  /project-cond-nodes} : Create a new projectCondNode.
     *
     * @param projectCondNode the projectCondNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCondNode, or with status {@code 400 (Bad Request)} if the projectCondNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-cond-nodes")
    public ResponseEntity<ProjectCondNode> createProjectCondNode(@RequestBody ProjectCondNode projectCondNode) throws URISyntaxException {
        log.debug("REST request to save ProjectCondNode : {}", projectCondNode);
        if (projectCondNode.getId() != null) {
            throw new BadRequestAlertException("A new projectCondNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectCondNode result = projectCondNodeService.save(projectCondNode);
        return ResponseEntity.created(new URI("/api/project-cond-nodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-cond-nodes} : Updates an existing projectCondNode.
     *
     * @param projectCondNode the projectCondNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCondNode,
     * or with status {@code 400 (Bad Request)} if the projectCondNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCondNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-cond-nodes")
    public ResponseEntity<ProjectCondNode> updateProjectCondNode(@RequestBody ProjectCondNode projectCondNode) throws URISyntaxException {
        log.debug("REST request to update ProjectCondNode : {}", projectCondNode);
        if (projectCondNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectCondNode result = projectCondNodeService.save(projectCondNode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectCondNode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-cond-nodes} : get all the projectCondNodes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectCondNodes in body.
     */
    @GetMapping("/project-cond-nodes")
    public ResponseEntity<List<ProjectCondNode>> getAllProjectCondNodes(ProjectCondNodeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectCondNodes by criteria: {}", criteria);
        Page<ProjectCondNode> page = projectCondNodeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-cond-nodes/count} : count all the projectCondNodes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-cond-nodes/count")
    public ResponseEntity<Long> countProjectCondNodes(ProjectCondNodeCriteria criteria) {
        log.debug("REST request to count ProjectCondNodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectCondNodeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-cond-nodes/:id} : get the "id" projectCondNode.
     *
     * @param id the id of the projectCondNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCondNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-cond-nodes/{id}")
    public ResponseEntity<ProjectCondNode> getProjectCondNode(@PathVariable Long id) {
        log.debug("REST request to get ProjectCondNode : {}", id);
        Optional<ProjectCondNode> projectCondNode = projectCondNodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCondNode);
    }

    /**
     * {@code DELETE  /project-cond-nodes/:id} : delete the "id" projectCondNode.
     *
     * @param id the id of the projectCondNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-cond-nodes/{id}")
    public ResponseEntity<Void> deleteProjectCondNode(@PathVariable Long id) {
        log.debug("REST request to delete ProjectCondNode : {}", id);
        projectCondNodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
