package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectTaskUser;
import com.mshz.microproject.repository.ProjectTaskUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTaskUser}.
 */
@Service
@Transactional
public class ProjectTaskUserService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskUserService.class);

    private final ProjectTaskUserRepository projectTaskUserRepository;

    public ProjectTaskUserService(ProjectTaskUserRepository projectTaskUserRepository) {
        this.projectTaskUserRepository = projectTaskUserRepository;
    }

    /**
     * Save a projectTaskUser.
     *
     * @param projectTaskUser the entity to save.
     * @return the persisted entity.
     */
    public ProjectTaskUser save(ProjectTaskUser projectTaskUser) {
        log.debug("Request to save ProjectTaskUser : {}", projectTaskUser);
        return projectTaskUserRepository.save(projectTaskUser);
    }

    /**
     * Get all the projectTaskUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskUser> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTaskUsers");
        return projectTaskUserRepository.findAll(pageable);
    }


    /**
     * Get one projectTaskUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTaskUser> findOne(Long id) {
        log.debug("Request to get ProjectTaskUser : {}", id);
        return projectTaskUserRepository.findById(id);
    }

    /**
     * Delete the projectTaskUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectTaskUser : {}", id);
        projectTaskUserRepository.deleteById(id);
    }
}
