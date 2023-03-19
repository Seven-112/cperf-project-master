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

import com.mshz.microproject.domain.ProjectEventTrigger;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectEventTriggerRepository;
import com.mshz.microproject.service.dto.ProjectEventTriggerCriteria;

/**
 * Service for executing complex queries for {@link ProjectEventTrigger} entities in the database.
 * The main input is a {@link ProjectEventTriggerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectEventTrigger} or a {@link Page} of {@link ProjectEventTrigger} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectEventTriggerQueryService extends QueryService<ProjectEventTrigger> {

    private final Logger log = LoggerFactory.getLogger(ProjectEventTriggerQueryService.class);

    private final ProjectEventTriggerRepository projectEventTriggerRepository;

    public ProjectEventTriggerQueryService(ProjectEventTriggerRepository projectEventTriggerRepository) {
        this.projectEventTriggerRepository = projectEventTriggerRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectEventTrigger} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectEventTrigger> findByCriteria(ProjectEventTriggerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectEventTrigger> specification = createSpecification(criteria);
        return projectEventTriggerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectEventTrigger} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectEventTrigger> findByCriteria(ProjectEventTriggerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectEventTrigger> specification = createSpecification(criteria);
        return projectEventTriggerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectEventTriggerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectEventTrigger> specification = createSpecification(criteria);
        return projectEventTriggerRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectEventTriggerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectEventTrigger> createSpecification(ProjectEventTriggerCriteria criteria) {
        Specification<ProjectEventTrigger> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectEventTrigger_.id));
            }
            if (criteria.getEditorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditorId(), ProjectEventTrigger_.editorId));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), ProjectEventTrigger_.createdAt));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectEventTrigger_.name));
            }
            if (criteria.getRecurrence() != null) {
                specification = specification.and(buildSpecification(criteria.getRecurrence(), ProjectEventTrigger_.recurrence));
            }
            if (criteria.getDisabled() != null) {
                specification = specification.and(buildSpecification(criteria.getDisabled(), ProjectEventTrigger_.disabled));
            }
            if (criteria.getEditorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditorName(), ProjectEventTrigger_.editorName));
            }
            if (criteria.getHour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHour(), ProjectEventTrigger_.hour));
            }
            if (criteria.getMinute() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinute(), ProjectEventTrigger_.minute));
            }
            if (criteria.getFirstStartedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstStartedAt(), ProjectEventTrigger_.firstStartedAt));
            }
            if (criteria.getSheduledOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSheduledOn(), ProjectEventTrigger_.sheduledOn));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), ProjectEventTrigger_.processId));
            }
        }
        return specification;
    }
}
