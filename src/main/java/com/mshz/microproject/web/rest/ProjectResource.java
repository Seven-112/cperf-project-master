package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.projection.ChronoUtil;
import com.mshz.microproject.domain.projection.ProjectTimeLineGantt;
import com.mshz.microproject.service.ProjectService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectCriteria;
import com.mshz.microproject.service.ProjectQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.mshz.microproject.domain.projection.ReactFrappeGanttUtil;

/**
 * REST controller for managing {@link com.mshz.microproject.domain.Project}.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    private static final String ENTITY_NAME = "microprojectProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectService projectService;

    private final ProjectQueryService projectQueryService;

    public ProjectResource(ProjectService projectService, ProjectQueryService projectQueryService) {
        this.projectService = projectService;
        this.projectQueryService = projectQueryService;
    }

    /**
     * {@code POST  /projects} : Create a new project.
     *
     * @param project the project to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new project, or with status {@code 400 (Bad Request)} if the project has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to save Project : {}", project);
        if (project.getId() != null) {
            throw new BadRequestAlertException("A new project cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Project result = projectService.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projects} : Updates an existing project.
     *
     * @param project the project to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated project,
     * or with status {@code 400 (Bad Request)} if the project is not valid,
     * or with status {@code 500 (Internal Server Error)} if the project couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projects")
    public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to update Project : {}", project);
        if (project.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Project result = projectService.save(project);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, project.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /projects} : get all the projects.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projects in body.
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects(ProjectCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Projects by criteria: {}", criteria);
        Page<Project> page = projectQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /projects/count} : count all the projects.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/projects/count")
    public ResponseEntity<Long> countProjects(ProjectCriteria criteria) {
        log.debug("REST request to count Projects by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /projects/:id} : get the "id" project.
     *
     * @param id the id of the project to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the project, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Optional<Project> project = projectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(project);
    }

    /**
     * {@code DELETE  /projects/:id} : delete the "id" project.
     *
     * @param id the id of the project to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/projects/getReactTimeLineGanttDataByProjectId/{projectId}")
    public ResponseEntity<List<ProjectTimeLineGantt>> getReactTimeLineGanttDataByProjectId(@PathVariable Long projectId){
        List<ProjectTimeLineGantt> result = projectService.getReactTimeLineGanttDataByProjectId(projectId, new ArrayList<>());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/projects/getChronoUtil/{projectId}")
    public ResponseEntity<ChronoUtil> getChronoUtil(@PathVariable Long projectId){
        log.debug("get project chrono util by id {}", projectId);
        ChronoUtil result = projectService.getChronoUtil(projectId);
        if(result == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("/projects/getGanntData/{projectId}")
    public ResponseEntity<List<ReactFrappeGanttUtil>> getGanntData(@PathVariable Long projectId){
        log.debug("get project gant data util by id {}", projectId);
        List<ReactFrappeGanttUtil> result = projectService.getProjectGanntData(projectId);
        if(result == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok().body(result);
    }

    @GetMapping("projects/getSimillarProjects/{projectId}")
    public ResponseEntity<List<Project>> getAllSimillarProjects(@PathVariable Long projectId){
        log.debug("REST to get no paged all others folders in some project with projectId: {}", projectId);
        List<Project> result = projectService.getAllSimillarProjects(projectId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("projects/progress/{projectId}")
    public ResponseEntity<Float> getProgressStatus(@PathVariable Long projectId){
        log.debug("REST to get project progress status by id: {}", projectId);
        Float result = projectService.getProgressStatus(projectId);
        return ResponseEntity.ok().body(result);
    }
}
