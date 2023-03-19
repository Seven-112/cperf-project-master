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

import com.mshz.microproject.domain.ProjectComment;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectCommentRepository;
import com.mshz.microproject.service.dto.ProjectCommentCriteria;

/**
 * Service for executing complex queries for {@link ProjectComment} entities in the database.
 * The main input is a {@link ProjectCommentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectComment} or a {@link Page} of {@link ProjectComment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectCommentQueryService extends QueryService<ProjectComment> {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentQueryService.class);

    private final ProjectCommentRepository projectCommentRepository;

    public ProjectCommentQueryService(ProjectCommentRepository projectCommentRepository) {
        this.projectCommentRepository = projectCommentRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectComment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectComment> findByCriteria(ProjectCommentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectComment> specification = createSpecification(criteria);
        return projectCommentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectComment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectComment> findByCriteria(ProjectCommentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectComment> specification = createSpecification(criteria);
        return projectCommentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCommentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectComment> specification = createSpecification(criteria);
        return projectCommentRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCommentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectComment> createSpecification(ProjectCommentCriteria criteria) {
        Specification<ProjectComment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectComment_.id));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectId(), ProjectComment_.projectId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), ProjectComment_.userId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), ProjectComment_.userName));
            }
            if (criteria.getUserEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserEmail(), ProjectComment_.userEmail));
            }
        }
        return specification;
    }
}
