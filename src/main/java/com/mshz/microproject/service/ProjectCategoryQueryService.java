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

import com.mshz.microproject.domain.ProjectCategory;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectCategoryRepository;
import com.mshz.microproject.service.dto.ProjectCategoryCriteria;

/**
 * Service for executing complex queries for {@link ProjectCategory} entities in the database.
 * The main input is a {@link ProjectCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectCategory} or a {@link Page} of {@link ProjectCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectCategoryQueryService extends QueryService<ProjectCategory> {

    private final Logger log = LoggerFactory.getLogger(ProjectCategoryQueryService.class);

    private final ProjectCategoryRepository projectCategoryRepository;

    public ProjectCategoryQueryService(ProjectCategoryRepository projectCategoryRepository) {
        this.projectCategoryRepository = projectCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectCategory> findByCriteria(ProjectCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectCategory> specification = createSpecification(criteria);
        return projectCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCategory> findByCriteria(ProjectCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectCategory> specification = createSpecification(criteria);
        return projectCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectCategory> specification = createSpecification(criteria);
        return projectCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectCategory> createSpecification(ProjectCategoryCriteria criteria) {
        Specification<ProjectCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectCategory_.description));
            }
        }
        return specification;
    }
}
