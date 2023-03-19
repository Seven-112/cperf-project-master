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

import com.mshz.microproject.domain.ProjectStartableTask;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectStartableTaskRepository;
import com.mshz.microproject.service.dto.ProjectStartableTaskCriteria;

/**
 * Service for executing complex queries for {@link ProjectStartableTask} entities in the database.
 * The main input is a {@link ProjectStartableTaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectStartableTask} or a {@link Page} of {@link ProjectStartableTask} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectStartableTaskQueryService extends QueryService<ProjectStartableTask> {

    private final Logger log = LoggerFactory.getLogger(ProjectStartableTaskQueryService.class);

    private final ProjectStartableTaskRepository projectStartableTaskRepository;

    public ProjectStartableTaskQueryService(ProjectStartableTaskRepository projectStartableTaskRepository) {
        this.projectStartableTaskRepository = projectStartableTaskRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectStartableTask} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectStartableTask> findByCriteria(ProjectStartableTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectStartableTask> specification = createSpecification(criteria);
        return projectStartableTaskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectStartableTask} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStartableTask> findByCriteria(ProjectStartableTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectStartableTask> specification = createSpecification(criteria);
        return projectStartableTaskRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectStartableTaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectStartableTask> specification = createSpecification(criteria);
        return projectStartableTaskRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectStartableTaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectStartableTask> createSpecification(ProjectStartableTaskCriteria criteria) {
        Specification<ProjectStartableTask> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectStartableTask_.id));
            }
            if (criteria.getTriggerTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTriggerTaskId(), ProjectStartableTask_.triggerTaskId));
            }
            if (criteria.getStartableTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartableTaskId(), ProjectStartableTask_.startableTaskId));
            }
            if (criteria.getTriggerTaskName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTriggerTaskName(), ProjectStartableTask_.triggerTaskName));
            }
            if (criteria.getStartableTaskName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartableTaskName(), ProjectStartableTask_.startableTaskName));
            }
            if (criteria.getTriggerProjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTriggerProjectName(), ProjectStartableTask_.triggerProjectName));
            }
            if (criteria.getStartableProjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartableProjectName(), ProjectStartableTask_.startableProjectName));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), ProjectStartableTask_.userId));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), ProjectStartableTask_.createdAt));
            }
            if (criteria.getStartCond() != null) {
                specification = specification.and(buildSpecification(criteria.getStartCond(), ProjectStartableTask_.startCond));
            }
        }
        return specification;
    }
}
