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

import com.mshz.microproject.domain.ProjectTaskFile;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskFileRepository;
import com.mshz.microproject.service.dto.ProjectTaskFileCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskFile} entities in the database.
 * The main input is a {@link ProjectTaskFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskFile} or a {@link Page} of {@link ProjectTaskFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskFileQueryService extends QueryService<ProjectTaskFile> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskFileQueryService.class);

    private final ProjectTaskFileRepository projectTaskFileRepository;

    public ProjectTaskFileQueryService(ProjectTaskFileRepository projectTaskFileRepository) {
        this.projectTaskFileRepository = projectTaskFileRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskFile> findByCriteria(ProjectTaskFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskFile> specification = createSpecification(criteria);
        return projectTaskFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskFile> findByCriteria(ProjectTaskFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskFile> specification = createSpecification(criteria);
        return projectTaskFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskFile> specification = createSpecification(criteria);
        return projectTaskFileRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskFile> createSpecification(ProjectTaskFileCriteria criteria) {
        Specification<ProjectTaskFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), ProjectTaskFile_.fileId));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), ProjectTaskFile_.fileName));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), ProjectTaskFile_.type));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), ProjectTaskFile_.taskId));
            }
        }
        return specification;
    }
}
