package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectFileRepository extends JpaRepository<ProjectFile, Long>, JpaSpecificationExecutor<ProjectFile> {
}
