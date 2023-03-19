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

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectRepository;
import com.mshz.microproject.service.dto.ProjectCriteria;

/**
 * Service for executing complex queries for {@link Project} entities in the database.
 * The main input is a {@link ProjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Project} or a {@link Page} of {@link Project} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectQueryService extends QueryService<Project> {

    private final Logger log = LoggerFactory.getLogger(ProjectQueryService.class);

    private final ProjectRepository projectRepository;

    public ProjectQueryService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Return a {@link List} of {@link Project} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Project> findByCriteria(ProjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Project} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Project> findByCriteria(ProjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Project> createSpecification(ProjectCriteria criteria) {
        Specification<Project> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Project_.id));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), Project_.label));
            }
            if (criteria.getPriorityLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getPriorityLevel(), Project_.priorityLevel));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), Project_.valid));
            }
            if (criteria.getPreviewStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreviewStartAt(), Project_.previewStartAt));
            }
            if (criteria.getStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAt(), Project_.startAt));
            }
            if (criteria.getPreviewFinishAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreviewFinishAt(), Project_.previewFinishAt));
            }
            if (criteria.getFinishedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishedAt(), Project_.finishedAt));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Project_.createdAt));
            }
            if (criteria.getStartCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartCount(), Project_.startCount));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentId(), Project_.parentId));
            }
            if (criteria.getEditorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditorId(), Project_.editorId));
            }
            if (criteria.getRunnableProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRunnableProcessId(), Project_.runnableProcessId));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategoryId(), Project_.categoryId));
            }
            if (criteria.getResponsableId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResponsableId(), Project_.responsableId));
            }
            if (criteria.getResponsableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsableName(), Project_.responsableName));
            }
            if (criteria.getResponsableEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsableEmail(), Project_.responsableEmail));
            }
            if (criteria.getPonderation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPonderation(), Project_.ponderation));
            }
            if (criteria.getTaskGlobalPonderation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskGlobalPonderation(), Project_.taskGlobalPonderation));
            }
            if (criteria.getPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPath(), Project_.path));
            }
        }
        return specification;
    }
}
