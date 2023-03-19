package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectItemCheckJustificationFile;
import com.mshz.microproject.repository.ProjectItemCheckJustificationFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectItemCheckJustificationFile}.
 */
@Service
@Transactional
public class ProjectItemCheckJustificationFileService {

    private final Logger log = LoggerFactory.getLogger(ProjectItemCheckJustificationFileService.class);

    private final ProjectItemCheckJustificationFileRepository projectItemCheckJustificationFileRepository;

    public ProjectItemCheckJustificationFileService(ProjectItemCheckJustificationFileRepository projectItemCheckJustificationFileRepository) {
        this.projectItemCheckJustificationFileRepository = projectItemCheckJustificationFileRepository;
    }

    /**
     * Save a projectItemCheckJustificationFile.
     *
     * @param projectItemCheckJustificationFile the entity to save.
     * @return the persisted entity.
     */
    public ProjectItemCheckJustificationFile save(ProjectItemCheckJustificationFile projectItemCheckJustificationFile) {
        log.debug("Request to save ProjectItemCheckJustificationFile : {}", projectItemCheckJustificationFile);
        return projectItemCheckJustificationFileRepository.save(projectItemCheckJustificationFile);
    }

    /**
     * Get all the projectItemCheckJustificationFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectItemCheckJustificationFile> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectItemCheckJustificationFiles");
        return projectItemCheckJustificationFileRepository.findAll(pageable);
    }


    /**
     * Get one projectItemCheckJustificationFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectItemCheckJustificationFile> findOne(Long id) {
        log.debug("Request to get ProjectItemCheckJustificationFile : {}", id);
        return projectItemCheckJustificationFileRepository.findById(id);
    }

    /**
     * Delete the projectItemCheckJustificationFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectItemCheckJustificationFile : {}", id);
        projectItemCheckJustificationFileRepository.deleteById(id);
    }
}
