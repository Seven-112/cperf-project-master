package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectEdgeInfo;
import com.mshz.microproject.service.ProjectEdgeInfoService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectEdgeInfoCriteria;
import com.mshz.microproject.service.ProjectEdgeInfoQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectEdgeInfo}.
 */
@RestController
@RequestMapping("/api")
public class ProjectEdgeInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProjectEdgeInfoResource.class);

    private static final String ENTITY_NAME = "microprojectProjectEdgeInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectEdgeInfoService projectEdgeInfoService;

    private final ProjectEdgeInfoQueryService projectEdgeInfoQueryService;

    public ProjectEdgeInfoResource(ProjectEdgeInfoService projectEdgeInfoService, ProjectEdgeInfoQueryService projectEdgeInfoQueryService) {
        this.projectEdgeInfoService = projectEdgeInfoService;
        this.projectEdgeInfoQueryService = projectEdgeInfoQueryService;
    }

    /**
     * {@code POST  /project-edge-infos} : Create a new projectEdgeInfo.
     *
     * @param projectEdgeInfo the projectEdgeInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectEdgeInfo, or with status {@code 400 (Bad Request)} if the projectEdgeInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-edge-infos")
    public ResponseEntity<ProjectEdgeInfo> createProjectEdgeInfo(@RequestBody ProjectEdgeInfo projectEdgeInfo) throws URISyntaxException {
        log.debug("REST request to save ProjectEdgeInfo : {}", projectEdgeInfo);
        if (projectEdgeInfo.getId() != null) {
            throw new BadRequestAlertException("A new projectEdgeInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectEdgeInfo result = projectEdgeInfoService.save(projectEdgeInfo);
        return ResponseEntity.created(new URI("/api/project-edge-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-edge-infos} : Updates an existing projectEdgeInfo.
     *
     * @param projectEdgeInfo the projectEdgeInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectEdgeInfo,
     * or with status {@code 400 (Bad Request)} if the projectEdgeInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectEdgeInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-edge-infos")
    public ResponseEntity<ProjectEdgeInfo> updateProjectEdgeInfo(@RequestBody ProjectEdgeInfo projectEdgeInfo) throws URISyntaxException {
        log.debug("REST request to update ProjectEdgeInfo : {}", projectEdgeInfo);
        if (projectEdgeInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectEdgeInfo result = projectEdgeInfoService.save(projectEdgeInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectEdgeInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-edge-infos} : get all the projectEdgeInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectEdgeInfos in body.
     */
    @GetMapping("/project-edge-infos")
    public ResponseEntity<List<ProjectEdgeInfo>> getAllProjectEdgeInfos(ProjectEdgeInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectEdgeInfos by criteria: {}", criteria);
        Page<ProjectEdgeInfo> page = projectEdgeInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-edge-infos/count} : count all the projectEdgeInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-edge-infos/count")
    public ResponseEntity<Long> countProjectEdgeInfos(ProjectEdgeInfoCriteria criteria) {
        log.debug("REST request to count ProjectEdgeInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectEdgeInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-edge-infos/:id} : get the "id" projectEdgeInfo.
     *
     * @param id the id of the projectEdgeInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectEdgeInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-edge-infos/{id}")
    public ResponseEntity<ProjectEdgeInfo> getProjectEdgeInfo(@PathVariable Long id) {
        log.debug("REST request to get ProjectEdgeInfo : {}", id);
        Optional<ProjectEdgeInfo> projectEdgeInfo = projectEdgeInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectEdgeInfo);
    }

    /**
     * {@code DELETE  /project-edge-infos/:id} : delete the "id" projectEdgeInfo.
     *
     * @param id the id of the projectEdgeInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-edge-infos/{id}")
    public ResponseEntity<Void> deleteProjectEdgeInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProjectEdgeInfo : {}", id);
        projectEdgeInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
