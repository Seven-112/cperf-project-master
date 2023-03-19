package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectTaskValidationControl;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskValidationControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskValidationControlRepository extends JpaRepository<ProjectTaskValidationControl, Long>, JpaSpecificationExecutor<ProjectTaskValidationControl> {

	@Modifying
	@Query("delete from ProjectTaskValidationControl tvc where tvc.taskId=:tid")
    void deletByTaskId(@Param("tid") Long id);
}
