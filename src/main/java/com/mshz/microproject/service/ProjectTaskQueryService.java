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

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectTaskRepository;
import com.mshz.microproject.service.dto.ProjectTaskCriteria;

/**
 * Service for executing complex queries for {@link ProjectTask} entities in the database.
 * The main input is a {@link ProjectTaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTask} or a {@link Page} of {@link ProjectTask} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTaskQueryService extends QueryService<ProjectTask> {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskQueryService.class);

    private final ProjectTaskRepository projectTaskRepository;

    public ProjectTaskQueryService(ProjectTaskRepository projectTaskRepository) {
        this.projectTaskRepository = projectTaskRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTask} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTask> findByCriteria(ProjectTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTask> specification = createSpecification(criteria);
        return projectTaskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTask} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTask> findByCriteria(ProjectTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTask> specification = createSpecification(criteria);
        return projectTaskRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTask> specification = createSpecification(criteria);
        return projectTaskRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectTaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectTask> createSpecification(ProjectTaskCriteria criteria) {
        Specification<ProjectTask> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectTask_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectTask_.name));
            }
            if (criteria.getNbMinuites() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbMinuites(), ProjectTask_.nbMinuites));
            }
            if (criteria.getNbHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbHours(), ProjectTask_.nbHours));
            }
            if (criteria.getNbDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbDays(), ProjectTask_.nbDays));
            }
            if (criteria.getNbMonths() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbMonths(), ProjectTask_.nbMonths));
            }
            if (criteria.getNbYears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbYears(), ProjectTask_.nbYears));
            }
            if (criteria.getStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAt(), ProjectTask_.startAt));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ProjectTask_.status));
            }
            if (criteria.getPriorityLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getPriorityLevel(), ProjectTask_.priorityLevel));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), ProjectTask_.type));
            }
            if (criteria.getValid() != null) {
                specification = specification.and(buildSpecification(criteria.getValid(), ProjectTask_.valid));
            }
            if (criteria.getFinishAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishAt(), ProjectTask_.finishAt));
            }
            if (criteria.getStartWithProcess() != null) {
                specification = specification.and(buildSpecification(criteria.getStartWithProcess(), ProjectTask_.startWithProcess));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), ProjectTask_.processId));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentId(), ProjectTask_.parentId));
            }
            if (criteria.getTaskModelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskModelId(), ProjectTask_.taskModelId));
            }
            if (criteria.getPauseAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPauseAt(), ProjectTask_.pauseAt));
            }
            if (criteria.getNbPause() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbPause(), ProjectTask_.nbPause));
            }
            if (criteria.getLogigramPosX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosX(), ProjectTask_.logigramPosX));
            }
            if (criteria.getLogigramPosY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogigramPosY(), ProjectTask_.logigramPosY));
            }
            if (criteria.getGroupId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGroupId(), ProjectTask_.groupId));
            }
            if (criteria.getRiskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRiskId(), ProjectTask_.riskId));
            }
            if (criteria.getManualMode() != null) {
                specification = specification.and(buildSpecification(criteria.getManualMode(), ProjectTask_.manualMode));
            }
            if (criteria.getSheduledStartAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSheduledStartAt(), ProjectTask_.sheduledStartAt));
            }
            if (criteria.getSheduledStartHour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSheduledStartHour(), ProjectTask_.sheduledStartHour));
            }
            if (criteria.getSheduledStartMinute() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSheduledStartMinute(), ProjectTask_.sheduledStartMinute));
            }
            if (criteria.getStartupTaskId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartupTaskId(), ProjectTask_.startupTaskId));
            }
            if (criteria.getPonderation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPonderation(), ProjectTask_.ponderation));
            }
            if (criteria.getChecked() != null) {
                specification = specification.and(buildSpecification(criteria.getChecked(), ProjectTask_.checked));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), ProjectTask_.createdAt));
            }
            if (criteria.getCurrentPauseHistoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentPauseHistoryId(), ProjectTask_.currentPauseHistoryId));
            }
        }
        return specification;
    }
}
