package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectCommentFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectCommentFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCommentFileRepository extends JpaRepository<ProjectCommentFile, Long>, JpaSpecificationExecutor<ProjectCommentFile> {
}
