package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectTaskSubmission;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskSubmissionRepository extends JpaRepository<ProjectTaskSubmission, Long>, JpaSpecificationExecutor<ProjectTaskSubmission> {

	@Modifying
	@Query("delete from ProjectTaskSubmission ts where ts.taskId=:tid")
    void deleteByTaskId(@Param("tid") Long id);
}
