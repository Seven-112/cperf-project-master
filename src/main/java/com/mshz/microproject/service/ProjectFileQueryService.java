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

import com.mshz.microproject.domain.ProjectFile;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectFileRepository;
import com.mshz.microproject.service.dto.ProjectFileCriteria;

/**
 * Service for executing complex queries for {@link ProjectFile} entities in the database.
 * The main input is a {@link ProjectFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectFile} or a {@link Page} of {@link ProjectFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectFileQueryService extends QueryService<ProjectFile> {

    private final Logger log = LoggerFactory.getLogger(ProjectFileQueryService.class);

    private final ProjectFileRepository projectFileRepository;

    public ProjectFileQueryService(ProjectFileRepository projectFileRepository) {
        this.projectFileRepository = projectFileRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectFile> findByCriteria(ProjectFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectFile> specification = createSpecification(criteria);
        return projectFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectFile> findByCriteria(ProjectFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectFile> specification = createSpecification(criteria);
        return projectFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectFile> specification = createSpecification(criteria);
        return projectFileRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectFile> createSpecification(ProjectFileCriteria criteria) {
        Specification<ProjectFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), ProjectFile_.fileId));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), ProjectFile_.fileName));
            }
            if (criteria.getFileType() != null) {
                specification = specification.and(buildSpecification(criteria.getFileType(), ProjectFile_.fileType));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectId(), ProjectFile_.projectId));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), ProjectFile_.userId));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), ProjectFile_.userName));
            }
            if (criteria.getUserEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserEmail(), ProjectFile_.userEmail));
            }
        }
        return specification;
    }
}
