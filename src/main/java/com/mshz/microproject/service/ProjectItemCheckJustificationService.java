package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectItemCheckJustification;
import com.mshz.microproject.repository.ProjectItemCheckJustificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectItemCheckJustification}.
 */
@Service
@Transactional
public class ProjectItemCheckJustificationService {

    private final Logger log = LoggerFactory.getLogger(ProjectItemCheckJustificationService.class);

    private final ProjectItemCheckJustificationRepository projectItemCheckJustificationRepository;

    public ProjectItemCheckJustificationService(ProjectItemCheckJustificationRepository projectItemCheckJustificationRepository) {
        this.projectItemCheckJustificationRepository = projectItemCheckJustificationRepository;
    }

    /**
     * Save a projectItemCheckJustification.
     *
     * @param projectItemCheckJustification the entity to save.
     * @return the persisted entity.
     */
    public ProjectItemCheckJustification save(ProjectItemCheckJustification projectItemCheckJustification) {
        log.debug("Request to save ProjectItemCheckJustification : {}", projectItemCheckJustification);
        if(projectItemCheckJustification != null){
            if(projectItemCheckJustification.isChecked() == null)
                projectItemCheckJustification.setChecked(Boolean.FALSE);
            if(projectItemCheckJustification.getDateAndTime() == null)
                projectItemCheckJustification.setDateAndTime(Instant.now());
            
        }
        return projectItemCheckJustificationRepository.save(projectItemCheckJustification);
    }

    /**
     * Get all the projectItemCheckJustifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectItemCheckJustification> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectItemCheckJustifications");
        return projectItemCheckJustificationRepository.findAll(pageable);
    }


    /**
     * Get one projectItemCheckJustification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectItemCheckJustification> findOne(Long id) {
        log.debug("Request to get ProjectItemCheckJustification : {}", id);
        return projectItemCheckJustificationRepository.findById(id);
    }

    /**
     * Delete the projectItemCheckJustification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectItemCheckJustification : {}", id);
        projectItemCheckJustificationRepository.deleteById(id);
    }
}
