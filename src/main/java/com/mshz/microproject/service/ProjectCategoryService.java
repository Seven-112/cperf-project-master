package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectCategory;
import com.mshz.microproject.repository.ProjectCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectCategory}.
 */
@Service
@Transactional
public class ProjectCategoryService {

    private final Logger log = LoggerFactory.getLogger(ProjectCategoryService.class);

    private final ProjectCategoryRepository projectCategoryRepository;

    public ProjectCategoryService(ProjectCategoryRepository projectCategoryRepository) {
        this.projectCategoryRepository = projectCategoryRepository;
    }

    /**
     * Save a projectCategory.
     *
     * @param projectCategory the entity to save.
     * @return the persisted entity.
     */
    public ProjectCategory save(ProjectCategory projectCategory) {
        log.debug("Request to save ProjectCategory : {}", projectCategory);
        return projectCategoryRepository.save(projectCategory);
    }

    /**
     * Get all the projectCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectCategories");
        return projectCategoryRepository.findAll(pageable);
    }


    /**
     * Get one projectCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectCategory> findOne(Long id) {
        log.debug("Request to get ProjectCategory : {}", id);
        return projectCategoryRepository.findById(id);
    }

    /**
     * Delete the projectCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectCategory : {}", id);
        projectCategoryRepository.deleteById(id);
    }
}
