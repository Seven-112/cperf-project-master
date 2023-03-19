package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectTaskValidationControl;
import com.mshz.microproject.repository.ProjectTaskValidationControlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTaskValidationControl}.
 */
@Service
@Transactional
public class ProjectTaskValidationControlService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskValidationControlService.class);

    private final ProjectTaskValidationControlRepository projectTaskValidationControlRepository;

    public ProjectTaskValidationControlService(ProjectTaskValidationControlRepository projectTaskValidationControlRepository) {
        this.projectTaskValidationControlRepository = projectTaskValidationControlRepository;
    }

    /**
     * Save a projectTaskValidationControl.
     *
     * @param projectTaskValidationControl the entity to save.
     * @return the persisted entity.
     */
    public ProjectTaskValidationControl save(ProjectTaskValidationControl projectTaskValidationControl) {
        log.debug("Request to save ProjectTaskValidationControl : {}", projectTaskValidationControl);
        return projectTaskValidationControlRepository.save(projectTaskValidationControl);
    }

    /**
     * Get all the projectTaskValidationControls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskValidationControl> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTaskValidationControls");
        return projectTaskValidationControlRepository.findAll(pageable);
    }


    /**
     * Get one projectTaskValidationControl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTaskValidationControl> findOne(Long id) {
        log.debug("Request to get ProjectTaskValidationControl : {}", id);
        return projectTaskValidationControlRepository.findById(id);
    }

    /**
     * Delete the projectTaskValidationControl by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTaskValidationControl : {}", id);
        projectTaskValidationControlRepository.deleteById(id);
    }
}
