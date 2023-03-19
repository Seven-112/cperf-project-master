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

import com.mshz.microproject.domain.ProjectEdgeInfo;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectEdgeInfoRepository;
import com.mshz.microproject.service.dto.ProjectEdgeInfoCriteria;

/**
 * Service for executing complex queries for {@link ProjectEdgeInfo} entities in the database.
 * The main input is a {@link ProjectEdgeInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectEdgeInfo} or a {@link Page} of {@link ProjectEdgeInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectEdgeInfoQueryService extends QueryService<ProjectEdgeInfo> {

    private final Logger log = LoggerFactory.getLogger(ProjectEdgeInfoQueryService.class);

    private final ProjectEdgeInfoRepository projectEdgeInfoRepository;

    public ProjectEdgeInfoQueryService(ProjectEdgeInfoRepository projectEdgeInfoRepository) {
        this.projectEdgeInfoRepository = projectEdgeInfoRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectEdgeInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectEdgeInfo> findByCriteria(ProjectEdgeInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectEdgeInfo> specification = createSpecification(criteria);
        return projectEdgeInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectEdgeInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectEdgeInfo> findByCriteria(ProjectEdgeInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectEdgeInfo> specification = createSpecification(criteria);
        return projectEdgeInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectEdgeInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectEdgeInfo> specification = createSpecification(criteria);
        return projectEdgeInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectEdgeInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectEdgeInfo> createSpecification(ProjectEdgeInfoCriteria criteria) {
        Specification<ProjectEdgeInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectEdgeInfo_.id));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), ProjectEdgeInfo_.source));
            }
            if (criteria.getTarget() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTarget(), ProjectEdgeInfo_.target));
            }
            if (criteria.getSourceHandle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceHandle(), ProjectEdgeInfo_.sourceHandle));
            }
            if (criteria.getTargetHandle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTargetHandle(), ProjectEdgeInfo_.targetHandle));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), ProjectEdgeInfo_.processId));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), ProjectEdgeInfo_.valid));
            }
        }
        return specification;
    }
}
