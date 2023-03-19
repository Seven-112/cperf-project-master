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

import com.mshz.microproject.domain.ProjectTaskPauseHistory;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskPauseHistoryRepository;
import com.mshz.microproject.service.dto.ProjectTaskPauseHistoryCriteria;

/**
 * Service for executing complex queries for {@link ProjectTaskPauseHistory} entities in the database.
 * The main input is a {@link ProjectTaskPauseHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTaskPauseHistory} or a {@link Page} of {@link ProjectTaskPauseHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskPauseHistoryQueryService extends QueryService<ProjectTaskPauseHistory> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskPauseHistoryQueryService.class);

    private final ProjectTaskPauseHistoryRepository projectTaskPauseHistoryRepository;

    public ProjectTaskPauseHistoryQueryService(ProjectTaskPauseHistoryRepository projectTaskPauseHistoryRepository) {
        this.projectTaskPauseHistoryRepository = projectTaskPauseHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTaskPauseHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTaskPauseHistory> findByCriteria(ProjectTaskPauseHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTaskPauseHistory> specification = createSpecification(criteria);
        return projectTaskPauseHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTaskPauseHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTaskPauseHistory> findByCriteria(ProjectTaskPauseHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTaskPauseHistory> specification = createSpecification(criteria);
        return projectTaskPauseHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskPauseHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTaskPauseHistory> specification = createSpecification(criteria);
        return projectTaskPauseHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskPauseHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTaskPauseHistory> createSpecification(ProjectTaskPauseHistoryCriteria criteria) {
        Specification<ProjectTaskPauseHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTaskPauseHistory_.id));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskId(), ProjectTaskPauseHistory_.taskId));
            }
            if (criteria.getPausedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPausedAt(), ProjectTaskPauseHistory_.pausedAt));
            }
            if (criteria.getRestartedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRestartedAt(), ProjectTaskPauseHistory_.restartedAt));
            }
            if (criteria.getOldTaskstatus() != null) {
                specification = specification.and(buildSpecification(criteria.getOldTaskstatus(), ProjectTaskPauseHistory_.oldTaskstatus));
            }
            if (criteria.getTaskExecutionDeleyExeceed() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskExecutionDeleyExeceed(), ProjectTaskPauseHistory_.taskExecutionDeleyExeceed));
            }
        }
        return specification;
    }
}
