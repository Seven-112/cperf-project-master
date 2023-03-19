package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectTaskFile;
import com.mshz.microproject.repository.ProjectTaskFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTaskFile}.
 */
@Service
@Transactional
public class ProjectTaskFileService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskFileService.class);

    private final ProjectTaskFileRepository projectTaskFileRepository;

    public ProjectTaskFileService(ProjectTaskFileRepository projectTaskFileRepository) {
        this.projectTaskFileRepository = projectTaskFileRepository;
    }

    /**
     * Save a projectTaskFile.
     *
     * @param projectTaskFile the entity to save.
     * @return the persisted entity.
     */
    public ProjectTaskFile save(ProjectTaskFile projectTaskFile) {
        log.debug("Request to save ProjectTaskFile : {}", projectTaskFile);
        return projectTaskFileRepository.save(projectTaskFile);
    }

    /**
     * Get all the projectTaskFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskFile> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTaskFiles");
        return projectTaskFileRepository.findAll(pageable);
    }


    /**
     * Get one projectTaskFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTaskFile> findOne(Long id) {
        log.debug("Request to get ProjectTaskFile : {}", id);
        return projectTaskFileRepository.findById(id);
    }

    /**
     * Delete the projectTaskFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTaskFile : {}", id);
        projectTaskFileRepository.deleteById(id);
    }
}
