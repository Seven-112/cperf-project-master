package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.ProjectTaskItem;
import com.mshz.microproject.repository.ProjectTaskItemRepository;
import com.mshz.microproject.repository.ProjectTaskRepository;
import com.mshz.microproject.webflux.ProjectNotifService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTaskItem}.
 */
@Service
@Transactional
public class ProjectTaskItemService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskItemService.class);

    private final ProjectTaskItemRepository projectTaskItemRepository;
    private final ProjectNotifService projectNotifService;
    private final ProjectTaskRepository taskRepository;

    public ProjectTaskItemService(ProjectTaskItemRepository projectTaskItemRepository,
        ProjectNotifService projectNotifService,ProjectTaskRepository taskRepository) {
        this.projectTaskItemRepository = projectTaskItemRepository;
        this.projectNotifService = projectNotifService;
        this.taskRepository = taskRepository;
    }

    /**
     * Save a projectTaskItem.
     *
     * @param projectTaskItem the entity to save.
     * @return the persisted entity.
     */
    public ProjectTaskItem save(ProjectTaskItem projectTaskItem) {
        log.debug("Request to save ProjectTaskItem : {}", projectTaskItem);
        boolean isNew = false;
        if(projectTaskItem != null){
            if(projectTaskItem.isChecked() == null)
                projectTaskItem.setChecked(Boolean.FALSE);
            if(projectTaskItem.isRequired() == null)
                projectTaskItem.setRequired(Boolean.FALSE);
            if(projectTaskItem.getId() == null)
                isNew = true;
        }
        projectTaskItem = projectTaskItemRepository.save(projectTaskItem);
        projectNotifService.sendTaskItemCheckNotification(projectTaskItem, isNew);
        autoCheckTaskByItem(projectTaskItem);
        return projectTaskItem;
    }

    /**
     * Get all the projectTaskItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskItem> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTaskItems");
        return projectTaskItemRepository.findAll(pageable);
    }


    /**
     * Get one projectTaskItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTaskItem> findOne(Long id) {
        log.debug("Request to get ProjectTaskItem : {}", id);
        return projectTaskItemRepository.findById(id);
    }

    /**
     * Delete the projectTaskItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTaskItem : {}", id);
        ProjectTaskItem item = projectTaskItemRepository.findById(id).orElse(null);
        projectTaskItemRepository.deleteById(id);
        autoCheckTaskByItem(item);
    }

    private void autoCheckTaskByItem(ProjectTaskItem item){
        if(item != null && item.getTaskId() != null){
            try {
                ProjectTask task = taskRepository.getOne(item.getTaskId());
                if(task != null){
                    ProjectTaskItem requiredUnchecked = projectTaskItemRepository
                            .findFirstByTaskIdAndRequiredAndChecked(item.getTaskId(), Boolean.TRUE, Boolean.FALSE);
                    if(requiredUnchecked != null)
                        task.setChecked(Boolean.FALSE);
                    else
                        task.setChecked(Boolean.TRUE);
                    taskRepository.saveAndFlush(task);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
