package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Long>, JpaSpecificationExecutor<ProjectComment> {
}
