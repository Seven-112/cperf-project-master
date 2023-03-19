package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectCalendar;
import com.mshz.microproject.repository.ProjectCalendarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectCalendar}.
 */
@Service
@Transactional
public class ProjectCalendarService {

    private final Logger log = LoggerFactory.getLogger(ProjectCalendarService.class);

    private final ProjectCalendarRepository projectCalendarRepository;

    public ProjectCalendarService(ProjectCalendarRepository projectCalendarRepository) {
        this.projectCalendarRepository = projectCalendarRepository;
    }

    /**
     * Save a projectCalendar.
     *
     * @param projectCalendar the entity to save.
     * @return the persisted entity.
     */
    public ProjectCalendar save(ProjectCalendar projectCalendar) {
        log.debug("Request to save ProjectCalendar : {}", projectCalendar);
        if(projectCalendar != null){
            projectCalendar.setDayNumber(formDayOfWeekToIsoNorm(projectCalendar.getDayNumber()));
        }
        return projectCalendarRepository.save(projectCalendar);
    }

    /**
     * Get all the projectCalendars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCalendar> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectCalendars");
        return projectCalendarRepository.findAll(pageable);
    }


    /**
     * Get one projectCalendar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectCalendar> findOne(Long id) {
        log.debug("Request to get ProjectCalendar : {}", id);
        return projectCalendarRepository.findById(id);
    }

    /**
     * Delete the projectCalendar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectCalendar : {}", id);
        projectCalendarRepository.deleteById(id);
    }

    public boolean isWorkingTime(Date date){
        try {
            if(date == null)
                date = new Date();

            ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            List<ProjectCalendar> calendars = projectCalendarRepository
                            .findByDayNumber(Integer.valueOf(zdt.getDayOfWeek().getValue()));
            for (ProjectCalendar workCalender : calendars) {
                if(zonedDateTimeInWorkingInstants(zdt, workCalender.getStartTime(), workCalender.getEndTime()))
                    return true;
            }
            
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
        }
        return false;
    }

    private boolean zonedDateTimeInWorkingInstants(ZonedDateTime zdt, Instant startInstant, Instant endInstant){
        try {
            if(zdt != null && startInstant != null && endInstant != null){

                Instant start = startInstant.isAfter(endInstant) ? endInstant : startInstant;

                Instant end = endInstant.isBefore(startInstant) ? startInstant : endInstant;

                ZonedDateTime startZdt = ZonedDateTime.ofInstant(start,  ZoneId.systemDefault())
                                                        .withYear(zdt.getYear())
                                                        .withMonth(zdt.getMonthValue())
                                                        .withDayOfMonth(zdt.getDayOfMonth());
                                                        
                ZonedDateTime endZdt = ZonedDateTime.ofInstant(end,  ZoneId.systemDefault())
                                                        .withYear(zdt.getYear())
                                                        .withMonth(zdt.getMonthValue())
                                                        .withDayOfMonth(zdt.getDayOfMonth()); 
                
                return ((zdt.isAfter(startZdt) || zdt.isEqual(startZdt)) && (zdt.isBefore(endZdt) || zdt.isEqual(endZdt)));   
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error {}", e.getMessage());
        }
        return false;
    }

    public Integer formDayOfWeekToIsoNorm(Integer day){
        if(day != null && day.intValue() == 0)
            return Integer.valueOf(7);
        return day;
    }
}
