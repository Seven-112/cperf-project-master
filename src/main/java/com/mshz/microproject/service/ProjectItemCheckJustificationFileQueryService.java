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

import com.mshz.microproject.domain.ProjectItemCheckJustificationFile;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectItemCheckJustificationFileRepository;
import com.mshz.microproject.service.dto.ProjectItemCheckJustificationFileCriteria;

/**
 * Service for executing complex queries for {@link ProjectItemCheckJustificationFile} entities in the database.
 * The main input is a {@link ProjectItemCheckJustificationFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectItemCheckJustificationFile} or a {@link Page} of {@link ProjectItemCheckJustificationFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectItemCheckJustificationFileQueryService extends QueryService<ProjectItemCheckJustificationFile> {

    private final Logger log = LoggerFactory.getLogger(ProjectItemCheckJustificationFileQueryService.class);

    private final ProjectItemCheckJustificationFileRepository projectItemCheckJustificationFileRepository;

    public ProjectItemCheckJustificationFileQueryService(ProjectItemCheckJustificationFileRepository projectItemCheckJustificationFileRepository) {
        this.projectItemCheckJustificationFileRepository = projectItemCheckJustificationFileRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectItemCheckJustificationFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectItemCheckJustificationFile> findByCriteria(ProjectItemCheckJustificationFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectItemCheckJustificationFile> specification = createSpecification(criteria);
        return projectItemCheckJustificationFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectItemCheckJustificationFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectItemCheckJustificationFile> findByCriteria(ProjectItemCheckJustificationFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectItemCheckJustificationFile> specification = createSpecification(criteria);
        return projectItemCheckJustificationFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectItemCheckJustificationFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectItemCheckJustificationFile> specification = createSpecification(criteria);
        return projectItemCheckJustificationFileRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectItemCheckJustificationFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectItemCheckJustificationFile> createSpecification(ProjectItemCheckJustificationFileCriteria criteria) {
        Specification<ProjectItemCheckJustificationFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectItemCheckJustificationFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), ProjectItemCheckJustificationFile_.fileId));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), ProjectItemCheckJustificationFile_.fileName));
            }
            if (criteria.getTicjId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTicjId(), ProjectItemCheckJustificationFile_.ticjId));
            }
        }
        return specification;
    }
}
