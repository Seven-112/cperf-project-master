package com.mshz.microproject.repository;

import com.mshz.microproject.domain.ProjectTaskPauseHistory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTaskPauseHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskPauseHistoryRepository extends JpaRepository<ProjectTaskPauseHistory, Long>, JpaSpecificationExecutor<ProjectTaskPauseHistory> {
	List<ProjectTaskPauseHistory> findByTaskIdAndTaskExecutionDeleyExeceedAndPausedAtNotNullAndRestartedAtNotNull(Long taskId,
			Boolean taskExecutionTimeExceceed);

	void deleteByTaskId(Long taskId);

	Page<ProjectTaskPauseHistory> findByTaskIdAndPausedAtNotNullAndRestartedAtNotNull(Long taskId, Pageable pageable);
}
