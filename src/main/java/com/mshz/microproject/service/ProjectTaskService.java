package com.mshz.microproject.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.microproject.domain.ProjectEdgeInfo;
import com.mshz.microproject.domain.ProjectStartableTask;
import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.ProjectTaskItem;
import com.mshz.microproject.domain.ProjectTaskPauseHistory;
import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import com.mshz.microproject.domain.enumeration.ProjectTaskUserRole;
import com.mshz.microproject.domain.projection.ChronoUtil;
import com.mshz.microproject.domain.projection.ITaskProject;
import com.mshz.microproject.domain.projection.ReactFrappeGanttUtil;
import com.mshz.microproject.repository.ProjectEdgeInfoRepository;
import com.mshz.microproject.repository.ProjectStartableTaskRepository;
import com.mshz.microproject.repository.ProjectTaskFileRepository;
import com.mshz.microproject.repository.ProjectTaskItemRepository;
import com.mshz.microproject.repository.ProjectTaskRepository;
import com.mshz.microproject.repository.ProjectTaskSubmissionRepository;
import com.mshz.microproject.repository.ProjectTaskUserRepository;
import com.mshz.microproject.repository.ProjectTaskValidationControlRepository;
import com.mshz.microproject.service.async.AsyncUtilsService;
import com.mshz.microproject.webflux.ProjectNotifService;

/**
 * Service Implementation for managing {@link ProjectTask}.
 */
@Service
@Transactional
public class ProjectTaskService {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskService.class);

    private final ProjectTaskRepository taskRepository;

    private final AsyncUtilsService asyncUtilsService;
    private final ProjectEdgeInfoRepository edgeInfoRepository;
    private ProjectTaskUserRepository taskUserRepository;
    private ProjectTaskFileRepository taskFileRepository;
    private ProjectTaskItemRepository taskItemRepository;
    private final ProjectTaskValidationControlRepository validationControlRepository;
    private final ProjectTaskSubmissionRepository submissionRepository;
    private final ProjectNotifService projectNotifService;
    private final ProjectStartableTaskRepository projectStartableTaskRepository;
    private final ProjectTaskPauseHistoryService pauseHistoryService;

    public ProjectTaskService(ProjectTaskRepository taskRepository,
    AsyncUtilsService asyncUtilsService,
    ProjectEdgeInfoRepository edgeInfoRepository,
    ProjectTaskUserRepository taskUserRepository,
    ProjectTaskFileRepository taskFileRepository,
    ProjectTaskItemRepository taskItemRepository,
    ProjectTaskValidationControlRepository validationControlRepository,
    ProjectTaskSubmissionRepository submissionRepository,
    ProjectNotifService projectNotifService,
    ProjectStartableTaskRepository projectStartableTaskRepository,
    ProjectTaskPauseHistoryService pauseHistoryService) {
        this.taskRepository = taskRepository;
        this.asyncUtilsService = asyncUtilsService;
        this.edgeInfoRepository = edgeInfoRepository;
        this.taskUserRepository = taskUserRepository;
        this.taskFileRepository = taskFileRepository;
        this.taskItemRepository = taskItemRepository;
        this.validationControlRepository = validationControlRepository;
        this.submissionRepository = submissionRepository;
        this.projectNotifService = projectNotifService;
        this.projectStartableTaskRepository = projectStartableTaskRepository;
        this.pauseHistoryService = pauseHistoryService;
    }

    /**
     * Save a projectTask.
     *
     * @param projectTask the entity to save.
     * @return the persisted entity.
     */
    public ProjectTask save(ProjectTask projectTask) {
        log.debug("Request to save ProjectTask : {}", projectTask);
        if(projectTask != null){
            if(projectTask.getCreatedAt() == null)
                projectTask.setCreatedAt(Instant.now());
            if(projectTask.getPonderation() == null || projectTask.getPonderation().intValue() == 0)
                projectTask.setPonderation(Integer.valueOf(1));
            else
                projectTask.setPonderation(Integer.valueOf(Math.abs(projectTask.getPonderation().intValue())));

            if(projectTask.getId() == null){
                projectTask.setChecked(Boolean.TRUE);
                projectTask = setDefaultLogigramCords(projectTask);
            }
        }
        ProjectTask saved = taskRepository.save(projectTask);
        if(isPlayableNow(saved))
            saved = startTask(saved, false);
        return saved;
    }

    /**
     * Get all the projectTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTask> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTasks");
        Page<ProjectTask> result = taskRepository.findAll(pageable);
        return new PageImpl<>(sortTasks(result.getContent()), result.getPageable(), result.getTotalElements());
    }


    /**
     * Get one projectTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectTask> findOne(Long id) {
        log.debug("Request to get ProjectTask : {}", id);
        return taskRepository.findById(id);
    }


    /**
     * Delete the task by id.
     *
     * @param id the id of the entity.a
     */
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        if(id!= null){
            taskRepository.unLinkStartupAssociationsByTaskId(id);
            taskRepository.unLinkParentIdByTaskId(id);
            taskUserRepository.deleteByTaskId(id);
            taskFileRepository.deleteByTaskId(id);
            taskItemRepository.deleteByTaskId(id);
            validationControlRepository.deletByTaskId(id);
            submissionRepository.deleteByTaskId(id);
            edgeInfoRepository.deleteBySourceOrTarget(id.toString(), id.toString());
        }
        taskRepository.deleteById(id);
    }

    public List<ProjectTask> findByProcessIdOrderByIdAsc(Long id) {
        return sortTasks(taskRepository.findByProcessId(id));
    }
    
    public ProjectTask copy(ProjectTask t, Long newProcessId) {
    	if(t == null)
    		return null;
            ProjectTask task = new ProjectTask();
    	task.setDescription(t.getDescription());
    	task.setGroupId(t.getGroupId());
    	task.setLogigramPosX(t.getLogigramPosX());
    	task.setLogigramPosY(t.getLogigramPosY());
        task.setManualMode(t.isManualMode());
    	task.setName(t.getName());
    	task.setNbDays(t.getNbDays());
    	task.setNbHours(t.getNbHours());
    	task.setNbMinuites(t.getNbMinuites());
    	task.setNbMonths(t.getNbMonths());
    	task.setNbPause(t.getNbPause());
    	task.setNbYears(t.getNbYears());
    	task.setPriorityLevel(t.getPriorityLevel());
    	task.setStartWithProcess(t.isStartWithProcess());
    	task.setValid(true);
    	task.setFinishAt(null);
    	task.setPauseAt(null);
    	task.setProcessId(newProcessId);
    	task.setStartAt(null);
    	task.setStatus(ProjectTaskStatus.VALID);
    	task.setTaskModelId(t.getId());
        task.setRiskId(t.getRiskId());
        task.setSheduledStartAt(t.getSheduledStartAt());
        task.setSheduledStartHour(t.getSheduledStartHour());
        task.setSheduledStartMinute(t.getSheduledStartMinute());
        task.setPonderation(t.getPonderation());
    	return taskRepository.saveAndFlush(task);
    }
    
    public ProjectTask copyParentAndStartup(ProjectTask task) {
    	if(task != null && task.getTaskModelId() != null) {
    		ProjectTask model = taskRepository.findById(task.getTaskModelId()).orElse(null);
        	if(model != null && model.getParentId() != null) {
        		ProjectTask parent = taskRepository.findByTaskModelIdAndProcessId(model.getParentId(), task.getProcessId()).orElse(null);
        		if(parent != null) 
        			task.setParentId(parent.getId());
        		else
        			task.setParentId(null);
        	}
        	if(model.getStartupTaskId() != null) {
        		ProjectTask startupTask = taskRepository.findByTaskModelIdAndProcessId(model.getStartupTaskId(), task.getProcessId()).orElse(null);
        		task.setStartupTaskId(startupTask.getId());
        	}
        	return taskRepository.saveAndFlush(task);
    	}
        return task;
    }
    

    // set task to valid status
    public List<ProjectTask> resetTask(ProjectTask task) {
    	List<ProjectTask>tasksStatusChanged = new ArrayList<ProjectTask>();
    	task.setFinishAt(null);
    	task.setManualMode(false);
    	task.setNbPause(0);
    	task.setStartAt(null);
    	task.setStatus(ProjectTaskStatus.VALID);
    	task.setValid(true);
        task.finishAt(null);
    	task = taskRepository.saveAndFlush(task);
    	if(task != null){
            projectNotifService.sendTaskSatatusChangeNote(task);
    		tasksStatusChanged.add(task);
            taskItemRepository.updateCheckedByTaskId(task.getId(), Boolean.FALSE);
            pauseHistoryService.deleteByTaskId(task.getId());
        }
    	return sortTasks(tasksStatusChanged) ;
    }
    
    public ProjectTask startTask(ProjectTask task, boolean cheickIsNotCanceled) {
    	if(task != null && task.getStatus() != ProjectTaskStatus.STARTED && (!cheickIsNotCanceled || task.getStatus() != ProjectTaskStatus.CANCELED)) {
            ProjectTaskStatus oldStatus = task.getStatus();
    		task.setValid(true);
    		task.setStartAt(new Date().toInstant());
            task.setPauseAt(null);
            task.setManualMode(false);
            task.setCurrentPauseHistoryId(null);
    		task.setStatus(ProjectTaskStatus.STARTED);
            task.setFinishAt(null);
    		taskRepository.save(task);
            if(task != null){
                if(oldStatus != ProjectTaskStatus.ON_PAUSE)
                    taskItemRepository.updateCheckedByTaskId(task.getId(), Boolean.FALSE);
                asyncUtilsService.startStratupTasks(task);
                projectNotifService.sendTaskSatatusChangeNote(task);
                pauseHistoryService.deleteByTaskId(task.getId());
            }
    	}
    	return task;
    }

    public ProjectTask executeTask(ProjectTask task) {
    	if(task != null && task.getStatus() != ProjectTaskStatus.EXECUTED) {
            List<ProjectTaskUserRole> roles = new ArrayList<>();
            roles.add(ProjectTaskUserRole.SUBMITOR);
            roles.add(ProjectTaskUserRole.VALIDATOR);
            if(!taskHasUsersWithRoles(task.getId(),roles))
                return finishTask(task);
            task.setValid(true);
            if(task.getStartAt() == null)
    		    task.setStartAt(new Date().toInstant());
            task.setPauseAt(null);
            task.setManualMode(false);
    		task.setStatus(ProjectTaskStatus.EXECUTED);
            task.setFinishAt(null);
    		taskRepository.save(task);
            projectNotifService.sendTaskSatatusChangeNote(task);
    	}
    	return task;
    } 
    public ProjectTask submitTask(ProjectTask task) {
    	if(task != null && task.getStatus() != ProjectTaskStatus.SUBMITTED) {
            List<ProjectTaskUserRole> roles = new ArrayList<>();
            roles.add(ProjectTaskUserRole.VALIDATOR);
            if(!taskHasUsersWithRoles(task.getId(),roles))
                return finishTask(task);
            task.setValid(true);
            if(task.getStartAt() == null)
    		    task.setStartAt(new Date().toInstant());
            task.setPauseAt(null);
            task.setManualMode(false);
    		task.setStatus(ProjectTaskStatus.SUBMITTED);
            task.setFinishAt(null);
    		taskRepository.save(task);
            projectNotifService.sendTaskSatatusChangeNote(task);
    	}
    	return task;
    }
    
    public void runTaskChildren(ProjectTask task){
    	if(task != null && task.getId() != null) {
            List<ProjectEdgeInfo> edgeInfos = edgeInfoRepository.findBySource(task.getId().toString());
            if(edgeInfos != null){
                /* chidlren task is linkend to parent with target edgeinfo property
                /* find chlidren task ids
                */
                List<Long> childrenIds = edgeInfos.stream()
                                                  .filter(ei -> ei.getTarget() != null && ei.getTarget().toLowerCase().indexOf("cond") == -1)
                                                  .map(ei -> Long.valueOf(ei.getTarget())).collect(Collectors.toList());
                if(childrenIds != null){
                    List<ProjectTask>children = taskRepository.findByIdInAndProcessId(childrenIds, task.getProcessId());
                    for (ProjectTask child : children) {
                       startTask(child, true);
                    }
                }
            }
    	}
    }
    
    public ProjectTask finishTask(ProjectTask task){
    	task.setFinishAt(new Date().toInstant());
    	task.setValid(true);
    	task.setStatus(ProjectTaskStatus.COMPLETED);
    	task = taskRepository.saveAndFlush(task);
    	if(task != null){
            projectNotifService.sendTaskSatatusChangeNote(task);
    		runTaskChildren(task);
        }
    	return task;
    }
    
    public ProjectTask cancelTask(ProjectTask task){
    	task.setStatus(ProjectTaskStatus.CANCELED);
    	task.setValid(true);
        task.setFinishAt(null);
    	task = taskRepository.saveAndFlush(task);
    	if(task != null){
            projectNotifService.sendTaskSatatusChangeNote(task);
    		runTaskChildren(task);
        }
    	return task;
    } 


    public ProjectTask playTask(ProjectTask task){
        if(task != null && (task.getStatus() == ProjectTaskStatus.SUBMITTED || task.getStatus() == ProjectTaskStatus.ON_PAUSE)){
            pauseHistoryService.updateRestartedAt(task.getCurrentPauseHistoryId());
            // task = addPauseDurationOnTaskDelay(task);
            task.setPauseAt(null);
            task.setCurrentPauseHistoryId(null);
            task.setNbPause((task.getNbPause() == null) ? 1: task.getNbPause() + 1);
            task.setValid(true);
            task.setManualMode(true);
            task.setFinishAt(null);
            if(task.getStartAt() == null)
                task.setStartAt(new Date().toInstant());
            task.setStatus(ProjectTaskStatus.STARTED);

            task = taskRepository.saveAndFlush(task);
            if(task != null){
                projectNotifService.sendTaskSatatusChangeNote(task);
                asyncUtilsService.startStratupTasks(task);
            }
        }
        return task;
    }
    
    // is turn task to automatic mode
    public ProjectTask playByInstance(ProjectTask task){
        if(task != null && (task.getStatus() == ProjectTaskStatus.SUBMITTED || task.getStatus() == ProjectTaskStatus.ON_PAUSE)){
            // task = addPauseDurationOnTaskDelay(task);
            pauseHistoryService.updateRestartedAt(task.getCurrentPauseHistoryId());
            task.setPauseAt(null);
            task.setFinishAt(null);
            task.setCurrentPauseHistoryId(null);
            task.setNbPause((task.getNbPause() == null) ? 1: task.getNbPause() + 1);
            task.setValid(true);
            task.setManualMode(false);
            if(task.getStartAt() == null)
                task.setStartAt(new Date().toInstant());
            task.setStatus(ProjectTaskStatus.STARTED);
            taskRepository.saveAndFlush(task);
            if(task != null){
                projectNotifService.sendTaskSatatusChangeNote(task);
                asyncUtilsService.startStratupTasks(task);
            }
        }
        return task;
    }

    public ProjectTask pauseTask(ProjectTask task, boolean onSubmiteAction){
        if(task != null && (task.getStatus() == ProjectTaskStatus.STARTED) && task.isValid()){
        	Instant pauseAt = Instant.now();
        	ProjectTaskPauseHistory pauseHistory = pauseHistoryService.trackTaskPause(task, pauseAt);
        	if(pauseHistory != null)
        		task.setCurrentPauseHistoryId(pauseHistory.getId());
            task.setPauseAt(pauseAt);
            task.setManualMode(true);
            task.setFinishAt(null);
            if(task.getStartAt() == null)
                task.setStartAt(new Date().toInstant());
            if(onSubmiteAction){
                List<ProjectTaskUserRole> roles = new ArrayList<>();
                roles.add(ProjectTaskUserRole.SUBMITOR);
                roles.add(ProjectTaskUserRole.VALIDATOR);
                if(!taskHasUsersWithRoles(task.getId(),roles))
                    return finishTask(task);
                else
                    task.setStatus(ProjectTaskStatus.SUBMITTED);
            }else{
                task.setStatus(ProjectTaskStatus.ON_PAUSE);
            }
            task = taskRepository.saveAndFlush(task);
        }
        return task;
    }

    public ProjectTask unUsedAddPauseDurationOnTaskDelay(ProjectTask task){
        if(task != null && task.getStartAt() != null && task.getPauseAt() != null){
            return generateTaskDurationFromTwoDates(task, task.getPauseAt(), Instant.now());
        }
        return task;
    }

    public ProjectTask submiTask(ProjectTask task){
        return pauseTask(task, true);
    }

	public Page<ITaskProject> findByEmployeeIdAndProjectTaskStatus(Long id, ProjectTaskStatus status, Pageable pageable) {
		Page<ITaskProject>  result = taskRepository.findByEmployeeIdAndStatus(id, Boolean.TRUE,status, pageable);
        return new PageImpl<>(sortTaskProjects(result.getContent()), result.getPageable(), result.getTotalElements());
	}


	public Page<ITaskProject>  findCheckableTasksByUserIdAndProjectTaskStatus(Long id,ProjectTaskStatus status,Pageable pageable) {
        List<ProjectTaskStatus> statusList = new ArrayList<>();
        if(status != null){
            statusList.add(status);
        }else{
            // use default status
            statusList.add(ProjectTaskStatus.STARTED);
            statusList.add(ProjectTaskStatus.EXECUTED);
        }
		Page<ITaskProject> result =  taskRepository.getTaskCheckListByUserIdAndAndValidAndProjectTaskStatus(id, Boolean.TRUE,statusList, pageable);
        return new PageImpl<>(sortTaskProjects(result.getContent()), result.getPageable(), result.getTotalElements());
	}

	public List<ProjectTask> findEmployeeTasksBetween(Long id, Instant startAt, Instant endAt) {
	    List<ProjectTask>  result = taskRepository.findByEmployeeTasksByInstancesCreatedAtBetween(id,startAt, endAt, Boolean.TRUE);
        return sortTasks(result);
	}

	public Page<ITaskProject>  findByEmployeeId(Long id, Pageable pageable) {
		Page<ITaskProject> result = taskRepository.findByEmployeeId(id, Boolean.TRUE, pageable);
        return new PageImpl<>(sortTaskProjects(result.getContent()), result.getPageable(), result.getTotalElements());
	}
	
	public Page<ProjectTask>findTasksToStartupAssociable(Long parentTaskId, Long taskProcessId, Pageable page){
		Page<ProjectTask> result = taskRepository.getTasksToStartupAssociable(parentTaskId, taskProcessId, page);
        return new PageImpl<>(sortTasks(result.getContent()), result.getPageable(), result.getTotalElements());
	}


    public List<ProjectTask>getStratupTasks(Long taskId){
        return sortTasks(taskRepository.findByStartupTaskId(taskId));
    }

    public List<ProjectTask>getChildrenTasks(Long taskParentId){
        return sortTasks(taskRepository.findByParentId(taskParentId));
    }

    public List<ProjectTask> getByProcessIdAndStatus(Long processId, ProjectTaskStatus status){
        return sortTasks(taskRepository.findByProcessIdAndStatus(processId, status));
    }

    public void updateManualModeByProcessId(Long processId, Boolean manualMode){
        taskRepository.updateManualModeByProcessId(manualMode, processId);
    }

    public void deleteByProjectId(Long id) {
        taskRepository.deleteByProjectId(id);
    }
    

    public boolean isPlayableNow(ProjectTask task){
        ZonedDateTime zdt = ZonedDateTime.now();
        return (task != null 
               && task.getStatus() == ProjectTaskStatus.VALID && task.isValid()
               && (task.isStartWithProcess()  || (
                    task.getSheduledStartAt() != null 
                    && (task.getSheduledStartAt().isBefore(zdt.toLocalDate()) || task.getSheduledStartAt().isEqual(zdt.toLocalDate()))
                    && task.getSheduledStartHour() == Integer.valueOf(zdt.getHour())
                    && task.getSheduledStartMinute() == Integer.valueOf(zdt.getMinute()))
               )
        );
    }

    public Instant getTaskPreviewFinishDate(ProjectTask task, Instant defaultStardDate){
        if(task != null && task.isValid() == Boolean.TRUE){
            Instant startDate = task.getStartAt() != null ? task.getStartAt() : defaultStardDate;
            long minutes = task.getNbMinuites() != null ? task.getNbMinuites().longValue() : 0;
            long hours = task.getNbHours() != null ? task.getNbHours().longValue() : 0;
            long days = task.getNbDays() != null ? task.getNbDays().longValue() : 0;
            long months = task.getNbMonths() != null ? task.getNbMonths().longValue() : 0;
            long years = task.getNbYears() != null ? task.getNbYears().longValue() : 0;
            if(startDate != null){
                ZonedDateTime zdt = ZonedDateTime.ofInstant(startDate, ZoneId.systemDefault())
                                    .plusYears(years)
                                    .plusMonths(months)
                                    .plusDays(days)
                                    .plusHours(hours)
                                    .plusMinutes(minutes);
               return zdt.toInstant();
            }
        }
        return null;
    }


    public Instant getLastPreviwFinishedByProject(Long projectId, Instant defaultStartAt){
        List<ProjectTask> tasks = taskRepository.findByProcessIdAndStatusNotAndValid(projectId, ProjectTaskStatus.CANCELED, Boolean.TRUE);
        Instant finishAt = null;
        for (ProjectTask task : tasks) {
            Instant taskfinishAt = getTaskPreviewFinishDate(task, defaultStartAt);
            if(finishAt == null){
                finishAt = taskfinishAt;
            }else{
                if(taskfinishAt != null && finishAt.isBefore(taskfinishAt)){
                    finishAt = taskfinishAt;
                }
            }
        }
        return finishAt;
    }

    public ProjectTask getFirstStartedTaskByProject(Long projectId) {
        List<ProjectTaskStatus> status = new ArrayList<>();
        status.add(ProjectTaskStatus.VALID);
        status.add(ProjectTaskStatus.CANCELED);
        ProjectTask firstStartedTask = taskRepository
                    .findFirstByProcessIdAndValidAndStatusNotInOrderByStartAtAsc(projectId, Boolean.TRUE,status);
        return firstStartedTask;
    }

    public Page<ITaskProject> findByUserIdAndRoleAndTaskStatus(Long userId, ProjectTaskStatus status,
            ProjectTaskUserRole role, Pageable pageable) {
            Page<ITaskProject> result = taskRepository.findByUserIdAndRoleAndTaskStatus(userId, Boolean.TRUE,status, role, pageable);
            return new PageImpl<>(sortTaskProjects(result.getContent()), result.getPageable(), result.getTotalElements());
    }
    
    public Page<ITaskProject> findByUserIdAndRoleAndTaskStatusIn(Long userId, List<ProjectTaskStatus> status,
        ProjectTaskUserRole role, Pageable pageable) {
        Page<ITaskProject> result = taskRepository.findByUserIdAndRoleAndTaskStatusIn(userId, Boolean.TRUE,status, role, pageable);
        return new PageImpl<>(sortTaskProjects(result.getContent()), result.getPageable(), result.getTotalElements());
    }
    

    public boolean taskHasUsersWithRoles(Long taskId, List<ProjectTaskUserRole> roles){
        if(taskId != null && roles != null){
            return !taskUserRepository.findByTaskIdAndRoleIn(taskId, roles, PageRequest.of(0, 1)).isEmpty();
        }
        return false;
    }

	/*
	 * public ChronoUtil calculeTaskChrono(ProjectTask task){ if(task != null &&
	 * task.getStartAt() != null){ // calcul max finish date ZonedDateTime zdt =
	 * ZonedDateTime.ofInstant(task.getStartAt(), ZoneId.systemDefault())
	 * .plusYears(task.getNbYears() != null ? task.getNbYears().longValue() : 0)
	 * .plusMonths(task.getNbMonths() != null ? task.getNbMonths().longValue() : 0)
	 * .plusDays(task.getNbDays() != null ? task.getNbDays().longValue() : 0)
	 * .plusHours(task.getNbHours() != null ? task.getNbHours().longValue() : 0)
	 * .plusMinutes(task.getNbMinuites() != null ? task.getNbMinuites().longValue()
	 * : 0);
	 * 
	 * Instant previewFinishDate = zdt.toInstant();
	 * 
	 * boolean execeed = (task.getStatus() == ProjectTaskStatus.COMPLETED &&
	 * task.getFinishAt() != null) ? previewFinishDate.isBefore(task.getFinishAt())
	 * : previewFinishDate.isBefore(Instant.now());
	 * 
	 * return new ChronoUtil(task.getStartAt(), task.getPauseAt(),
	 * previewFinishDate, task.getFinishAt(), task.getStatus(), execeed);
	 * 
	 * } return null; }
	 */

	
	  public ChronoUtil getTaskChronoUtil(Long taskId) { 
		  ProjectTask task = taskRepository.findById(taskId).orElse(null); 
		  return pauseHistoryService.getTaskChrono(task);
	  }
	 

    public List<ProjectTask> findByProjectIdAndValid(Long projectId, Boolean true1) {
        return sortTasks(taskRepository.findByProcessIdAndValid(projectId, Boolean.TRUE));
    }

    public List<ProjectTask> findByProjectIdAndValidIsTrueOrderByParentIdAsc(Long projectId){
       return sortTasks(taskRepository.findByProcessIdAndValidIsTrueOrderByParentIdAsc(projectId));
    }

    public Boolean allRequiredTaskItemsIsChecked(Long taskId) {
        ProjectTaskItem unchecked = taskItemRepository.
            findFirstByTaskIdAndRequiredAndChecked(taskId, Boolean.TRUE, Boolean.FALSE);
        return unchecked == null;
    }

    public void startStartableTasks(Long triggerTaskId, ProjectStartableTaskCond startCond){
        asyncUtilsService.startStartableTasks(triggerTaskId, startCond);
    }

    public Page<ProjectTask> getByProjectIdNotIn(Long[] ids, Pageable pageable) {
        Page<ProjectTask> result = taskRepository.findByValidIsTrueAndProcessIdNotIn(ids, pageable);
        return new PageImpl<>(sortTasks(result.getContent()), result.getPageable(), result.getTotalElements());
    }

    @Async
    public void startByTaskIdAndConds(Long taskId, List<String> conds) {
        if(taskId != null && conds != null){
           List<ProjectStartableTaskCond> enumConds = new ArrayList<>();
           for (String cond : conds) {
               try { enumConds.add(ProjectStartableTaskCond.valueOf(cond)); } catch (Exception e) {}
           }
           List<ProjectStartableTask> startableTasks =
            projectStartableTaskRepository.findByTriggerTaskIdAndStartCondIn(taskId, enumConds);
            for (ProjectStartableTask item : startableTasks) {
                ProjectTask task =  taskRepository.findById(item.getStartableTaskId()).orElse(null);
                if(task != null){
                    log.info("link task starting {} with cond {}", task.getName(), item.getStartCond());
                    startTask(task, item.getStartCond() != ProjectStartableTaskCond.LOOPBACK);
                }
            }
        }
    }

    public List<ProjectTask> findByProjectIdAndValidIsTrueOrderByIdAsc(Long projectId) {
        return sortTasks(taskRepository.findByProcessIdAndValidIsTrueOrderByParentIdAsc(projectId));
    }

    public ProjectTask updateSheduledStartDate(Long taskId, Instant startDate, Instant endDate){
        ProjectTask task = taskRepository.findById(taskId).orElse(null);
        if(task != null && task.getStatus() == ProjectTaskStatus.VALID){
            if(startDate != null){
                task.setSheduledStartAt(LocalDate.ofInstant(startDate, ZoneId.systemDefault()));
                task.setSheduledStartHour(Integer.valueOf(startDate.atZone(ZoneId.systemDefault()).getHour()));
                task.setSheduledStartMinute(Integer.valueOf(startDate.atZone(ZoneId.systemDefault()).getMinute()));
                if(endDate != null)
                    task = generateTaskDurationFromTwoDates(task, startDate, endDate);
            }else{
                task.setSheduledStartAt(null);
                task.setSheduledStartHour(null);
                task.setSheduledStartMinute(null);
            }
            task = taskRepository.save(task);
            if(isPlayableNow(task))
                task = startTask(task, true);
        }
        return task;
    }


    public ProjectTask generateTaskDurationFromTwoDates(ProjectTask task, Instant startDate, Instant endDate){
        if(task != null && startDate != null && endDate != null){
            Period p = Period.between(startDate.atZone(ZoneId.systemDefault()).toLocalDate(),
                                      endDate.atZone(ZoneId.systemDefault()).toLocalDate());
            long diffSeconds = Duration.between(startDate, endDate).getSeconds();
            if(!p.isNegative()){
                 // year
                if(diffSeconds > 0){
                    int nbYears = p.getYears();
                    log.debug("nbYears  {}", nbYears);
                    if(nbYears > 0){
                        task.setNbYears(task.getNbYears() != null ? (task.getNbYears()  + nbYears) : nbYears);
                        diffSeconds = diffSeconds - (365 * nbYears * 24 * 60 * 60);
                    }
                }
    
                // months
                if(diffSeconds > 0){
                    int months = p.getMonths();
                    log.debug("months  {}", months);
                    if(months > 0){
                        task.setNbMonths(task.getNbMonths() != null ? (task.getNbMonths() + months) : months);
                        diffSeconds = diffSeconds - (months * 30 * 24 * 60 *60);
                    }
                }
    
                // days
                if(diffSeconds > 0){
                    int days = p.getDays();
                    log.debug("days  {}", days);
                    if(days > 0){
                        task.setNbDays(task.getNbDays() != null ? (task.getNbDays() + days) : days);
                        diffSeconds = diffSeconds - (days * 24 * 60 *60);
                    }
                }
    
            }
    
            /* hours */
            if(diffSeconds > 0){
                long hour = diffSeconds / 3600;
                if(hour > 0){
                    log.debug("hours  {}", hour);
                    task.setNbHours(task.getNbHours() != null ? (task.getNbHours() + Math.toIntExact(hour)) : Math.toIntExact(hour));
                    diffSeconds = diffSeconds - (hour * 60 *60);
                }
            }
    
            /* minmutes */
            if(diffSeconds > 0){
                long minutes = diffSeconds % 3600 / 60;
                task.setNbMinuites(task.getNbMinuites() != null ? (task.getNbMinuites() + Math.toIntExact(minutes)) : Math.toIntExact(minutes));
            }
        }
        return task;
    }

    public ProjectTask getLastPreviewTaskByStrSourceEdges(List<String> sourceEdges){
        if(sourceEdges != null && !sourceEdges.isEmpty()){
            List<Long> ids = new ArrayList<>();
            for (String source : sourceEdges) {
                try {
                    Long id = Long.valueOf(source);
                    ids.add(id);
                } catch (Exception e) {
                    //TODO: handle exception
                }
            }
            return taskRepository.findFirstByIdInAndValidIsTrueOrderByIdDesc(ids);
        }
        return null;
    }


    public ReactFrappeGanttUtil getGanntData(Long taskId) {
        ProjectTask task = taskRepository.findById(taskId).orElse(null);
        return getGanntData(task);
    }

    public ReactFrappeGanttUtil getGanntData(ProjectTask task){
        if(task != null){
            ChronoUtil taskChronoUtil = pauseHistoryService.getTaskChrono(task);
            ReactFrappeGanttUtil taskGanttUtil = new ReactFrappeGanttUtil(task, taskChronoUtil);
            List<ProjectEdgeInfo> edgeInfos = edgeInfoRepository
                .findBySourceOrTarget(task.getId().toString(), task.getId().toString());
            if(edgeInfos != null && !edgeInfos.isEmpty()){
                taskGanttUtil.setEditable(false);
                List<Long> depends = edgeInfos.stream()
                    .filter(e -> e.getSource() != null && e.getTarget() != null 
                        && e.getTarget().equals(task.getId().toString()))
                    .map(e -> Long.valueOf(e.getSource())).collect(Collectors.toList());
                taskGanttUtil.setDepends(depends);
            }else{
                taskGanttUtil.setEditable(true);
            }

            return taskGanttUtil;
        }
        return null;
    }

    private ProjectTask setDefaultLogigramCords(ProjectTask task){
        if(task != null){
            ProjectTask refTask = taskRepository.findFirstByOrderByLogigramPosXAsc();
            double x = 100;
            double y = 100;
            if(refTask != null && refTask.getLogigramPosX() != null && refTask.getLogigramPosY() != null){
                 x = refTask.getLogigramPosX().doubleValue() + 8;
                 y = refTask.getLogigramPosY().doubleValue()+8;
            }
            task.setLogigramPosX(Double.valueOf(x));
            task.setLogigramPosY(Double.valueOf(y));
        }

        return task;
    }

    /** 
     * ===================================
     * ======= BEGIN SOTRING UTILS METHODS =====
     * ===================================
     */

    public List<ProjectTask> sortTasksyIds(List<Long> ids){
        List<ProjectTask> tasks = new ArrayList<>();
        if(ids != null && !ids.isEmpty()){
            tasks = taskRepository.findByIdIn(ids);
            return sortTasks(tasks);
        }
        return tasks;
    }

    public List<ProjectTask> sortTasks(List<ProjectTask> tasks){
        if(tasks != null && tasks.size() >=2){

            return tasks.stream()
                // sorting by ids
                .sorted((a,b) -> sortById(a,b))
                // sorting by started tasks
                .sorted((a,b) -> sortByStatus(a,b))
                // sorting by chrono
                .sorted((a,b) -> sortByChrono(a,b))
                .collect(Collectors.toList());
        }

        return tasks;
    }

    public List<ITaskProject> sortTaskProjects(List<ITaskProject> elements) {
        if(elements != null && !elements.isEmpty()){
            /**
             * a will placed before b if sorted comparator is negative
             * b will placed before a if sorted comparator is positive
             * no sorting if sorted comparator is zero
             */
            return elements.stream()
                // sorting by ids
                .sorted((a,b) -> sortById(a.getTask(),b.getTask()))
                // sorting by started tasks
                .sorted((a,b) -> sortByStatus(a.getTask(),b.getTask()))
                // sorting by chrono
                .sorted((a,b) -> sortByChrono(a.getTask(),b.getTask()))
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private int sortById(ProjectTask ta, ProjectTask tb){
        if(ta != null && tb != null){
            try {
                if(ta.getId() == null && tb.getId() != null)
                    return 1; // tb avant ta
                if(ta.getId() != null && tb.getId() == null)
                    return -1; // ta avant tb
                if(ta.getId() != null && tb.getId() != null)
                    return tb.getId().intValue() - ta.getId().intValue();
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return 0;
    }

    private int sortByStatus(ProjectTask ta, ProjectTask tb){
        // if task is started will render first
        if(ta != null && tb != null){
            try {
                List<ProjectTaskStatus> startedStatus = Arrays
                    .asList(ProjectTaskStatus.EXECUTED, ProjectTaskStatus.STARTED, ProjectTaskStatus.SUBMITTED);
                
                boolean taIsStarted = ta.getStatus() != null && startedStatus.contains(ta.getStatus());
                boolean tbIsStarted = tb.getStatus() != null && startedStatus.contains(tb.getStatus());

                if(!taIsStarted && tbIsStarted)
                    return 1; // tb avant ta
                if(taIsStarted && !tbIsStarted)
                    return -1; // ta avant tb
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return 0;
    }

    private int sortByChrono(ProjectTask ta, ProjectTask tb){
        if(ta != null && tb != null){
            try {
                ChronoUtil taChrono = pauseHistoryService.getTaskChrono(ta);
                ChronoUtil tbChrono = pauseHistoryService.getTaskChrono(tb);

                if(taChrono == null && tbChrono == null)
                    return 0;

                if(taChrono == null && tbChrono != null)
                    return 1; // tb avant ta

                if(taChrono != null && tbChrono == null)
                    return -1; // ta avant tb
                    
                if(taChrono.getStatus() != ProjectTaskStatus.CANCELED && tbChrono.getStatus() == ProjectTaskStatus.CANCELED)
                    return -1; // tb avant ta

                if(taChrono.getStatus() == ProjectTaskStatus.CANCELED && tbChrono.getStatus() != ProjectTaskStatus.CANCELED)
                    return 1; // ta avant tb

                if(taChrono.getFinishDate() == null && tbChrono.getFinishDate() != null)
                    return 1; // tb avant ta

                if(taChrono.getFinishDate() != null && tbChrono.getFinishDate() == null)
                    return -1; // ta avant tb
                    
                if(taChrono.getPausedDate() == null && tbChrono.getPausedDate() != null)
                    return -1; // tb avant ta

                if(taChrono.getPausedDate() != null && tbChrono.getPausedDate() == null)
                    return 1; // ta avant tb

                // sorting by excecced chrono
                if(taChrono.isExeceed() && !tbChrono.isExeceed())
                    return -1;
                if(!taChrono.isExeceed() && tbChrono.isExeceed())
                    return 1;
                if(taChrono.isExeceed() && tbChrono.isExeceed()){
                    Instant now = Instant.now();
                    Duration da = Duration.between(now, taChrono.getPrviewFinishDate());
                    Duration db = Duration.between(now, tbChrono.getPrviewFinishDate());
                    return da.compareTo(db); 
                }
                // end  sorting by excecced chrono

                if(taChrono.getPrviewFinishDate() != null && tbChrono.getPrviewFinishDate() != null )
                    return taChrono.getPrviewFinishDate().compareTo(tbChrono.getPrviewFinishDate());
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        return 0;
    }

    /** 
     * ==================================
     * ==== END SOTRING UTILS METHODS ===
     * ==================================
     */

}
