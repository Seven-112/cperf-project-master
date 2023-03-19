package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectPublicHoliday;
import com.mshz.microproject.repository.ProjectPublicHolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectPublicHoliday}.
 */
@Service
@Transactional
public class ProjectPublicHolidayService {

    private final Logger log = LoggerFactory.getLogger(ProjectPublicHolidayService.class);

    private final ProjectPublicHolidayRepository projectPublicHolidayRepository;

    public ProjectPublicHolidayService(ProjectPublicHolidayRepository projectPublicHolidayRepository) {
        this.projectPublicHolidayRepository = projectPublicHolidayRepository;
    }

    /**
     * Save a projectPublicHoliday.
     *
     * @param projectPublicHoliday the entity to save.
     * @return the persisted entity.
     */
    public ProjectPublicHoliday save(ProjectPublicHoliday projectPublicHoliday) {
        log.debug("Request to save ProjectPublicHoliday : {}", projectPublicHoliday);
        return projectPublicHolidayRepository.save(projectPublicHoliday);
    }

    /**
     * Get all the projectPublicHolidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectPublicHoliday> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectPublicHolidays");
        return projectPublicHolidayRepository.findAll(pageable);
    }


    /**
     * Get one projectPublicHoliday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectPublicHoliday> findOne(Long id) {
        log.debug("Request to get ProjectPublicHoliday : {}", id);
        return projectPublicHolidayRepository.findById(id);
    }

    /**
     * Delete the projectPublicHoliday by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectPublicHoliday : {}", id);
        projectPublicHolidayRepository.deleteById(id);
    }

    public boolean isPublicHoliday(Date date) {
        if(date == null)
            date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        boolean isHoliday = projectPublicHolidayRepository.findByDate(localDate).orElse(null) != null;
        log.info("is holiday : {}", isHoliday);
        return isHoliday;
    }

    public List<ProjectPublicHoliday> findByOfDateBetween(LocalDate dateMin, LocalDate dateMax) {
        return projectPublicHolidayRepository.findByDateBetween(dateMin, dateMax);
    }
}
