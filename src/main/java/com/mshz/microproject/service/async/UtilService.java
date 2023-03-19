package com.mshz.microproject.service.async;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.ProjectStartableTask;
import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.ProjectTaskFile;
import com.mshz.microproject.domain.ProjectTaskItem;
import com.mshz.microproject.domain.ProjectTaskPauseHistory;
import com.mshz.microproject.domain.ProjectTaskUser;
import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;
import com.mshz.microproject.domain.enumeration.ProjectTaskFileType;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import com.mshz.microproject.repository.ProjectRepository;
import com.mshz.microproject.repository.ProjectStartableTaskRepository;
import com.mshz.microproject.repository.ProjectTaskFileRepository;
import com.mshz.microproject.repository.ProjectTaskItemRepository;
import com.mshz.microproject.repository.ProjectTaskRepository;
import com.mshz.microproject.repository.ProjectTaskUserRepository;
import com.mshz.microproject.service.ProjectCalendarService;
import com.mshz.microproject.service.ProjectPublicHolidayService;
import com.mshz.microproject.service.ProjectTaskPauseHistoryService;
import com.mshz.microproject.webflux.ProjectNotifService;

@Service
public class UtilService {

    private final Logger log = LoggerFactory.getLogger(UtilService.class);

    private final ProjectTaskRepository taskRepository;

    private final ProjectRepository projectRepository;
    private final ProjectPublicHolidayService publicHolidayServie;
    private final ProjectCalendarService calenderService;
    private final ProjectTaskFileRepository taskFileRepository;
    private final ProjectTaskUserRepository taskUserRepository;
    private final ProjectTaskItemRepository taskItemRepository;
    private final ProjectNotifService projectNotifService;

    private final ProjectStartableTaskRepository startableTaskRepository;
    
    private final ProjectTaskPauseHistoryService pauseHistoryService;

    private final String percentKey = "percent";

    private final String pondKey = "ponderation";

    public UtilService(ProjectTaskRepository taskRepository, 
        ProjectRepository projectRepository,
        ProjectPublicHolidayService publicHolidayServie,
            ProjectCalendarService calenderService,
        ProjectTaskFileRepository taskFileRepository,
            ProjectTaskUserRepository taskUserRepository,
        ProjectTaskItemRepository taskItemRepository,
        ProjectNotifService projectNotifService,
        ProjectStartableTaskRepository startableTaskRepository,
        ProjectTaskPauseHistoryService pauseHistoryService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.publicHolidayServie = publicHolidayServie;
        this.calenderService = calenderService;
        this.taskFileRepository = taskFileRepository;
        this.taskUserRepository = taskUserRepository;
        this.taskItemRepository = taskItemRepository;
        this.projectNotifService = projectNotifService;
        this.startableTaskRepository = startableTaskRepository;
        this.pauseHistoryService = pauseHistoryService;
    }


    /**
     * auto start tasks
     */
    public void autoPauseTasks(){
       try {
            List<Project> instances = projectRepository.findByValid(Boolean.TRUE);
            if(instances != null && !instances.isEmpty()){
                List<Long> instancesIds = instances.stream().map(p -> p.getId()).collect(Collectors.toList());
                List<ProjectTask> tasks = taskRepository.findByStatusAndProcessIdInAndManualModeNot(ProjectTaskStatus.STARTED, instancesIds, true);
                for (ProjectTask task : tasks) {
                    if(publicHolidayServie.isPublicHoliday(new Date()) || !calenderService.isWorkingTime(new Date())){
                    	Instant pauseAt = Instant.now();
                    	ProjectTaskPauseHistory pauseHistory = pauseHistoryService.trackTaskPause(task, pauseAt);
                        task.setPauseAt(pauseAt);
                        task.setCurrentPauseHistoryId(pauseHistory.getId());
                        task.setStatus(ProjectTaskStatus.ON_PAUSE);
                        task.setFinishAt(null);
                        taskRepository.save(task);
                    }
                }
                tasks.clear();
                tasks = null;
                instancesIds.clear();
                instancesIds = null;
            }
            instances.clear();
            instances = null;
       } catch (Exception e) {
            e.printStackTrace();
       }
    }

    public void autoPlaySheduledTaks(){
        ZonedDateTime zdt = ZonedDateTime.now();
        log.debug("sheduled date {} {}:{}",zdt.format(DateTimeFormatter.ISO_DATE), zdt.getHour(), zdt.getMinute());
        List<ProjectTask> tasks = taskRepository
            .getShudledTasks(ProjectTaskStatus.VALID,zdt.toLocalDate(), zdt.getHour(), zdt.getMinute(),Boolean.TRUE);
        for (ProjectTask task : tasks) {
            log.debug("shedeledTask {}-{} ", task.getId(), task.getName());
            StartTask(task, true, true);
            reitinializeTaskCheickItems(task);
        }
        tasks.clear();
        tasks = null;
    }

    /**
     * auto plays tasks
     */
    public void  autoPlayTasks(){
        try {
            log.info("in auto play Task id {} and name {}");
            List<Project> instances = projectRepository.findByValid(Boolean.TRUE);
            if(instances != null && !instances.isEmpty()){
                List<Long> instancesIds = instances.stream().map(p -> p.getId()).collect(Collectors.toList());
                List<ProjectTask> tasks = taskRepository
                .getAllTaskToPlayAutomatically(ProjectTaskStatus.ON_PAUSE, instancesIds);
                for (ProjectTask task : tasks) {
                    StartTask(task, false, false);
                    reitinializeTaskCheickItems(task);
                }
                instancesIds.clear();
                instancesIds = null;
                tasks.clear();
                tasks = null;
            }
            instances.clear();
            instances = null;
        } catch (Exception e) {
             e.printStackTrace();
        }
     }

    /**
     * 
     * @param t
     */
    public void startStatupTask(ProjectTask t){
        try{
            if(t != null){
                List<ProjectTask> tasks = taskRepository.findByStartupTaskId(t.getId());
                log.debug("srtTaskfinded {}", tasks.size());
                if(tasks != null){
                    for (ProjectTask task : tasks) {
                        if(task.getSheduledStartAt() == null){
                            ProjectTask startedTTask = StartTask(task, false, true);
                            reitinializeTaskCheickItems(task);
                            if(startedTTask != null && startedTTask.getStatus() == ProjectTaskStatus.STARTED)
                                startStatupTask(startedTTask);
                        }
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            log.info("error {}", e.getMessage());
        }
    }

    public void reitinializeTaskCheickItems(ProjectTask task){
        if(task != null && task.getStatus() == ProjectTaskStatus.VALID){
            log.debug("checik cleaning {} {} ", task.getStatus(), task.getName());
            // cleaning task cheick validations
            taskItemRepository.updateCheckedByTaskId(task.getId(), Boolean.FALSE); 
        }
    }

    /**
     * 
     * @param task
     * @return
     */
    
    public ProjectTask StartTask(ProjectTask task, boolean ignoreCalander, boolean sendNotification){
            boolean isValidWorkingTime = ignoreCalander ? true : (!publicHolidayServie.isPublicHoliday(new Date()) && calenderService.isWorkingTime(new Date()));
            if(task != null && (task.getStatus() == ProjectTaskStatus.VALID || task.getStatus() == ProjectTaskStatus.ON_PAUSE) 
                && (task.isManualMode() == null || task.isManualMode().booleanValue() == false) && isValidWorkingTime) {
                if(task.getStatus() == ProjectTaskStatus.ON_PAUSE){
                    // addPauseDurationOnTaskDelay(task);
                    task.setNbPause((task.getNbPause() == null) ? 1: task.getNbPause() + 1);
                    pauseHistoryService.updateRestartedAt(task.getCurrentPauseHistoryId());
                }
                task.setValid(true);
                if(task.getStartAt() == null)
                    task.setStartAt(new Date().toInstant());
                task.setPauseAt(null);
                task.setCurrentPauseHistoryId(null);
                task.setStatus(ProjectTaskStatus.STARTED);
                task.setFinishAt(null);
                taskRepository.save(task);
                if(sendNotification)
                    projectNotifService.sendTaskSatatusChangeNote(task);
            }
        return task;
    }

    /**
     * 
     * @param tasks
     * @param taskFileType
     */
   public void copyTaskSFiles(List<ProjectTask> tasks, ProjectTaskFileType taskFileType) {
    for (ProjectTask task : tasks) {
         List<ProjectTaskFile> files = taskFileRepository.findByTaskIdAndType(task.getTaskModelId(), taskFileType);
         for (ProjectTaskFile file : files) {
            copyTaskFile(file, task);
         }
         
     }
  }

  /**
   * 
   * @param file
   * @param newTask
   */
  public void copyTaskFile(ProjectTaskFile file, ProjectTask newTask){
    if(file != null && newTask != null){
        ProjectTaskFile copy = new ProjectTaskFile();
        copy.setTaskId(newTask.getId());
        copy.setFileId(file.getFileId());
        copy.setFileName(file.getFileName());
        copy.setType(file.getType());
        taskFileRepository.save(copy);
    }
  }

   /**
    * 
    * @param tasks
    */
  public void copyTasksUsers(List<ProjectTask> tasks) {
        for (ProjectTask task : tasks) {
            if(task.getTaskModelId() != null) {
                    ProjectTask model = taskRepository.findById(task.getTaskModelId()).orElse(null);
                    if(model != null) {
                        List<ProjectTaskUser> tasksUsers = taskUserRepository.findByTaskId(model.getId());
                        for (ProjectTaskUser taskUser : tasksUsers) {
                            copyTaskUser(taskUser, task);
                        }
                    }
            }
       }
    }

    /**
     * 
     * @param taskUser
     * @param newTask
     * @return
     */
    public ProjectTaskUser copyTaskUser(ProjectTaskUser taskUser, ProjectTask newTask) {
    	ProjectTaskUser copy = new ProjectTaskUser();
    	copy.setUserId(taskUser.getUserId());
    	copy.setTaskId(newTask.getId());
    	copy.setRole(taskUser.getRole());
    	return taskUserRepository.save(copy);
    }

    public void copyTaskitemsFormTasks(List<ProjectTask> tasks){
        if(tasks != null){
            for (ProjectTask task : tasks) {
                if(task.getTaskModelId() != null){
                    copyTaskItems(task.getTaskModelId(), task.getId());
                }
            }
        }
    }

    public List<ProjectTaskItem> copyTaskItems(Long taskId, Long newTaskId){
        List<ProjectTaskItem> results = new ArrayList<>();
        if(taskId != null && newTaskId != null && taskId != newTaskId){
            List<ProjectTaskItem> items = taskItemRepository.findByTaskId(taskId);
            for (ProjectTaskItem taskItem : items) {
                taskItem.setId(null);
                taskItem.setTaskId(newTaskId);
                taskItem = taskItemRepository.save(taskItem);
                if(taskItem != null)
                    results.add(taskItem);
            }
        }
        return results;
    }

    public ProjectTask unUsedAddPauseDurationOnTaskDelay(ProjectTask task){
        if(task != null && task.getStartAt() != null){
            LocalDate now  = LocalDate.now();
            Period p = Period.between(task.getPauseAt().atZone(ZoneOffset.UTC).toLocalDate(), now);
            long diffSeconds = Duration.between(task.getPauseAt(), (new Date()).toInstant()).getSeconds();
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

    public float getProjectAdvencedPercentByTasks(List<ProjectTask> tasks){
        if(tasks != null && !tasks.isEmpty()){
             int finishedTaskNumber =  tasks.stream()
                                        .filter(task -> task.getStatus() == ProjectTaskStatus.COMPLETED)
                                        .map(task -> cleanPonderation(task.getPonderation(),1))
                                        .mapToInt(Integer::intValue).sum();
            int unfinishedTaskNumber = tasks.stream()
                                            .filter(task -> task.getStatus() != ProjectTaskStatus.COMPLETED)
                                            .map(task -> cleanPonderation(task.getPonderation(),1))
                                            .mapToInt(Integer::intValue).sum();
            if(finishedTaskNumber > 0)
                return (finishedTaskNumber * 100) / (finishedTaskNumber + unfinishedTaskNumber);
            else
                return 0;
        }
        return 100;
    }

    public float getProjectProgressStatus(Project project){
        if(project != null && project.getId() != null){
            List<ProjectTask> tasks = taskRepository
                .findByProcessIdAndStatusNotAndValid(project.getId(), ProjectTaskStatus.CANCELED, Boolean.TRUE);
                return getProjectProgressStatus(project, tasks);
            
        }
        return 0;
    }


    public float getProjectProgressStatus(Project project, List<ProjectTask> tasks){
        float progress = 0;
        int pond = 0;
        if(project != null && project.getId() != null){
            if(tasks != null && !tasks.isEmpty()){
                pond = cleanPonderation(project.getTaskGlobalPonderation(), 1);
                progress = pond * getProjectAdvencedPercentByTasks(tasks);
            }
            List<Project> children = projectRepository.findByParentIdAndValid(project.getId(), Boolean.TRUE);
            for (Project child : children) {
                int childPond = cleanPonderation(child.getPonderation(), 1);
                pond = pond + childPond;
                progress = progress + (getProjectProgressStatus(child) * childPond);
            }
            
        }
        return (progress > 0 && pond > 0) ? (progress / pond) : 0;
    }

    public float getProjectProgressStatus(Long projectId){
        if(projectId != null){
            Project project = projectRepository.findById(projectId).orElse(null);
            if(project != null)
                return getProjectProgressStatus(project);
        }
        return 0;
    }

    public Integer cleanPonderation(Integer pond, int defaultIntValue){
        if(pond != null && pond.intValue() > 0)
            return pond;
        return Integer.valueOf(defaultIntValue);
    }

    public void startStartableTasks(Long triggerTaskId, ProjectStartableTaskCond startCond){
        List<ProjectStartableTask>  startableTasks = startableTaskRepository
            .findByTriggerTaskIdAndStartCond(triggerTaskId, startCond);
        List<Long> tasksToStartIds = startableTasks
            .stream().map(item -> item.getStartableTaskId())
            .collect(Collectors.toList());
        List<ProjectTask> tasksToStart = taskRepository.findAllById(tasksToStartIds);
        for (ProjectTask projectTask : tasksToStart) {
            StartTask(projectTask, true, true);
            startStatupTask(projectTask);
        }
    }

}


