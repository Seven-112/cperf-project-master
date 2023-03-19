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

import com.mshz.microproject.domain.ProjectTaskValidationControl;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskValidationControlRepository;
import com.mshz.microproject.service.dto.ProjectTaskValidationControlCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskValidationControl} entities in the database.
 * The main input is a {@link ProjectTaskValidationControlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskValidationControl} or a {@link Page} of {@link ProjectTaskValidationControl} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskValidationControlQueryService extends QueryService<ProjectTaskValidationControl> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskValidationControlQueryService.class);

    private final ProjectTaskValidationControlRepository projectTaskValidationControlRepository;

    public ProjectTaskValidationControlQueryService(ProjectTaskValidationControlRepository projectTaskValidationControlRepository) {
        this.projectTaskValidationControlRepository = projectTaskValidationControlRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskValidationControl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskValidationControl> findByCriteria(ProjectTaskValidationControlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskValidationControl> specification = createSpecification(criteria);
        return projectTaskValidationControlRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskValidationControl} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskValidationControl> findByCriteria(ProjectTaskValidationControlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskValidationControl> specification = createSpecification(criteria);
        return projectTaskValidationControlRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskValidationControlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskValidationControl> specification = createSpecification(criteria);
        return projectTaskValidationControlRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskValidationControlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskValidationControl> createSpecification(ProjectTaskValidationControlCriteria criteria) {
        Specification<ProjectTaskValidationControl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskValidationControl_.id));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), ProjectTaskValidationControl_.label));
            }
            if (criteria.getRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getRequired(), ProjectTaskValidationControl_.required));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), ProjectTaskValidationControl_.valid));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), ProjectTaskValidationControl_.taskId));
            }
        }
        return specification;
    }
}
