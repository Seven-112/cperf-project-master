package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectPublicHoliday;
import com.mshz.microproject.service.ProjectPublicHolidayService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectPublicHolidayCriteria;
import com.mshz.microproject.service.ProjectPublicHolidayQueryService;

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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectPublicHoliday}.
 */
@RestController
@RequestMapping("/api")
public class ProjectPublicHolidayResource {

    private final Logger log = LoggerFactory.getLogger(ProjectPublicHolidayResource.class);

    private static final String ENTITY_NAME = "microprojectProjectPublicHoliday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectPublicHolidayService projectPublicHolidayService;

    private final ProjectPublicHolidayQueryService projectPublicHolidayQueryService;

    public ProjectPublicHolidayResource(ProjectPublicHolidayService projectPublicHolidayService, ProjectPublicHolidayQueryService projectPublicHolidayQueryService) {
        this.projectPublicHolidayService = projectPublicHolidayService;
        this.projectPublicHolidayQueryService = projectPublicHolidayQueryService;
    }

    /**
     * {@code POST  /project-public-holidays} : Create a new projectPublicHoliday.
     *
     * @param projectPublicHoliday the projectPublicHoliday to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectPublicHoliday, or with status {@code 400 (Bad Request)} if the projectPublicHoliday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-public-holidays")
    public ResponseEntity<ProjectPublicHoliday> createProjectPublicHoliday(@Valid @RequestBody ProjectPublicHoliday projectPublicHoliday) throws URISyntaxException {
        log.debug("REST request to save ProjectPublicHoliday : {}", projectPublicHoliday);
        if (projectPublicHoliday.getId() != null) {
            throw new BadRequestAlertException("A new projectPublicHoliday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectPublicHoliday result = projectPublicHolidayService.save(projectPublicHoliday);
        return ResponseEntity.created(new URI("/api/project-public-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-public-holidays} : Updates an existing projectPublicHoliday.
     *
     * @param projectPublicHoliday the projectPublicHoliday to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectPublicHoliday,
     * or with status {@code 400 (Bad Request)} if the projectPublicHoliday is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectPublicHoliday couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-public-holidays")
    public ResponseEntity<ProjectPublicHoliday> updateProjectPublicHoliday(@Valid @RequestBody ProjectPublicHoliday projectPublicHoliday) throws URISyntaxException {
        log.debug("REST request to update ProjectPublicHoliday : {}", projectPublicHoliday);
        if (projectPublicHoliday.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectPublicHoliday result = projectPublicHolidayService.save(projectPublicHoliday);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectPublicHoliday.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-public-holidays} : get all the projectPublicHolidays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectPublicHolidays in body.
     */
    @GetMapping("/project-public-holidays")
    public ResponseEntity<List<ProjectPublicHoliday>> getAllProjectPublicHolidays(ProjectPublicHolidayCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectPublicHolidays by criteria: {}", criteria);
        Page<ProjectPublicHoliday> page = projectPublicHolidayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-public-holidays/count} : count all the projectPublicHolidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-public-holidays/count")
    public ResponseEntity<Long> countProjectPublicHolidays(ProjectPublicHolidayCriteria criteria) {
        log.debug("REST request to count ProjectPublicHolidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectPublicHolidayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-public-holidays/:id} : get the "id" projectPublicHoliday.
     *
     * @param id the id of the projectPublicHoliday to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectPublicHoliday, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-public-holidays/{id}")
    public ResponseEntity<ProjectPublicHoliday> getProjectPublicHoliday(@PathVariable Long id) {
        log.debug("REST request to get ProjectPublicHoliday : {}", id);
        Optional<ProjectPublicHoliday> projectPublicHoliday = projectPublicHolidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectPublicHoliday);
    }

    /**
     * {@code DELETE  /project-public-holidays/:id} : delete the "id" projectPublicHoliday.
     *
     * @param id the id of the projectPublicHoliday to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-public-holidays/{id}")
    public ResponseEntity<Void> deleteProjectPublicHoliday(@PathVariable Long id) {
        log.debug("REST request to delete ProjectPublicHoliday : {}", id);
        projectPublicHolidayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/project-public-holidays/between")
    public ResponseEntity<List<ProjectPublicHoliday>> getPublicHolidaysBeteewn(LocalDate dateMin, LocalDate dateMax) {
        log.debug("REST request to get PublicHolidays between data: {} {}", dateMin, dateMax);
        List<ProjectPublicHoliday> entityList = projectPublicHolidayService.findByOfDateBetween(dateMin, dateMax);
        return ResponseEntity.ok().body(entityList);
    }
}
