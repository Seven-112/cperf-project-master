package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectTaskStatusTraking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskStatusTraking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskStatusTrakingRepository extends JpaRepository<ProjectTaskStatusTraking, Long>, JpaSpecificationExecutor<ProjectTaskStatusTraking> {
}
