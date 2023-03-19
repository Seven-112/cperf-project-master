package com.mshz.microproject.web.rest;

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import com.mshz.microproject.domain.enumeration.ProjectTaskUserRole;
import com.mshz.microproject.domain.projection.ChronoUtil;
import com.mshz.microproject.domain.projection.ITaskProject;
import com.mshz.microproject.domain.projection.ReactFrappeGanttUtil;
import com.mshz.microproject.service.ProjectTaskService;
import com.mshz.microproject.web.rest.errors.BadRequestAlertException;
import com.mshz.microproject.service.dto.ProjectTaskCriteria;
import com.mshz.microproject.service.ProjectTaskQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mshz.microproject.domain.ProjectTask}.
 */
@RestController
@RequestMapping("/api")
public class ProjectTaskResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTaskResource.class);

    private static final String ENTITY_NAME = "microprojectProjectTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTaskService projectTaskService;

    private final ProjectTaskQueryService projectTaskQueryService;

    public ProjectTaskResource(ProjectTaskService projectTaskService, ProjectTaskQueryService projectTaskQueryService) {
        this.projectTaskService = projectTaskService;
        this.projectTaskQueryService = projectTaskQueryService;
    }

    /**
     * {@code POST  /project-tasks} : Create a new projectTask.
     *
     * @param projectTask the projectTask to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTask, or with status {@code 400 (Bad Request)} if the projectTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-tasks")
    public ResponseEntity<ProjectTask> createProjectTask(@Valid @RequestBody ProjectTask projectTask) throws URISyntaxException {
        log.debug("REST request to save ProjectTask : {}", projectTask);
        if (projectTask.getId() != null) {
            throw new BadRequestAlertException("A new projectTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTask result = projectTaskService.save(projectTask);
        return ResponseEntity.created(new URI("/api/project-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-tasks} : Updates an existing projectTask.
     *
     * @param projectTask the projectTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTask,
     * or with status {@code 400 (Bad Request)} if the projectTask is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks")
    public ResponseEntity<ProjectTask> updateProjectTask(@Valid @RequestBody ProjectTask projectTask) throws URISyntaxException {
        log.debug("REST request to update ProjectTask : {}", projectTask);
        if (projectTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.save(projectTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTask.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-tasks} : get all the projectTasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTasks in body.
     */
    @GetMapping("/project-tasks")
    public ResponseEntity<List<ProjectTask>> getAllProjectTasks(ProjectTaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjectTasks by criteria: {}", criteria);
        Page<ProjectTask> page = projectTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        List<ProjectTask> sortedByPriority = projectTaskService.sortTasks(page.getContent());
        return ResponseEntity.ok().headers(headers).body(sortedByPriority);
    }

    /**
     * {@code GET  /project-tasks/count} : count all the projectTasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/project-tasks/count")
    public ResponseEntity<Long> countProjectTasks(ProjectTaskCriteria criteria) {
        log.debug("REST request to count ProjectTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTaskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-tasks/:id} : get the "id" projectTask.
     *
     * @param id the id of the projectTask to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTask, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-tasks/{id}")
    public ResponseEntity<ProjectTask> getProjectTask(@PathVariable Long id) {
        log.debug("REST request to get ProjectTask : {}", id);
        Optional<ProjectTask> projectTask = projectTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTask);
    }

    /**
     * {@code DELETE  /project-tasks/:id} : delete the "id" projectTask.
     *
     * @param id the id of the projectTask to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-tasks/{id}")
    public ResponseEntity<Void> deleteProjectTask(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTask : {}", id);
        projectTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    /**
     * 
     * @param empId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/project-tasks/by-employee/{id}")
    public ResponseEntity<Page<ITaskProject>> getEmployeeTasksWithProcessesPage(@PathVariable Long id,Pageable pageable){
             log.debug("REST request to find employee tasks page by employee id : {}", id);
    Page<ITaskProject> result = projectTaskService.findByEmployeeId(id, pageable);
    return ResponseEntity.ok().body(result);
    } 
    
    @GetMapping("/project-tasks/findByUserIdAndRoleAndTaskStatus")
    public ResponseEntity<Page<ITaskProject>> getUserTasksWithProcessesPageByUserIdAndRoleAndTaskStatus(
            Long userId, ProjectTaskStatus status, ProjectTaskUserRole role, Pageable pageable){
             log.debug("REST request to find usser tasks page by employee id and task status: {}", userId);
    Page<ITaskProject> result = projectTaskService.findByUserIdAndRoleAndTaskStatus(userId, status, role, pageable);
    return ResponseEntity.ok().body(result);
    }

    @GetMapping("/project-tasks/findByUserIdAndRoleAndTaskStatusIn")
    public ResponseEntity<Page<ITaskProject>> getUserTasksWithProcessesPageByUserIdAndRoleAndTaskStatus(
             @RequestParam("userId") Long userId, @RequestParam("status") List<ProjectTaskStatus> status, 
             @RequestParam("role") ProjectTaskUserRole role, Pageable pageable){
             log.debug("REST request to find usser tasks page by employee id and task status: {}", userId);
    Page<ITaskProject> result = projectTaskService.findByUserIdAndRoleAndTaskStatusIn(userId, status, role, pageable);
    return ResponseEntity.ok().body(result);
    }

    /**
     * 
     * @param id
     * @param status
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/project-tasks/by-employee-and-status/{id}")
    public ResponseEntity<Page<ITaskProject>> getEmployeeTasksWithProcessesPageByStatus(@PathVariable Long id,
                @RequestParam("status") ProjectTaskStatus status, Pageable pageable){
             log.debug("REST request to find employee tasks page by employee id and task status: {}", id);
    Page<ITaskProject> result = projectTaskService.findByEmployeeIdAndProjectTaskStatus(id, status,pageable);
    return ResponseEntity.ok().body(result);
    }

    @GetMapping("/project-tasks/by-employee-created-between/{id}")
    public ResponseEntity<List<ProjectTask>> getEmployeeTasksCreatedBetween(@PathVariable Long id,
                @RequestParam("startAt") Instant startAt, @RequestParam("endAt") Instant endAt){
        log.debug("REST request to find employee tasks filtered by process created at {} and {}: {}", id, startAt, endAt);
        List<ProjectTask> result = projectTaskService.findEmployeeTasksBetween(id, startAt, endAt);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/project-tasks/startupassociatable/{taskId}/{processId}")
    public ResponseEntity<List<ProjectTask>> getTaskStartupAssiatable(@PathVariable Long taskId,@PathVariable Long processId,Pageable pageable){
        log.debug("REST request to find associable startuble tasks to task by id for process {} and {}: {}", taskId, processId);
        Page<ProjectTask> result = projectTaskService.findTasksToStartupAssociable(taskId, processId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }
    
    /**
     * {@code GET /project-tasks/startups/:id} 
     * @param id
     * @return list of task finded
     */
    @GetMapping("/project-tasks/startups/{id}")
    public ResponseEntity<List<ProjectTask>> getStartupTasks(@PathVariable Long id){
        log.debug("REST request to find startup task for task {}", id);
        List<ProjectTask> tasks = projectTaskService.getStratupTasks(id);
        return ResponseEntity.ok().body(tasks);
    }

    /**
     * {@code GET /project-tasks/children/:id} 
     * @param id
     * @return list of task finded
     */
    @GetMapping("/project-tasks/children/{id}")
    public ResponseEntity<List<ProjectTask>> getChildrenTasks(@PathVariable Long id){
        log.debug("REST request to find children task for task {}", id);
        List<ProjectTask> tasks = projectTaskService.getChildrenTasks(id);
        return ResponseEntity.ok().body(tasks);
    }

    /**
     * {@code PUT  /project-tasks/start} : start an existing task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks/start")
    public ResponseEntity<ProjectTask> startTask(@Valid @RequestBody ProjectTask task) throws URISyntaxException {
        log.debug("REST request to start Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.startTask(task, false);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-tasks/execute} : start an existing task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks/execute")
    public ResponseEntity<ProjectTask> executeTask(@Valid @RequestBody ProjectTask task) throws URISyntaxException {
        log.debug("REST request to execute Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.executeTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }
    /**
     * {@code PUT  /project-tasks/submit} : start an existing task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks/submit")
    public ResponseEntity<ProjectTask> submitTask(@Valid @RequestBody ProjectTask task) throws URISyntaxException {
        log.debug("REST request to submit Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.submitTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-tasks/finish} : start an finish task.
     *
     * @param task the task to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks/finish")
    public ResponseEntity<ProjectTask> finish(@Valid @RequestBody ProjectTask task) throws URISyntaxException {
        log.debug("REST request to finish Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.finishTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /project-tasks/pause} : pause an existing task.
     *
     * @param task the task to pause.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks/pause")
    public ResponseEntity<ProjectTask> pause(@Valid @RequestBody ProjectTask task) throws URISyntaxException {
        log.debug("REST request to pause Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.pauseTask(task, false);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-tasks/play} : paly an existing task.
     *
     * @param task the task to play.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks/play")
    public ResponseEntity<ProjectTask> play(@Valid @RequestBody ProjectTask task) throws URISyntaxException {
        log.debug("REST request to play Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.playTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-tasks/cancel} : cancel an existing task.
     *
     * @param task the task to cancel.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the started task,
     * or with status {@code 400 (Bad Request)} if the task is not valid,
     * or with status {@code 500 (Internal Server Error)} if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-tasks/cancel")
    public ResponseEntity<ProjectTask> cancel(@Valid @RequestBody ProjectTask task) throws URISyntaxException {
        log.debug("REST request to cancel Task : {}", task);
        if (task.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTask result = projectTaskService.cancelTask(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.getId().toString()))
            .body(result);
    }


    @GetMapping("/project-tasks/getCheckableTasksByUserIdAndStatus/{userId}")
    public ResponseEntity<Page<ITaskProject>> getCheckableTasksByUserIdAndStatus(
                @PathVariable Long userId, @RequestParam(name = "status", required = false) ProjectTaskStatus status, Pageable pageable){
             log.debug("REST request to find checkable tasks page by user id {} and task status: {}", userId, status);
        Page<ITaskProject> result = projectTaskService.findCheckableTasksByUserIdAndProjectTaskStatus(userId, status,pageable);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/project-tasks/getChronoUtil/{taskId}")
    public ResponseEntity<ChronoUtil> getChronoUtil(@PathVariable Long taskId) {
        log.debug("REST request to get TasK chrono Util by id : {}", taskId);
        ChronoUtil result = projectTaskService.getTaskChronoUtil(taskId);
        if(result == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET /project-tasks/isChecked/:id} 
     * @param taskId
     * @return boolean in body with true if all required task items checked or false
     */
    @GetMapping("/project-tasks/isChecked/{taskId}")
    public ResponseEntity<Boolean> allRequiredTaskItemsIsChecked(@PathVariable Long taskId){
        log.debug("REST request to check if all required task items checked by task id : {}", taskId);
        Boolean checked = projectTaskService.allRequiredTaskItemsIsChecked(taskId);
        return ResponseEntity.ok().body(checked);
    }

    @GetMapping("/project-tasks/startStartableTasks/")
    public ResponseEntity<Void> startStartableTasks(@RequestParam(name = "taskId") Long triggerTaskId, 
        @RequestParam("startCond") ProjectStartableTaskCond cond){
        log.debug("REST request to start all starable tasks by triggerTaskId: {} and cond: {}", triggerTaskId, cond);
        projectTaskService.startStartableTasks(triggerTaskId, cond);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, triggerTaskId.toString())).build();
    }


    @GetMapping("/project-tasks/getByProjectIdNotIn")
    public ResponseEntity<Page<ProjectTask>> getByProjectIdNotIn(Long[] ids, Pageable pageable){
        log.debug("REST request to find tasks page by project id not in {}", ids);
        Page<ProjectTask> result = projectTaskService.getByProjectIdNotIn(ids,pageable);
        return ResponseEntity.ok().body(result);
    }
    
    @GetMapping("/project-tasks/startByTaskIdAndConds/{taskId}")
    public ResponseEntity<Void> startByTaskIdAndConds(@PathVariable Long taskId,
        @RequestParam("conds") List<String> conds) {
        log.debug("REST request to start ProjectStartableTask by taskid : {} and conds: {}", taskId, conds);
        projectTaskService.startByTaskIdAndConds(taskId, conds);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/project-tasks/updateSheduledStartDate/{taskId}")
    public ResponseEntity<ProjectTask> changeSheduledDate(@PathVariable Long taskId,Instant startDate, Instant endDate){
        log.debug("REST request to update task sheduledStartDate by taskid : {}, startDate: {} and endDate: {}", taskId, startDate, endDate);
        ProjectTask result = projectTaskService.updateSheduledStartDate(taskId, startDate, endDate);
        return ResponseEntity.ok().body(result);
    }


    @GetMapping("/project-tasks/getGanntData/{taskId}")
    public ResponseEntity<ReactFrappeGanttUtil> getGanntData(@PathVariable Long taskId){
        log.debug("get task gant data util by id {}", taskId);
        ReactFrappeGanttUtil result = projectTaskService.getGanntData(taskId);
        return ResponseEntity.ok().body(result);
    }
}