package com.mshz.microproject.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mshz.microproject.domain.ProjectTaskStatusTrakingFile;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskStatusTrakingFileRepository;
import com.mshz.microproject.service.dto.ProjectTaskStatusTrakingFileCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskStatusTrakingFile} entities in the database.
 * The main input is a {@link ProjectTaskStatusTrakingFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskStatusTrakingFile} or a {@link Page} of {@link ProjectTaskStatusTrakingFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskStatusTrakingFileQueryService extends QueryService<ProjectTaskStatusTrakingFile> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskStatusTrakingFileQueryService.class);

    private final ProjectTaskStatusTrakingFileRepository projectTaskStatusTrakingFileRepository;

    public ProjectTaskStatusTrakingFileQueryService(ProjectTaskStatusTrakingFileRepository projectTaskStatusTrakingFileRepository) {
        this.projectTaskStatusTrakingFileRepository = projectTaskStatusTrakingFileRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskStatusTrakingFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskStatusTrakingFile> findByCriteria(ProjectTaskStatusTrakingFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskStatusTrakingFile> specification = createSpecification(criteria);
        return projectTaskStatusTrakingFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskStatusTrakingFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskStatusTrakingFile> findByCriteria(ProjectTaskStatusTrakingFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskStatusTrakingFile> specification = createSpecification(criteria);
        return projectTaskStatusTrakingFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskStatusTrakingFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskStatusTrakingFile> specification = createSpecification(criteria);
        return projectTaskStatusTrakingFileRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskStatusTrakingFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskStatusTrakingFile> createSpecification(ProjectTaskStatusTrakingFileCriteria criteria) {
        Specification<ProjectTaskStatusTrakingFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskStatusTrakingFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), ProjectTaskStatusTrakingFile_.fileId));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), ProjectTaskStatusTrakingFile_.fileName));
            }
            if (criteria.getTrackId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrackId(), ProjectTaskStatusTrakingFile_.trackId));
            }
        }
        return specification;
    }
}
