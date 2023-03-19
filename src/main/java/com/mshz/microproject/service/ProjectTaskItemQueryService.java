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

import com.mshz.microproject.domain.ProjectTaskItem;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskItemRepository;
import com.mshz.microproject.service.dto.ProjectTaskItemCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskItem} entities in the database.
 * The main input is a {@link ProjectTaskItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskItem} or a {@link Page} of {@link ProjectTaskItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskItemQueryService extends QueryService<ProjectTaskItem> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskItemQueryService.class);

    private final ProjectTaskItemRepository projectTaskItemRepository;

    public ProjectTaskItemQueryService(ProjectTaskItemRepository projectTaskItemRepository) {
        this.projectTaskItemRepository = projectTaskItemRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskItem> findByCriteria(ProjectTaskItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskItem> specification = createSpecification(criteria);
        return projectTaskItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskItem> findByCriteria(ProjectTaskItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskItem> specification = createSpecification(criteria);
        return projectTaskItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskItem> specification = createSpecification(criteria);
        return projectTaskItemRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskItem> createSpecification(ProjectTaskItemCriteria criteria) {
        Specification<ProjectTaskItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskItem_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectTaskItem_.name));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), ProjectTaskItem_.taskId));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), ProjectTaskItem_.checked));
            }
            if (criteria.getCheckerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCheckerId(), ProjectTaskItem_.checkerId));
            }
            if (criteria.getCheckerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckerName(), ProjectTaskItem_.checkerName));
            }
            if (criteria.getCheckerEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckerEmail(), ProjectTaskItem_.checkerEmail));
            }
            if (criteria.getEditorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditorId(), ProjectTaskItem_.editorId));
            }
            if (criteria.getEditorEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditorEmail(), ProjectTaskItem_.editorEmail));
            }
            if (criteria.getEditorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditorName(), ProjectTaskItem_.editorName));
            }
            if (criteria.getRequired() != null) {
                specification = specification.and(buildSpecification(criteria.getRequired(), ProjectTaskItem_.required));
            }
        }
        return specification;
    }
}
