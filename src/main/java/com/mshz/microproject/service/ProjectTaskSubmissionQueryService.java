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

import com.mshz.microproject.domain.ProjectTaskSubmission;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskSubmissionRepository;
import com.mshz.microproject.service.dto.ProjectTaskSubmissionCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskSubmission} entities in the database.
 * The main input is a {@link ProjectTaskSubmissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskSubmission} or a {@link Page} of {@link ProjectTaskSubmission} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskSubmissionQueryService extends QueryService<ProjectTaskSubmission> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskSubmissionQueryService.class);

    private final ProjectTaskSubmissionRepository projectTaskSubmissionRepository;

    public ProjectTaskSubmissionQueryService(ProjectTaskSubmissionRepository projectTaskSubmissionRepository) {
        this.projectTaskSubmissionRepository = projectTaskSubmissionRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskSubmission} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskSubmission> findByCriteria(ProjectTaskSubmissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskSubmission> specification = createSpecification(criteria);
        return projectTaskSubmissionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskSubmission} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskSubmission> findByCriteria(ProjectTaskSubmissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskSubmission> specification = createSpecification(criteria);
        return projectTaskSubmissionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskSubmissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskSubmission> specification = createSpecification(criteria);
        return projectTaskSubmissionRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskSubmissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskSubmission> createSpecification(ProjectTaskSubmissionCriteria criteria) {
        Specification<ProjectTaskSubmission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskSubmission_.id));
            }
            if (criteria.getSubmitorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmitorId(), ProjectTaskSubmission_.submitorId));
            }
            if (criteria.getSubmitorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubmitorName(), ProjectTaskSubmission_.submitorName));
            }
            if (criteria.getSubmitorEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubmitorEmail(), ProjectTaskSubmission_.submitorEmail));
            }
            if (criteria.getStoreUp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStoreUp(), ProjectTaskSubmission_.storeUp));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), ProjectTaskSubmission_.taskId));
            }
        }
        return specification;
    }
}
