package com.mshz.microproject.service;

import com.mshz.microproject.domain.ProjectEdgeInfo;
import com.mshz.microproject.repository.ProjectEdgeInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectEdgeInfo}.
 */
@Service
@Transactional
public class ProjectEdgeInfoService {

    private final Logger log = LoggerFactory.getLogger(ProjectEdgeInfoService.class);

    private final ProjectEdgeInfoRepository projectEdgeInfoRepository;

    public ProjectEdgeInfoService(ProjectEdgeInfoRepository projectEdgeInfoRepository) {
        this.projectEdgeInfoRepository = projectEdgeInfoRepository;
    }

    /**
     * Save a projectEdgeInfo.
     *
     * @param projectEdgeInfo the entity to save.
     * @return the persisted entity.
     */
    public ProjectEdgeInfo save(ProjectEdgeInfo projectEdgeInfo) {
        log.debug("Request to save ProjectEdgeInfo : {}", projectEdgeInfo);
        return projectEdgeInfoRepository.save(projectEdgeInfo);
    }

    /**
     * Get all the projectEdgeInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectEdgeInfo> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectEdgeInfos");
        return projectEdgeInfoRepository.findAll(pageable);
    }


    /**
     * Get one projectEdgeInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectEdgeInfo> findOne(Long id) {
        log.debug("Request to get ProjectEdgeInfo : {}", id);
        return projectEdgeInfoRepository.findById(id);
    }

    /**
     * Delete the projectEdgeInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectEdgeInfo : {}", id);
        projectEdgeInfoRepository.deleteById(id);
    }
}
