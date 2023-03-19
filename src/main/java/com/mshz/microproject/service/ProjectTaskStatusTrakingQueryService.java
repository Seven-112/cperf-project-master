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

import com.mshz.microproject.domain.ProjectTaskStatusTraking;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskStatusTrakingRepository;
import com.mshz.microproject.service.dto.ProjectTaskStatusTrakingCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskStatusTraking} entities in the database.
 * The main input is a {@link ProjectTaskStatusTrakingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskStatusTraking} or a {@link Page} of {@link ProjectTaskStatusTraking} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskStatusTrakingQueryService extends QueryService<ProjectTaskStatusTraking> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskStatusTrakingQueryService.class);

    private final ProjectTaskStatusTrakingRepository projectTaskStatusTrakingRepository;

    public ProjectTaskStatusTrakingQueryService(ProjectTaskStatusTrakingRepository projectTaskStatusTrakingRepository) {
        this.projectTaskStatusTrakingRepository = projectTaskStatusTrakingRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskStatusTraking} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskStatusTraking> findByCriteria(ProjectTaskStatusTrakingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskStatusTraking> specification = createSpecification(criteria);
        return projectTaskStatusTrakingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskStatusTraking} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskStatusTraking> findByCriteria(ProjectTaskStatusTrakingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskStatusTraking> specification = createSpecification(criteria);
        return projectTaskStatusTrakingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskStatusTrakingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskStatusTraking> specification = createSpecification(criteria);
        return projectTaskStatusTrakingRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskStatusTrakingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskStatusTraking> createSpecification(ProjectTaskStatusTrakingCriteria criteria) {
        Specification<ProjectTaskStatusTraking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskStatusTraking_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), ProjectTaskStatusTraking_.taskId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ProjectTaskStatusTraking_.status));
            }
            if (criteria.getTracingAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTracingAt(), ProjectTaskStatusTraking_.tracingAt));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), ProjectTaskStatusTraking_.userId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), ProjectTaskStatusTraking_.userName));
            }
            if (criteria.getUserEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserEmail(), ProjectTaskStatusTraking_.userEmail));
            }
            if (criteria.getEditable() != null) {
                specification = specification.and(buildSpecification(criteria.getEditable(), ProjectTaskStatusTraking_.editable));
            }
        }
        return specification;
    }
}
