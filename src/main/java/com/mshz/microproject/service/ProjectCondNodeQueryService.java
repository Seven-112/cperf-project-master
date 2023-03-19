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

import com.mshz.microproject.domain.ProjectCondNode;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectCondNodeRepository;
import com.mshz.microproject.service.dto.ProjectCondNodeCriteria;

/**
 * Service for executing complex queries for {@link ProjectCondNode} entities in the database.
 * The main input is a {@link ProjectCondNodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectCondNode} or a {@link Page} of {@link ProjectCondNode} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectCondNodeQueryService extends QueryService<ProjectCondNode> {

    private final Logger log = LoggerFactory.getLogger(ProjectCondNodeQueryService.class);

    private final ProjectCondNodeRepository projectCondNodeRepository;

    public ProjectCondNodeQueryService(ProjectCondNodeRepository projectCondNodeRepository) {
        this.projectCondNodeRepository = projectCondNodeRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectCondNode} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectCondNode> findByCriteria(ProjectCondNodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectCondNode> specification = createSpecification(criteria);
        return projectCondNodeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectCondNode} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCondNode> findByCriteria(ProjectCondNodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectCondNode> specification = createSpecification(criteria);
        return projectCondNodeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCondNodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectCondNode> specification = createSpecification(criteria);
        return projectCondNodeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCondNodeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectCondNode> createSpecification(ProjectCondNodeCriteria criteria) {
        Specification<ProjectCondNode> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectCondNode_.id));
            }
            if (criteria.getLogigramPosX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosX(), ProjectCondNode_.logigramPosX));
            }
            if (criteria.getLogigramPosY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosY(), ProjectCondNode_.logigramPosY));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectId(), ProjectCondNode_.projectId));
            }
        }
        return specification;
    }
}
