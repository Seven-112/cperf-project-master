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

import com.mshz.microproject.domain.ProjectPublicHoliday;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectPublicHolidayRepository;
import com.mshz.microproject.service.dto.ProjectPublicHolidayCriteria;

/**
 * Service for executing complex queries for {@link ProjectPublicHoliday} entities in the database.
 * The main input is a {@link ProjectPublicHolidayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectPublicHoliday} or a {@link Page} of {@link ProjectPublicHoliday} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectPublicHolidayQueryService extends QueryService<ProjectPublicHoliday> {

    private final Logger log = LoggerFactory.getLogger(ProjectPublicHolidayQueryService.class);

    private final ProjectPublicHolidayRepository projectPublicHolidayRepository;

    public ProjectPublicHolidayQueryService(ProjectPublicHolidayRepository projectPublicHolidayRepository) {
        this.projectPublicHolidayRepository = projectPublicHolidayRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectPublicHoliday} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectPublicHoliday> findByCriteria(ProjectPublicHolidayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectPublicHoliday> specification = createSpecification(criteria);
        return projectPublicHolidayRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectPublicHoliday} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectPublicHoliday> findByCriteria(ProjectPublicHolidayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectPublicHoliday> specification = createSpecification(criteria);
        return projectPublicHolidayRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectPublicHolidayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectPublicHoliday> specification = createSpecification(criteria);
        return projectPublicHolidayRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectPublicHolidayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectPublicHoliday> createSpecification(ProjectPublicHolidayCriteria criteria) {
        Specification<ProjectPublicHoliday> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectPublicHoliday_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectPublicHoliday_.name));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), ProjectPublicHoliday_.date));
            }
            if (criteria.getLevelId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLevelId(), ProjectPublicHoliday_.levelId));
            }
            if (criteria.getProcessId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessId(), ProjectPublicHoliday_.processId));
            }
        }
        return specification;
    }
}
