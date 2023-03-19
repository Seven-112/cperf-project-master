package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectTaskStatusTrakingFile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskStatusTrakingFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskStatusTrakingFileRepository extends JpaRepository<ProjectTaskStatusTrakingFile, Long>, JpaSpecificationExecutor<ProjectTaskStatusTrakingFile> {
}
