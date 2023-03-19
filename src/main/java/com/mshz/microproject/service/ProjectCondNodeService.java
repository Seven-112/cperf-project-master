package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectCondNode;
import com.mshz.microproject.repository.ProjectCondNodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectCondNode}.
 */
@Service
@Transactional
public class ProjectCondNodeService {

    private final Logger log = LoggerFactory.getLogger(ProjectCondNodeService.class);

    private final ProjectCondNodeRepository projectCondNodeRepository;

    public ProjectCondNodeService(ProjectCondNodeRepository projectCondNodeRepository) {
        this.projectCondNodeRepository = projectCondNodeRepository;
    }

    /**
     * Save a projectCondNode.
     *
     * @param projectCondNode the entity to save.
     * @return the persisted entity.
     */
    public ProjectCondNode save(ProjectCondNode projectCondNode) {
        log.debug("Request to save ProjectCondNode : {}", projectCondNode);
        return projectCondNodeRepository.save(projectCondNode);
    }

    /**
     * Get all the projectCondNodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCondNode> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectCondNodes");
        return projectCondNodeRepository.findAll(pageable);
    }


    /**
     * Get one projectCondNode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectCondNode> findOne(Long id) {
        log.debug("Request to get ProjectCondNode : {}", id);
        return projectCondNodeRepository.findById(id);
    }

    /**
     * Delete the projectCondNode by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectCondNode : {}", id);
        projectCondNodeRepository.deleteById(id);
    }
}
