package com.mshz.microproject.repository;

import java.util.List;

import com.mshz.microproject.domain.ProjectTaskItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskItemRepository extends JpaRepository<ProjectTaskItem, Long>, JpaSpecificationExecutor<ProjectTaskItem> {

    List<ProjectTaskItem> findByTaskId(Long taskId);

	@Modifying
	@Query("delete from ProjectTaskItem ti where ti.taskId=:tid")
    void deleteByTaskId(@Param("tid") Long id);

	@Modifying(flushAutomatically = true)
	@Query("update ProjectTaskItem ti set ti.checked=:cheked where ti.taskId=:tid")
    void updateCheckedByTaskId(@Param("tid") Long id, @Param("cheked") Boolean checked);

    ProjectTaskItem findFirstByTaskIdAndRequiredAndChecked(Long taskId, Boolean true1, Boolean false1);

    List<ProjectTaskItem> findByTaskIdAndCheckedIsNot(Long taskId, Boolean true1);
}
