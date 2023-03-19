package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectTaskStatusTraking;
import com.mshz.microproject.repository.ProjectTaskStatusTrakingRepository;
import com.mshz.microproject.webflux.ProjectNotifService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTaskStatusTraking}.
 */
@Service
@Transactional
public class ProjectTaskStatusTrakingService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskStatusTrakingService.class);

    private final ProjectTaskStatusTrakingRepository projectTaskStatusTrakingRepository;

    private final ProjectNotifService notifService;

    public ProjectTaskStatusTrakingService(
        ProjectTaskStatusTrakingRepository projectTaskStatusTrakingRepository,
        ProjectNotifService notifService) {
        this.projectTaskStatusTrakingRepository = projectTaskStatusTrakingRepository;
        this.notifService = notifService;
    }

    /**
     * Save a projectTaskStatusTraking.
     *
     * @param projectTaskStatusTraking the entity to save.
     * @return the persisted entity.
     */
    public ProjectTaskStatusTraking save(ProjectTaskStatusTraking projectTaskStatusTraking) {
        log.debug("Request to save ProjectTaskStatusTraking : {}", projectTaskStatusTraking);
        boolean isNew = projectTaskStatusTraking != null && projectTaskStatusTraking.getId() == null;
        projectTaskStatusTraking = projectTaskStatusTrakingRepository.save(projectTaskStatusTraking);
        if(projectTaskStatusTraking != null && projectTaskStatusTraking.isEditable() == Boolean.TRUE)
            notifService.sendTaskLogEditNote(projectTaskStatusTraking, isNew);
        return projectTaskStatusTraking;
    }

    /**
     * Get all the projectTaskStatusTrakings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskStatusTraking> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTaskStatusTrakings");
        return projectTaskStatusTrakingRepository.findAll(pageable);
    }


    /**
     * Get one projectTaskStatusTraking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTaskStatusTraking> findOne(Long id) {
        log.debug("Request to get ProjectTaskStatusTraking : {}", id);
        return projectTaskStatusTrakingRepository.findById(id);
    }

    /**
     * Delete the projectTaskStatusTraking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTaskStatusTraking : {}", id);
        projectTaskStatusTrakingRepository.deleteById(id);
    }
}
