package com.mshz.microproject.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import com.mshz.microproject.domain.projection.ChronoUtil;
import com.mshz.microproject.domain.projection.ProjectTimeLineGantt;
import com.mshz.microproject.domain.projection.ReactFrappeGanttUtil;
import com.mshz.microproject.repository.ProjectEdgeInfoRepository;
import com.mshz.microproject.repository.ProjectRepository;
import com.mshz.microproject.service.async.UtilService;

/**
 * Service Implementation for managing {@link Project}.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final ProjectTaskService taskService;

    private final UtilService utilService;
    
    private final ProjectTaskPauseHistoryService pauseHistoryService;

    public ProjectService(ProjectRepository projectRepository,
         ProjectTaskService taskService,
         ProjectEdgeInfoRepository edgeInfoRepository,
         UtilService utilService, ProjectTaskPauseHistoryService pauseHistoryService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.utilService = utilService;
        this.pauseHistoryService = pauseHistoryService;
        
    }

    /**
     * Save a project.
     *
     * @param project the entity to save.
     * @return the persisted entity.
     */
    public Project save(Project project) {
        log.debug("Request to save Project : {}", project);
        if(project != null){
            if(project.getPonderation() == null || project.getPonderation().intValue() ==0)
                project.setPonderation(Integer.valueOf(1));
            else
                project.setPonderation(Integer.valueOf(Math.abs(project.getPonderation().intValue())));
        }
        project = generateProjectPath(project);
        project = projectRepository.saveAndFlush(project);
        return project;
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable);
    }


    /**
     * Get one project by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Project> findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id);
    }

    /**
     * Delete the project by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        taskService.deleteByProjectId(id);
        projectRepository.deleteById(id);
    }

    public List<ProjectTimeLineGantt> getReactTimeLineGanttDataByProjectId(Long projectId, List<ProjectTimeLineGantt> resultContainer){
        Project project = projectRepository.findById(projectId).orElse(null);
        return getReactTimeLineGanttDataByProject(project, resultContainer);
    }
    
    public List<ProjectTimeLineGantt> getReactTimeLineGanttDataByProject(Project project, List<ProjectTimeLineGantt> resultContainer) {
        if(project != null){
            Instant startAt = null;
            ProjectTask firstStartedTask = taskService.getFirstStartedTaskByProject(project.getId());
            if(firstStartedTask != null && firstStartedTask.getStartAt() != null)
                startAt = firstStartedTask.getStartAt();
            else
                startAt = project.getCreatedAt() != null ? project.getCreatedAt() : Instant.now();

            Instant previwFinishAt = taskService.getLastPreviwFinishedByProject(project.getId(), startAt);
            if(previwFinishAt == null){
                previwFinishAt = project.getPreviewFinishAt() != null ? project.getPreviewFinishAt() : startAt.plus(1, ChronoUnit.DAYS);
            }
            resultContainer.add(new ProjectTimeLineGantt(project.getId(), project, startAt, previwFinishAt));
            List<Project> childreen = projectRepository.findByParentIdAndValid(project.getId(), Boolean.TRUE);
            for (Project child : childreen)
                resultContainer =  getReactTimeLineGanttDataByProject(child, resultContainer);
        }
        return resultContainer;
    }

    public ChronoUtil getChronoUtil(Long projectId){
        Project project = projectRepository.findById(projectId).orElse(null);
        return getChronoUtil(project);
    }

    public ChronoUtil getChronoUtil(Project project){
        List<ProjectTask> tasks = taskService.findByProjectIdAndValid(project.getId(), Boolean.TRUE);
        return getChronoUtil(project, tasks);
    }

    public ChronoUtil getChronoUtil(Project project, List<ProjectTask> tasks){
        if(project != null && project.isValid()){
            ProjectTaskStatus  status = getProjectChronoStatusByTasks(tasks);
            List<ProjectTask> noCanceledTasks = tasks.stream()
                .filter(t -> t.getStatus() != ProjectTaskStatus.CANCELED)
                .collect(Collectors.toList());
            if(!noCanceledTasks.isEmpty()){

            Instant startDate = noCanceledTasks.stream()
                .filter(t -> t.getStartAt() != null)
                .map(t -> t.getStartAt()).min(Instant::compareTo).orElse(null);

            Instant previewFinishDate = null;

            Integer nbMinutes = 0;

            if(startDate != null){

                Integer nbYears = noCanceledTasks.stream()
                    .filter(t -> t.getNbYears() != null)
                    .mapToInt(t -> t.getNbYears()).sum();

                Integer nbMonths = noCanceledTasks.stream()
                    .filter(t -> t.getNbMonths() != null)
                    .mapToInt(t -> t.getNbMonths()).sum();

                Integer nbDays = noCanceledTasks.stream()
                    .filter(t -> t.getNbDays() != null)
                    .map(t -> t.getNbDays()).collect(Collectors.summingInt(Integer::intValue));

                Integer nbHours = noCanceledTasks.stream()
                    .filter(t -> t.getNbHours() != null)
                    .mapToInt(t -> t.getNbHours()).sum();

                nbMinutes = noCanceledTasks.stream()
                    .filter(t -> t.getNbMinuites() != null)
                    .mapToInt(t -> t.getNbMinuites()).sum();
                nbMinutes = nbMinutes != null ? nbMinutes : 0;
        
                previewFinishDate = ZonedDateTime
                    .ofInstant(startDate, ZoneId.systemDefault())
                    .plusYears(nbYears != null ? nbYears.longValue() : 0)
                    .plusMonths(nbMonths != null ? nbMonths.longValue() : 0)
                    .plusDays(nbDays != null ? nbDays.longValue() : 0)
                    .plusHours(nbHours != null ? nbHours.longValue() : 0)
                    .plusMinutes(nbMinutes != null ? nbMinutes.longValue() : 0)
                    .toInstant();
            }

            Instant pausedDate = null;

            if(status == ProjectTaskStatus.ON_PAUSE){
                pausedDate = noCanceledTasks.stream()
                    .filter(t -> t.getPauseAt() != null)
                    .map(t -> t.getPauseAt()).max(Instant::compareTo).get();
            }

            Instant finishDate = null;

            if(status == ProjectTaskStatus.COMPLETED && previewFinishDate != null){
                finishDate = ZonedDateTime.ofInstant(previewFinishDate, ZoneId.systemDefault())
                            .plusSeconds(getSavedOrLostSeconds(noCanceledTasks, true).longValue())
                            .minusSeconds(getSavedOrLostSeconds(noCanceledTasks, false).longValue())
                            .toInstant();
            }

            boolean execeed =  previewFinishDate != null ?
                (status == ProjectTaskStatus.COMPLETED && finishDate != null)
                ? previewFinishDate.isBefore(finishDate) : previewFinishDate.isBefore(Instant.now())
                : false;

            return new ChronoUtil(startDate, pausedDate, previewFinishDate, finishDate, status, execeed);

            }
        }
        return null;
    }
 
    private ProjectTaskStatus getProjectChronoStatusByTasks(List<ProjectTask> tasks){
        if(tasks != null && !tasks.isEmpty()){
           if(tasks.stream().anyMatch(t -> t.getStatus() == ProjectTaskStatus.STARTED)
            || tasks.stream().anyMatch(t -> t.getStatus() == ProjectTaskStatus.SUBMITTED)
            || tasks.stream().anyMatch(t -> t.getStatus() == ProjectTaskStatus.EXECUTED)){
             return ProjectTaskStatus.STARTED;
           }
 
           if(tasks.stream().allMatch(t -> t.getStatus() == ProjectTaskStatus.CANCELED)){
             return ProjectTaskStatus.CANCELED;
           } 
 
           if(tasks.stream().anyMatch(t -> t.getStatus() == ProjectTaskStatus.COMPLETED)
                 && tasks.stream().noneMatch(t -> t.getStatus() == ProjectTaskStatus.VALID)
                 && tasks.stream().noneMatch(t -> t.getStatus() == ProjectTaskStatus.ON_PAUSE)
                 && tasks.stream().noneMatch(t -> t.getStatus() == ProjectTaskStatus.EXECUTED)
                 && tasks.stream().noneMatch(t -> t.getStatus() == ProjectTaskStatus.SUBMITTED)){
             return ProjectTaskStatus.COMPLETED;
           }
 
           if(tasks.stream().anyMatch(t -> t.getStatus() == ProjectTaskStatus.ON_PAUSE)){
             return ProjectTaskStatus.ON_PAUSE;
           }
           
        }
        return ProjectTaskStatus.VALID;
    }
 
    // calcul le temps perdu ou gagné
    private Long getSavedOrLostSeconds(List<ProjectTask> tasks, boolean execeed){
        Long result = Long.valueOf(0);
        if(tasks != null && !tasks.isEmpty()){
            try {
             long sum = tasks.stream()
                 .filter(t -> t.getFinishAt() != null && t.getStatus() == ProjectTaskStatus.COMPLETED)
                 .map(t -> pauseHistoryService.getTaskChrono(t))
                 .filter(cu -> (cu.getFinishDate() != null && cu.getPrviewFinishDate() != null && cu.isExeceed() == execeed))
                 .mapToLong(tcu -> ChronoUnit.SECONDS.between(tcu.getFinishDate(), tcu.getPrviewFinishDate()))
                 .sum();
             result = Long.valueOf(sum);
            } catch (Exception e) {
                log.debug(e.getMessage());
                e.printStackTrace();
            }
 
        }
        return Math.abs(result.longValue());
    }

    public List<ReactFrappeGanttUtil> getProjectGanntData(Long projectId){
        Project project = projectRepository.findById(projectId).orElse(null);
        return getProjectGanntData(project);
    }

    public List<ReactFrappeGanttUtil> getProjectGanntData(Project project){
        LinkedList<ReactFrappeGanttUtil> ganttUtils = new LinkedList<>();
        List<ProjectTask> tasks = new ArrayList<>();
        if(project != null){
            try {
                tasks = taskService.findByProjectIdAndValidIsTrueOrderByIdAsc(project.getId());
                if(tasks != null && !tasks.isEmpty()){
                    for (ProjectTask task : tasks) {
                        ReactFrappeGanttUtil taskGanntUtil = taskService.getGanntData(task);
                        if(taskGanntUtil != null){
                            if((taskGanntUtil.getDepends() == null
                                 || taskGanntUtil.getDepends().isEmpty()) && task.getProcessId() != null)
                                taskGanntUtil.setDepends(Arrays.asList(task.getProcessId()));
                            ganttUtils.add(taskGanntUtil);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.debug(e.getMessage());
            }
        }

        ganttUtils = (LinkedList) normalizeGanttTaskStartDates(ganttUtils).clone();

        // add children project gantt utils
        ganttUtils.addAll(getChildrenProjectsGanttData(project.getId()));

        // adding project into gantt
        if(project != null){
            Instant startDate = ganttUtils.stream().map(item -> item.getStartDate())
            .min(Instant::compareTo).orElse(Instant.now())
            .atZone(ZoneId.systemDefault())
            .minusDays(1).toInstant();

            Instant endDate = ganttUtils.stream().map(item -> item.getEndDate())
             .max(Instant::compareTo).orElse(Instant.now()
             .atZone(ZoneId.systemDefault()).plusDays(1)
             .toInstant());

            ReactFrappeGanttUtil current = getOneProjectGanttDate(project, tasks, startDate, endDate);
            if(current != null)
                ganttUtils.addFirst(current);
        }

        return ganttUtils;
    }

    public ReactFrappeGanttUtil getOneProjectGanttDate(Project project, List<ProjectTask> tasks, Instant startDate, Instant endDate){
        ReactFrappeGanttUtil data = null;
        try {
            if(project != null){
               ChronoUtil chronoUtil = getChronoUtil(project, tasks);
               data = new ReactFrappeGanttUtil(project, chronoUtil, tasks);
               data.setStartDate(startDate);
               data.setEndDate(endDate);
               float  progress = 0;
               if(tasks != null && !tasks.isEmpty()){
                   List<ProjectTask> noCanceledTasks = tasks.stream()
                       .filter(t->t.getStatus() != ProjectTaskStatus.CANCELED).collect(Collectors.toList());
                   progress = utilService.getProjectProgressStatus(project, noCanceledTasks);
               }else{
                   progress = utilService.getProjectProgressStatus(project, tasks);
               }
               data.setProgress(Float.valueOf(progress));
               if(project.getParentId() != null)
                    data.setDepends(Arrays.asList(project.getParentId()));
               
            }
               
           } catch (Exception e) {
               e.printStackTrace();
               log.debug(e.getMessage());
           }
        return data;
    }

    public LinkedList<ReactFrappeGanttUtil> normalizeGanttTaskStartDates(List<ReactFrappeGanttUtil> taskUtils){
        LinkedList<ReactFrappeGanttUtil> result = new LinkedList<>();
        if(taskUtils != null && !taskUtils.isEmpty()){
            for (ReactFrappeGanttUtil taskUtil : taskUtils) {
                if(taskUtil.getDepends() != null && !taskUtil.getDepends().isEmpty()){
                    Long firstDependId = taskUtil.getDepends().get(0);
                    ReactFrappeGanttUtil depend = getDependById(taskUtils, firstDependId);
                    log.debug("avant {} start at {} finishat {}", taskUtil.getTask().getName(), taskUtil.getStartDate().toString(), taskUtil.getEndDate().toString());
                    if(depend != null && depend.getEndDate() != null){
                        log.debug("depends_debug t {} depend {}", taskUtil.getTask().getName(), depend.getTask().getName()); 
                        taskUtil.setPreviewTaskFinishAt(depend.getEndDate());
                    }
                    log.debug("avant {} start at {} finishat {}", taskUtil.getTask().getName(), taskUtil.getStartDate().toString(), taskUtil.getEndDate().toString());
                }

                try {
                    ReactFrappeGanttUtil clone = new ReactFrappeGanttUtil(taskUtil);
                    if(clone != null)
                        result.add(clone);
                } catch (Exception e) {
                    log.debug("cloning failed");
                }
            }
        }

        return result;
    }

    private LinkedList<ReactFrappeGanttUtil> getChildrenProjectsGanttData(Long projectId){
        LinkedList<ReactFrappeGanttUtil> result = new LinkedList<>();
        if(projectId != null){
            // finding children projects
            // String path = projectId.toString();
            // String startWith = path+"-";
            List<Project> projects = projectRepository.findByParentIdAndValidOrderByPath(projectId, Boolean.TRUE);
            for (Project project : projects) {
                List<ReactFrappeGanttUtil> data = getProjectGanntData(project);
                if(data != null && !data.isEmpty())
                    result.addAll(data);
            }

        }
        return result;
    } 

    private ReactFrappeGanttUtil getDependById(List<ReactFrappeGanttUtil> taskUtils, Long dependId){
        if(taskUtils != null && dependId != null){
            for (ReactFrappeGanttUtil tu : taskUtils) {
                if(tu.getId() != null && tu.getId().equals(dependId))
                    return tu;
            }
        }
        return null;
    }
    

    public List<Project> getAllSimillarProjects(Long projectId) {
        try{
            Project project = projectRepository.findById(projectId).orElse(null);
            if(project != null){
                Project FirstParent = null;
                if(project.getPath() != null){
                    String[] projectPaths = project.getPath().split("-");
                    if(projectPaths != null && projectPaths.length != 0){
                        Long firstParentId = Long.valueOf(projectPaths[0]);
                        FirstParent = projectRepository.findById(firstParentId).orElse(null);
                    }
                }
        
                String path = FirstParent != null ? FirstParent.getId().toString() : project.getId().toString();
                List<Long> ids = new ArrayList<>();
                ids.add(projectId);
                if(FirstParent != null)
                    ids.add(FirstParent.getId());
                if(project.getParentId() != null)
                    ids.add(project.getParentId());
                
                return projectRepository.findByPathContainingIgnoreCaseOrIdIn(path, ids);
            }

        }catch(Exception e){
            log.info("error: {}", e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Project generateProjectPath(Project project){
        if(project != null){
            String path = null;
            Project parent = null;
            if(project.getParentId() != null){
                parent = projectRepository.findById(project.getParentId()).orElse(null);
                path = parent.getPath() != null ? parent.getPath()+'-'+parent.getId().toString() : parent.getId().toString();
            }
            project.setPath(path);
        }
        return project;
    }
    

    public Float getProgressStatus(Long projectId){
        float progress = 0;
        if(projectId != null){
            progress = utilService.getProjectProgressStatus(projectId);
        }
        return Float.valueOf(progress);
    }

}
