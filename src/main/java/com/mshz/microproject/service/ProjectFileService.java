package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectFile;
import com.mshz.microproject.repository.ProjectFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectFile}.
 */
@Service
@Transactional
public class ProjectFileService {

    private final Logger log = LoggerFactory.getLogger(ProjectFileService.class);

    private final ProjectFileRepository projectFileRepository;

    public ProjectFileService(ProjectFileRepository projectFileRepository) {
        this.projectFileRepository = projectFileRepository;
    }

    /**
     * Save a projectFile.
     *
     * @param projectFile the entity to save.
     * @return the persisted entity.
     */
    public ProjectFile save(ProjectFile projectFile) {
        log.debug("Request to save ProjectFile : {}", projectFile);
        return projectFileRepository.save(projectFile);
    }

    /**
     * Get all the projectFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectFile> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectFiles");
        return projectFileRepository.findAll(pageable);
    }


    /**
     * Get one projectFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectFile> findOne(Long id) {
        log.debug("Request to get ProjectFile : {}", id);
        return projectFileRepository.findById(id);
    }

    /**
     * Delete the projectFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectFile : {}", id);
        projectFileRepository.deleteById(id);
    }
}
