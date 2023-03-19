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

import com.mshz.microproject.domain.ProjectTaskUser;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskUserRepository;
import com.mshz.microproject.service.dto.ProjectTaskUserCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskUser} entities in the database.
 * The main input is a {@link ProjectTaskUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskUser} or a {@link Page} of {@link ProjectTaskUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskUserQueryService extends QueryService<ProjectTaskUser> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskUserQueryService.class);

    private final ProjectTaskUserRepository projectTaskUserRepository;

    public ProjectTaskUserQueryService(ProjectTaskUserRepository projectTaskUserRepository) {
        this.projectTaskUserRepository = projectTaskUserRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskUser> findByCriteria(ProjectTaskUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskUser> specification = createSpecification(criteria);
        return projectTaskUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskUser> findByCriteria(ProjectTaskUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskUser> specification = createSpecification(criteria);
        return projectTaskUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskUser> specification = createSpecification(criteria);
        return projectTaskUserRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskUser> createSpecification(ProjectTaskUserCriteria criteria) {
        Specification<ProjectTaskUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskUser_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), ProjectTaskUser_.userId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), ProjectTaskUser_.userName));
            }
            if (criteria.getUserEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserEmail(), ProjectTaskUser_.userEmail));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildSpecification(criteria.getRole(), ProjectTaskUser_.role));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), ProjectTaskUser_.taskId));
            }
        }
        return specification;
    }
}
