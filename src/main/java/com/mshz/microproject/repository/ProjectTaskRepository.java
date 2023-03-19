package com.mshz.microproject.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import com.mshz.microproject.domain.enumeration.ProjectTaskUserRole;
import com.mshz.microproject.domain.projection.ITaskProject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long>, JpaSpecificationExecutor<ProjectTask> {
    

	List<ProjectTask> findByProcessId(Long id);

	Optional<ProjectTask> findByTaskModelIdAndProcessId(Long parentId, Long newProcessId);

	Set<ProjectTask> findByProcessIdAndTaskModelIdIn(Long newProcessId, Set<ProjectTask> startupTasks);
	
	List<ProjectTask> findByStartupTaskId(Long startupId);

    // get all task to play automatically
    @Query("select t from ProjectTask t where t.status =:status and t.processId in :processIds "+
           "and (t.manualMode = false or t.manualMode=null)")
    List<ProjectTask> getAllTaskToPlayAutomatically(@Param("status") ProjectTaskStatus status, @Param("processIds") List<Long> processIds);
    
    // get all task to pause automatically
    List<ProjectTask> findByStatusAndProcessIdInAndManualModeNot(ProjectTaskStatus status, List<Long> processIds, Boolean manualMode);
   
    // page tasks by employee id
    @Query("SELECT DISTINCT t as task, p as project FROM ProjectTask t "
    		+ "JOIN Project p on t.processId = p.id "
    		+ "LEFT JOIN ProjectTaskUser tu on t.id=tu.taskId " 
            +"WHERE tu.userId =:empId AND p.valid =:valid")
    Page<ITaskProject> findByEmployeeId(@Param("empId") Long empId, @Param("valid") Boolean valid, Pageable pageable);    
    
    // page tasks by employee id and status
    @Query("SELECT DISTINCT t as task, p as project FROM ProjectTask t "
    		+ "JOIN Project p on t.processId = p.id "
    		+ "LEFT JOIN ProjectTaskUser tu on t.id=tu.taskId " 
            +"WHERE tu.userId =:empId AND t.status =:status AND p.valid =:valid")
    Page<ITaskProject> findByEmployeeIdAndStatus(@Param("empId") Long empId, @Param("valid") Boolean valid, @Param("status") ProjectTaskStatus status, Pageable pageable);


    // page tasks by employee id and status
    @Query("SELECT DISTINCT t as task, p as project FROM ProjectTask t "
    		+ "JOIN Project p on t.processId = p.id "
                + "JOIN ProjectTaskItem ti on ti.taskId = t.id "
            +"WHERE ti.checkerId =:empId AND t.status in :status AND p.valid =:valid")
    Page<ITaskProject> getTaskCheckListByUserIdAndAndValidAndProjectTaskStatus(@Param("empId") Long empId, @Param("valid") Boolean valid, @Param("status") List<ProjectTaskStatus> status, Pageable pageable);
    

    // find tasks by employee id and process startAt beetwen
    @Query("SELECT DISTINCT t as task FROM ProjectTask t JOIN Project p on t.processId = p.id LEFT JOIN ProjectTaskUser tu on t.id=tu.taskId " 
            +"WHERE tu.userId =:empId AND p.createdAt != null AND (p.createdAt between :startAt AND :endAt) AND p.valid =:valid")
    List<ProjectTask> findByEmployeeTasksByInstancesCreatedAtBetween(@Param("empId") Long employeeId, @Param("startAt") Instant startAt,  @Param("endAt") Instant endAt, @Param("valid") Boolean valid);
    
    @Query("SELECT t FROM ProjectTask t WHERE t.id != :tid AND t.processId = :pid AND (t.startupTaskId = null OR t.startupTaskId!= :tid)")
    Page<ProjectTask> getTasksToStartupAssociable(@Param("tid") Long taskToAssociateId, @Param("pid") Long processId,  Pageable page);
    
    List<ProjectTask> findByParentId(Long parentId);

    List<ProjectTask> findByProcessIdAndStatus(Long processId, ProjectTaskStatus ProjectTaskStatus);

    // change manual mode 
    @Modifying(flushAutomatically = true)
    @Query("UPDATE ProjectTask t set t.manualMode =:manualMode WHERE t.processId =:processId")
    void updateManualModeByProcessId(@Param("manualMode") Boolean manualMode, @Param("processId") Long processId);

    List<ProjectTask> findByIdIn(List<Long> childrenIds);

    @Query("select distinct t from ProjectTask t join Project p on p.id=t.processId "+
           "where t.status =:status and p.valid=:pValid "+
           "and t.sheduledStartAt=:dte and t.sheduledStartHour=:hour and t.sheduledStartMinute=:minute")
    List<ProjectTask> getShudledTasks(@Param("status") ProjectTaskStatus status, @Param("dte") LocalDate localDate,
        @Param("hour") int hour, @Param("minute") int minute, @Param("pValid") Boolean projectValid);
   
   @Modifying
   @Query("update ProjectTask t set t.startupTaskId=null where t.startupTaskId=:tid")
   void unLinkStartupAssociationsByTaskId(@Param("tid") Long id);   
   
   @Modifying
   @Query("update ProjectTask t set t.parentId=null where t.parentId=:tid")
   void unLinkParentIdByTaskId(@Param("tid") Long id);
   
    @Modifying
    @Query("delete from ProjectTask t where t.processId=:pid")
    void deleteByProjectId(@Param("pid") Long id);

    List<ProjectTask> findByProcessIdAndStatusNot(Long projectId, ProjectTaskStatus canceled);

    List<ProjectTask> findByProcessIdAndStatusNotAndValid(Long projectId, ProjectTaskStatus canceled, Boolean valid);

    ProjectTask findFirstByProcessIdAndValidAndStatusNotInOrderByStartAtAsc(Long projectId, Boolean true1,List<ProjectTaskStatus> status);


    @Query("SELECT DISTINCT t as task, p as project FROM ProjectTask t "
    		+ "JOIN Project p on t.processId = p.id "
    		+ "LEFT JOIN ProjectTaskUser tu on t.id=tu.taskId " 
            +"WHERE tu.userId =:userId AND tu.role=:role AND t.status =:status AND p.valid =:valid")
    Page<ITaskProject> findByUserIdAndRoleAndTaskStatus(@Param("userId") Long userId, @Param("valid") Boolean valid, 
                @Param("status") ProjectTaskStatus status, @Param("role") ProjectTaskUserRole role, Pageable pageable);

    @Query("SELECT DISTINCT t as task, p as project FROM ProjectTask t "
            + "JOIN Project p on t.processId = p.id "
            + "LEFT JOIN ProjectTaskUser tu on t.id=tu.taskId " 
            +"WHERE tu.userId =:userId AND tu.role=:role AND t.status IN :status AND p.valid =:valid")
    Page<ITaskProject> findByUserIdAndRoleAndTaskStatusIn(@Param("userId") Long userId, @Param("valid") Boolean valid, 
                @Param("status") List<ProjectTaskStatus> status, @Param("role") ProjectTaskUserRole role, Pageable pageable);

    List<ProjectTask> findByProcessIdAndValid(Long projectId, Boolean valid);

    List<ProjectTask> findByProcessIdAndValidIsTrueOrderByParentIdAsc(Long projectId);

    Page<ProjectTask> findByValidIsTrueAndProcessIdNotIn(Long[] ids, Pageable pageable);

    ProjectTask findFirstByIdInAndValidIsTrueOrderByIdDesc(List<Long> ids);

    ProjectTask findFirstByOrderByLogigramPosXAsc();

    List<ProjectTask> findByIdInAndProcessId(List<Long> childrenIds, Long processId);
}
