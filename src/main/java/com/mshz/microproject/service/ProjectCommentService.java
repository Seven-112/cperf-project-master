package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectComment;
import com.mshz.microproject.repository.ProjectCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectComment}.
 */
@Service
@Transactional
public class ProjectCommentService {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentService.class);

    private final ProjectCommentRepository projectCommentRepository;

    public ProjectCommentService(ProjectCommentRepository projectCommentRepository) {
        this.projectCommentRepository = projectCommentRepository;
    }

    /**
     * Save a projectComment.
     *
     * @param projectComment the entity to save.
     * @return the persisted entity.
     */
    public ProjectComment save(ProjectComment projectComment) {
        log.debug("Request to save ProjectComment : {}", projectComment);
        return projectCommentRepository.save(projectComment);
    }

    /**
     * Get all the projectComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectComment> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectComments");
        return projectCommentRepository.findAll(pageable);
    }


    /**
     * Get one projectComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectComment> findOne(Long id) {
        log.debug("Request to get ProjectComment : {}", id);
        return projectCommentRepository.findById(id);
    }

    /**
     * Delete the projectComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectComment : {}", id);
        projectCommentRepository.deleteById(id);
    }
}
