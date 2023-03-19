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

import com.mshz.microproject.domain.ProjectCommentFile;
import com.mshz.microproject.domain.*; // for static metamodels
import com.mshz.microproject.repository.ProjectCommentFileRepository;
import com.mshz.microproject.service.dto.ProjectCommentFileCriteria;

/**
 * Service for executing complex queries for {@link ProjectCommentFile} entities in the database.
 * The main input is a {@link ProjectCommentFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectCommentFile} or a {@link Page} of {@link ProjectCommentFile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectCommentFileQueryService extends QueryService<ProjectCommentFile> {

    private final Logger log = LoggerFactory.getLogger(ProjectCommentFileQueryService.class);

    private final ProjectCommentFileRepository projectCommentFileRepository;

    public ProjectCommentFileQueryService(ProjectCommentFileRepository projectCommentFileRepository) {
        this.projectCommentFileRepository = projectCommentFileRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectCommentFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectCommentFile> findByCriteria(ProjectCommentFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectCommentFile> specification = createSpecification(criteria);
        return projectCommentFileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectCommentFile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCommentFile> findByCriteria(ProjectCommentFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectCommentFile> specification = createSpecification(criteria);
        return projectCommentFileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCommentFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectCommentFile> specification = createSpecification(criteria);
        return projectCommentFileRepository.count(specification);
    }

    /**
     * Function to convert {@link ProjectCommentFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProjectCommentFile> createSpecification(ProjectCommentFileCriteria criteria) {
        Specification<ProjectCommentFile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProjectCommentFile_.id));
            }
            if (criteria.getFileId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileId(), ProjectCommentFile_.fileId));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), ProjectCommentFile_.fileName));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommentId(), ProjectCommentFile_.commentId));
            }
        }
        return specification;
    }
}
