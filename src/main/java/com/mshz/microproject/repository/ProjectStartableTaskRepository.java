package com.mshz.microproject.repository;

import java.util.List;

import com.mshz.microproject.domain.ProjectStartableTask;
import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectStartableTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectStartableTaskRepository extends JpaRepository<ProjectStartableTask, Long>, JpaSpecificationExecutor<ProjectStartableTask> {

    List<ProjectStartableTask> findByTriggerTaskIdAndStartCond(Long triggerTaskId, ProjectStartableTaskCond startCond);

    List<ProjectStartableTask> findByTriggerTaskIdAndStartCondIn(Long taskId, List<ProjectStartableTaskCond> conds);
}
