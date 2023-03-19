package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectEventTrigger;
import com.mshz.microproject.repository.ProjectEventTriggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectEventTrigger}.
 */
@Service
@Transactional
public class ProjectEventTriggerService {

    private final Logger log = LoggerFactory.getLogger(ProjectEventTriggerService.class);

    private final ProjectEventTriggerRepository projectEventTriggerRepository;

    public ProjectEventTriggerService(ProjectEventTriggerRepository projectEventTriggerRepository) {
        this.projectEventTriggerRepository = projectEventTriggerRepository;
    }

    /**
     * Save a projectEventTrigger.
     *
     * @param projectEventTrigger the entity to save.
     * @return the persisted entity.
     */
    public ProjectEventTrigger save(ProjectEventTrigger projectEventTrigger) {
        log.debug("Request to save ProjectEventTrigger : {}", projectEventTrigger);
        if(projectEventTrigger != null && projectEventTrigger.getId() == null){
            projectEventTrigger.setDisabled(Boolean.FALSE);
            projectEventTrigger.setCreatedAt((new Date()).toInstant());
        }
        return projectEventTriggerRepository.save(projectEventTrigger);
    }

    /**
     * Get all the projectEventTriggers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectEventTrigger> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectEventTriggers");
        return projectEventTriggerRepository.findAll(pageable);
    }


    /**
     * Get one projectEventTrigger by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectEventTrigger> findOne(Long id) {
        log.debug("Request to get ProjectEventTrigger : {}", id);
        return projectEventTriggerRepository.findById(id);
    }

    /**
     * Delete the projectEventTrigger by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectEventTrigger : {}", id);
        projectEventTriggerRepository.deleteById(id);
    }
   
    /*
    public void autoCreateSheduledInstances(){
        ZonedDateTime zdt = ZonedDateTime.now();
        List<ProjectEventTrigger> events = projectEventTriggerRepository
            .findBySheduledOnAndHourAndMinuteAndDisabledAndProcessIsNotNull(
                zdt.toLocalDate(),zdt.getHour(), zdt.getMinute(), Boolean.FALSE);
        log.debug("findedEvents {} now {} hour {} minute {} ", events, zdt.toLocalDate(), zdt.getHour(), zdt.getMinute());
        for (ProjectEventTrigger event : events) {
            String dossier = event.getFirstStartedAt() == null ? event.getName() : "";
           Process processInstance = processService.createInstance(dossier, event.getProcess().getId(), null, null);
           if(processInstance != null){
               if(event.getFirstStartedAt() == null)
                    event.setFirstStartedAt(zdt.toInstant());
                event = setNextShudeledOnAfter(event);
               eventTriggerRepository.save(event);
           }
        }
        
    }

    public EventTrigger setNextShudeledOnAfter(EventTrigger event){
        if(event != null){
            if(event.getSheduledOn() != null){
                ZonedDateTime zdt = event.getSheduledOn().atStartOfDay(ZoneId.systemDefault());
                if(event.getRecurrence() != null && event.getRecurrence() != ProcessEventRecurrence.ONCE){
                    if(event.getRecurrence() == ProcessEventRecurrence.EVERY_WEEK_ON_DAY){
                        event.setSheduledOn(zdt.plusDays(7).toLocalDate());
                    }else if(event.getRecurrence() == ProcessEventRecurrence.EVERY_YEAR_ON_DATE){
                        int month = zdt.getMonthValue();
                        int dayOfMonth = zdt.getDayOfMonth();
                        event.setSheduledOn(zdt.plusYears(1).withMonth(month)
                                    .withDayOfMonth(dayOfMonth).toLocalDate());
                    }else if(event.getRecurrence() == ProcessEventRecurrence.WEEK){
                        if(zdt.getDayOfWeek() == DayOfWeek.FRIDAY)
                            event.setSheduledOn(zdt.plusDays(3).toLocalDate());
                        else if(zdt.getDayOfWeek() == DayOfWeek.SATURDAY)
                            event.setSheduledOn(zdt.plusDays(2).toLocalDate());
                        else
                            event.setSheduledOn(zdt.plusDays(1).toLocalDate());
                    }else{
                        event.setSheduledOn(zdt.plusDays(1).toLocalDate());
                    }
                }else{
                    event.setSheduledOn(null);
                }
            }else{
                if(event.getFirstStartedAt() != null)
                    event.setSheduledOn(event.getFirstStartedAt().atZone(ZoneId.systemDefault()).toLocalDate());
            }
        }
        return event;
    } */
}
