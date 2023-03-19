package com.mshz.microproject.service;

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.ProjectStartableTask;
import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;
import com.mshz.microproject.repository.ProjectRepository;
import com.mshz.microproject.repository.ProjectStartableTaskRepository;
import com.mshz.microproject.repository.ProjectTaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectStartableTask}.
 */
@Service
@Transactional
public class ProjectStartableTaskService {

    private final Logger log = LoggerFactory.getLogger(ProjectStartableTaskService.class);

    private final ProjectStartableTaskRepository projectStartableTaskRepository;

    private final ProjectTaskRepository projectTaskRepository;

    private final ProjectRepository projectRepository;

    private final ProjectTaskService projectTaskService;

    public ProjectStartableTaskService(ProjectStartableTaskRepository projectStartableTaskRepository,
    ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository,
    ProjectTaskService projectTaskService) {
        this.projectStartableTaskRepository = projectStartableTaskRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
        this.projectTaskService = projectTaskService;
    }

    /**
     * Save a projectStartableTask.
     *
     * @param projectStartableTask the entity to save.
     * @return the persisted entity.
     */
    public ProjectStartableTask save(ProjectStartableTask projectStartableTask) {
        log.debug("Request to save ProjectStartableTask : {}", projectStartableTask);
        projectStartableTask = normalize(projectStartableTask);
        return projectStartableTaskRepository.save(projectStartableTask);
    }

    /**
     * Get all the projectStartableTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStartableTask> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectStartableTasks");
        return projectStartableTaskRepository.findAll(pageable);
    }


    /**
     * Get one projectStartableTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectStartableTask> findOne(Long id) {
        log.debug("Request to get ProjectStartableTask : {}", id);
        return projectStartableTaskRepository.findById(id);
    }

    /**
     * Delete the projectStartableTask by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectStartableTask : {}", id);
        projectStartableTaskRepository.deleteById(id);
    }

    public ProjectStartableTask normalize(ProjectStartableTask entity){
        if(entity != null){
            if(entity.getCreatedAt() == null){
                entity.setCreatedAt(Instant.now());
            }
            // normalize trigger field
            if(entity.getTriggerProjectName() == null && entity.getTriggerTaskId() != null){
                ProjectTask triggerTask = projectTaskRepository.findById(entity.getTriggerTaskId()).orElse(null);
                if(triggerTask != null){
                    entity.setTriggerTaskName(triggerTask.getName());
                    Project triggerProject = projectRepository.findById(triggerTask.getProcessId()).orElse(null);
                    if(triggerProject != null)
                        entity.setTriggerProjectName(triggerProject.getLabel());
                }
            }

            // normalize startable field
            if(entity.getStartableProjectName() == null && entity.getStartableTaskId() != null){
                ProjectTask startable = projectTaskRepository.findById(entity.getStartableTaskId()).orElse(null);
                if(startable != null){
                    entity.setStartableTaskName(startable.getName());
                    Project startableProject = projectRepository.findById(startable.getProcessId()).orElse(null);
                    if(startableProject != null)
                        entity.setStartableProjectName(startableProject.getLabel());
                }
            }
        }
        return entity;
    }
}
