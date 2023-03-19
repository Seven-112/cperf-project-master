package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectCategory;
import com.mshz.microproject.service.ProjectCategoryService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectCategoryCriteria;
import com.mshz.microproject.service.ProjectCategoryQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectCategory}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCategoryResource.class);

    private static final String ENTITY_NAME = "microprojectProjectCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCategoryService projectCategoryService;

    private final ProjectCategoryQueryService projectCategoryQueryService;

    public ProjectCategoryResource(ProjectCategoryService projectCategoryService, ProjectCategoryQueryService projectCategoryQueryService) {
        this.projectCategoryService = projectCategoryService;
        this.projectCategoryQueryService = projectCategoryQueryService;
    }

    /**
     * {@code POST  /project-categories} : Create a new projectCategory.
     *
     * @param projectCategory the projectCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCategory, or with status {@code 400 (Bad Request)} if the projectCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-categories")
    public ResponseEntity<ProjectCategory> createProjectCategory(@Valid @RequestBody ProjectCategory projectCategory) throws URISyntaxException {
        log.debug("REST request to save ProjectCategory : {}", projectCategory);
        if (projectCategory.getId() != null) {
            throw new BadRequestAlertException("A new projectCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectCategory result = projectCategoryService.save(projectCategory);
        return ResponseEntity.created(new URI("/api/project-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-categories} : Updates an existing projectCategory.
     *
     * @param projectCategory the projectCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCategory,
     * or with status {@code 400 (Bad Request)} if the projectCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-categories")
    public ResponseEntity<ProjectCategory> updateProjectCategory(@Valid @RequestBody ProjectCategory projectCategory) throws URISyntaxException {
        log.debug("REST request to update ProjectCategory : {}", projectCategory);
        if (projectCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectCategory result = projectCategoryService.save(projectCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-categories} : get all the projectCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectCategories in body.
     */
    @GetMapping("/project-categories")
    public ResponseEntity<List<ProjectCategory>> getAllProjectCategories(ProjectCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectCategories by criteria: {}", criteria);
        Page<ProjectCategory> page = projectCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-categories/count} : count all the projectCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-categories/count")
    public ResponseEntity<Long> countProjectCategories(ProjectCategoryCriteria criteria) {
        log.debug("REST request to count ProjectCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-categories/:id} : get the "id" projectCategory.
     *
     * @param id the id of the projectCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-categories/{id}")
    public ResponseEntity<ProjectCategory> getProjectCategory(@PathVariable Long id) {
        log.debug("REST request to get ProjectCategory : {}", id);
        Optional<ProjectCategory> projectCategory = projectCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCategory);
    }

    /**
     * {@code DELETE  /project-categories/:id} : delete the "id" projectCategory.
     *
     * @param id the id of the projectCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-categories/{id}")
    public ResponseEntity<Void> deleteProjectCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProjectCategory : {}", id);
        projectCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
