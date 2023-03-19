package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectTaskSubmission;
import com.mshz.microproject.repository.ProjectTaskSubmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTaskSubmission}.
 */
@Service
@Transactional
public class ProjectTaskSubmissionService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskSubmissionService.class);

    private final ProjectTaskSubmissionRepository projectTaskSubmissionRepository;

    public ProjectTaskSubmissionService(ProjectTaskSubmissionRepository projectTaskSubmissionRepository) {
        this.projectTaskSubmissionRepository = projectTaskSubmissionRepository;
    }

    /**
     * Save a projectTaskSubmission.
     *
     * @param projectTaskSubmission the entity to save.
     * @return the persisted entity.
     */
    public ProjectTaskSubmission save(ProjectTaskSubmission projectTaskSubmission) {
        log.debug("Request to save ProjectTaskSubmission : {}", projectTaskSubmission);
        return projectTaskSubmissionRepository.save(projectTaskSubmission);
    }

    /**
     * Get all the projectTaskSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskSubmission> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTaskSubmissions");
        return projectTaskSubmissionRepository.findAll(pageable);
    }


    /**
     * Get one projectTaskSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTaskSubmission> findOne(Long id) {
        log.debug("Request to get ProjectTaskSubmission : {}", id);
        return projectTaskSubmissionRepository.findById(id);
    }

    /**
     * Delete the projectTaskSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTaskSubmission : {}", id);
        projectTaskSubmissionRepository.deleteById(id);
    }
}
