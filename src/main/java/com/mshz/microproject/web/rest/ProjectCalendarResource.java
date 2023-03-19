package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectCalendar;
import com.mshz.microproject.service.ProjectCalendarService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectCalendarCriteria;
import com.mshz.microproject.service.ProjectCalendarQueryService;

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
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectCalendar}.
 */
@RestController
@RequestMapping("/api")
public class ProjectCalendarResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCalendarResource.class);

    private static final String ENTITY_NAME = "microprojectProjectCalendar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectCalendarService projectCalendarService;

    private final ProjectCalendarQueryService projectCalendarQueryService;

    public ProjectCalendarResource(ProjectCalendarService projectCalendarService, ProjectCalendarQueryService projectCalendarQueryService) {
        this.projectCalendarService = projectCalendarService;
        this.projectCalendarQueryService = projectCalendarQueryService;
    }

    /**
     * {@code POST  /project-calendars} : Create a new projectCalendar.
     *
     * @param projectCalendar the projectCalendar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCalendar, or with status {@code 400 (Bad Request)} if the projectCalendar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-calendars")
    public ResponseEntity<ProjectCalendar> createProjectCalendar(@Valid @RequestBody ProjectCalendar projectCalendar) throws URISyntaxException {
        log.debug("REST request to save ProjectCalendar : {}", projectCalendar);
        if (projectCalendar.getId() != null) {
            throw new BadRequestAlertException("A new projectCalendar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectCalendar result = projectCalendarService.save(projectCalendar);
        return ResponseEntity.created(new URI("/api/project-calendars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-calendars} : Updates an existing projectCalendar.
     *
     * @param projectCalendar the projectCalendar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCalendar,
     * or with status {@code 400 (Bad Request)} if the projectCalendar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCalendar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-calendars")
    public ResponseEntity<ProjectCalendar> updateProjectCalendar(@Valid @RequestBody ProjectCalendar projectCalendar) throws URISyntaxException {
        log.debug("REST request to update ProjectCalendar : {}", projectCalendar);
        if (projectCalendar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectCalendar result = projectCalendarService.save(projectCalendar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectCalendar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-calendars} : get all the projectCalendars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectCalendars in body.
     */
    @GetMapping("/project-calendars")
    public ResponseEntity<List<ProjectCalendar>> getAllProjectCalendars(ProjectCalendarCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectCalendars by criteria: {}", criteria);
        Page<ProjectCalendar> page = projectCalendarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-calendars/count} : count all the projectCalendars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-calendars/count")
    public ResponseEntity<Long> countProjectCalendars(ProjectCalendarCriteria criteria) {
        log.debug("REST request to count ProjectCalendars by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectCalendarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-calendars/:id} : get the "id" projectCalendar.
     *
     * @param id the id of the projectCalendar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCalendar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-calendars/{id}")
    public ResponseEntity<ProjectCalendar> getProjectCalendar(@PathVariable Long id) {
        log.debug("REST request to get ProjectCalendar : {}", id);
        Optional<ProjectCalendar> projectCalendar = projectCalendarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCalendar);
    }

    /**
     * {@code DELETE  /project-calendars/:id} : delete the "id" projectCalendar.
     *
     * @param id the id of the projectCalendar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-calendars/{id}")
    public ResponseEntity<Void> deleteProjectCalendar(@PathVariable Long id) {
        log.debug("REST request to delete ProjectCalendar : {}", id);
        projectCalendarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
