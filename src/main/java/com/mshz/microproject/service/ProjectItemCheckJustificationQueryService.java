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

import com.mshz.microproject.domain.ProjectItemCheckJustification;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectItemCheckJustificationRepository;
import com.mshz.microproject.service.dto.ProjectItemCheckJustificationCriteria;

/**
 * Service for executing complex queries for {@link ProjectItemCheckJustification} entities in the database.
 * The main input is a {@link ProjectItemCheckJustificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectItemCheckJustification} or a {@link Page} of {@link ProjectItemCheckJustification} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectItemCheckJustificationQueryService extends QueryService<ProjectItemCheckJustification> {

    private final Logger log = LoggerFactory.getLogger(ProjectItemCheckJustificationQueryService.class);

    private final ProjectItemCheckJustificationRepository projectItemCheckJustificationRepository;

    public ProjectItemCheckJustificationQueryService(ProjectItemCheckJustificationRepository projectItemCheckJustificationRepository) {
        this.projectItemCheckJustificationRepository = projectItemCheckJustificationRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectItemCheckJustification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectItemCheckJustification> findByCriteria(ProjectItemCheckJustificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectItemCheckJustification> specification = createSpecification(criteria);
        return projectItemCheckJustificationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectItemCheckJustification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectItemCheckJustification> findByCriteria(ProjectItemCheckJustificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectItemCheckJustification> specification = createSpecification(criteria);
        return projectItemCheckJustificationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectItemCheckJustificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectItemCheckJustification> specification = createSpecification(criteria);
        return projectItemCheckJustificationRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectItemCheckJustificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectItemCheckJustification> createSpecification(ProjectItemCheckJustificationCriteria criteria) {
        Specification<ProjectItemCheckJustification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectItemCheckJustification_.id));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), ProjectItemCheckJustification_.checked));
            }
            if (criteria.getTaskItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskItemId(), ProjectItemCheckJustification_.taskItemId));
            }
            if (criteria.getDateAndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAndTime(), ProjectItemCheckJustification_.dateAndTime));
            }
        }
        return specification;
    }
}
