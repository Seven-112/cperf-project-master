package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectCommentFile;
import com.mshz.microproject.repository.ProjectCommentFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectCommentFile}.
 */
@Service
@Transactional
public class ProjectCommentFileService {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentFileService.class);

    private final ProjectCommentFileRepository projectCommentFileRepository;

    public ProjectCommentFileService(ProjectCommentFileRepository projectCommentFileRepository) {
        this.projectCommentFileRepository = projectCommentFileRepository;
    }

    /**
     * Save a projectCommentFile.
     *
     * @param projectCommentFile the entity to save.
     * @return the persisted entity.
     */
    public ProjectCommentFile save(ProjectCommentFile projectCommentFile) {
        log.debug("Request to save ProjectCommentFile : {}", projectCommentFile);
        return projectCommentFileRepository.save(projectCommentFile);
    }

    /**
     * Get all the projectCommentFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCommentFile> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectCommentFiles");
        return projectCommentFileRepository.findAll(pageable);
    }


    /**
     * Get one projectCommentFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectCommentFile> findOne(Long id) {
        log.debug("Request to get ProjectCommentFile : {}", id);
        return projectCommentFileRepository.findById(id);
    }

    /**
     * Delete the projectCommentFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectCommentFile : {}", id);
        projectCommentFileRepository.deleteById(id);
    }
}
