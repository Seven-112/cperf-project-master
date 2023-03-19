package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectTaskStatusTrakingFile;
import com.mshz.microproject.repository.ProjectTaskStatusTrakingFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTaskStatusTrakingFile}.
 */
@Service
@Transactional
public class ProjectTaskStatusTrakingFileService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskStatusTrakingFileService.class);

    private final ProjectTaskStatusTrakingFileRepository projectTaskStatusTrakingFileRepository;

    public ProjectTaskStatusTrakingFileService(ProjectTaskStatusTrakingFileRepository projectTaskStatusTrakingFileRepository) {
        this.projectTaskStatusTrakingFileRepository = projectTaskStatusTrakingFileRepository;
    }

    /**
     * Save a projectTaskStatusTrakingFile.
     *
     * @param projectTaskStatusTrakingFile the entity to save.
     * @return the persisted entity.
     */
    public ProjectTaskStatusTrakingFile save(ProjectTaskStatusTrakingFile projectTaskStatusTrakingFile) {
        log.debug("Request to save ProjectTaskStatusTrakingFile : {}", projectTaskStatusTrakingFile);
        return projectTaskStatusTrakingFileRepository.save(projectTaskStatusTrakingFile);
    }

    /**
     * Get all the projectTaskStatusTrakingFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskStatusTrakingFile> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTaskStatusTrakingFiles");
        return projectTaskStatusTrakingFileRepository.findAll(pageable);
    }


    /**
     * Get one projectTaskStatusTrakingFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTaskStatusTrakingFile> findOne(Long id) {
        log.debug("Request to get ProjectTaskStatusTrakingFile : {}", id);
        return projectTaskStatusTrakingFileRepository.findById(id);
    }

    /**
     * Delete the projectTaskStatusTrakingFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTaskStatusTrakingFile : {}", id);
        projectTaskStatusTrakingFileRepository.deleteById(id);
    }
}
