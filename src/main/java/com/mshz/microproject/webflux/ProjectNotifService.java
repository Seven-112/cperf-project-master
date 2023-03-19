package com.mshz.microproject.webflux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mshz.microproject.domain.Project;
import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.ProjectTaskItem;
import com.mshz.microproject.domain.ProjectTaskStatusTraking;
import com.mshz.microproject.domain.ProjectTaskUser;
import com.mshz.microproject.domain.enumeration.ProjectTaskStatus;
import com.mshz.microproject.domain.enumeration.ProjectTaskUserRole;
import com.mshz.microproject.repository.ProjectRepository;
import com.mshz.microproject.repository.ProjectTaskItemRepository;
import com.mshz.microproject.repository.ProjectTaskRepository;
import com.mshz.microproject.repository.ProjectTaskUserRepository;
import com.mshz.microproject.webflux.dto.NotifAction;
import com.mshz.microproject.webflux.dto.NotifDTO;
import com.mshz.microproject.webflux.dto.NotifEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProjectNotifService {

    Logger logger = LoggerFactory.getLogger(ProjectNotifService.class);
    
    private final GatewayClientService gatewayClientService;

    private final ProjectTaskUserRepository taskUserRepository;

    private final ProjectTaskRepository projectTaskRepository;

    private final ProjectRepository projectRepository;

    private final ProjectTaskItemRepository taskItemRepository;

    public ProjectNotifService(GatewayClientService gatewayClientService,
        ProjectTaskUserRepository taskUserRepository,
        ProjectTaskRepository projectTaskRepository,
        ProjectRepository projectRepository,
        ProjectTaskItemRepository taskItemRepository){
        this.gatewayClientService = gatewayClientService;
        this.taskUserRepository = taskUserRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
        this.taskItemRepository = taskItemRepository;
    }

    @Async
    public void sendTaskSatatusChangeNote(ProjectTask task){
        logger.info("sending status change notif for task: {}", task);
        if(task != null){
            List<ProjectTaskUser> users = getNotifUserByTaskStatus(task);
            NotifAction action = null;
            switch(task.getStatus()){
                case CANCELED:
                    action = NotifAction.PRJ_TASK_CANCELED;
                    break;
                case EXECUTED:
                    action = NotifAction.PRJ_TASK_EXECUTED;
                    break;
                case SUBMITTED:
                    action = NotifAction.PRJ_TASK_SUBMITTED;
                    break;
                /* case ON_PAUSE:
                    action = NotifAction.PRJ_TASK_ON_PAUSE;
                    users = taskUserRepository.findByTask_id(task.getId()); */
                case COMPLETED:
                    action = NotifAction.PRJ_TASK_COMPLETED;
                    break;
                case STARTED:
                    action = NotifAction.PRJ_TASK_STARTED;
                    break;
                case VALID:
                    action = NotifAction.PRJ_TASK_VALID;
                    break;
                default:
                    action = null;
            } // swith end


            List<NotifEntity> entities = new ArrayList<>();
            entities.add(new NotifEntity(task.getId(), task.getName()));
            Project project = projectRepository.findById(task.getProcessId()).orElse(null);
            if(project != null){
                entities.add(new NotifEntity(project.getId(), project.getLabel()));
            }

            // sendiding notif to checker controller
            if(action == NotifAction.PRJ_TASK_STARTED 
                || action == NotifAction.PRJ_TASK_EXECUTED
                || action == NotifAction.PRJ_TASK_SUBMITTED){
                 sendTaskItemCheckersNotification(task, action, entities);
            }

            if(!users.isEmpty()){
                List<Long> targetUserIds = users.stream()
                    .filter(u -> u.getUserId() != null)
                    .map(u -> u.getUserId().longValue()).distinct()
                    .map(id -> Long.valueOf(id)).collect(Collectors.toList());
                if(targetUserIds != null && !targetUserIds.isEmpty()){
                    NotifDTO note = new NotifDTO();
                    note.setAction(action);
                    note.setTargetUserIds(targetUserIds);
                    note.setEntities(entities);

                    gatewayClientService.sendNotificationToGateway(note);
                }
            }
        }
    }

    @Async
    public void sendTaskLogEditNote(ProjectTaskStatusTraking track,boolean isNew){
        logger.info("sending task track note {}", track);
        if(track != null){
            List<ProjectTaskUser> users = taskUserRepository.findByTaskId(track.getTaskId());
            List<Long> targetUserIds = users.stream()
            .filter(tu -> tu.getUserId() != null && !tu.getUserId().equals(track.getUserId()))
            .map(tu -> tu.getUserId().longValue()).sorted().distinct()
            .map(id -> Long.valueOf(id)).collect(Collectors.toList());
            if(targetUserIds != null && !targetUserIds.isEmpty()){
                ProjectTask task = projectTaskRepository.findById(track.getTaskId()).orElse(null);
                if(task != null){
                    List<NotifEntity> entities = new ArrayList<>();
                    entities.add(new NotifEntity(task.getId(), task.getName()));
                    if(task.getProcessId() != null){
                        Project instance = projectRepository.findById(task.getProcessId()).orElse(null);
                        if(instance != null){
                            entities.add(new NotifEntity(instance.getId(), instance.getLabel()));
                        }
                    }
                    // setting notigication
                    NotifDTO note = new NotifDTO();
                    note.setAction(isNew ? NotifAction.PRJ_TASK_LOG_CREATED : NotifAction.PRJ_TASK_LOG_UPDATED);
                    note.setEntities(entities);
                    note.setTargetUserIds(targetUserIds);
                    // sending notification to gateway api service
                    gatewayClientService.sendNotificationToGateway(note);
                } // end task null check
            }
        } // end track  null check
    }

    @Async
    public void sendTaskItemCheckNotification(ProjectTaskItem taskItem, boolean isNew) {
        logger.info("sending task item notification {} is created: {}", taskItem, isNew);
        if(taskItem != null){
            ProjectTask task = projectTaskRepository.findById(taskItem.getTaskId()).orElse(null);
            if(task != null && task.getStatus() != null &&
                !task.getStatus().equals(ProjectTaskStatus.VALID) && !task.getStatus().equals(ProjectTaskStatus.CANCELED)){
                // setting notications entities
                List<NotifEntity> entities = new ArrayList<>();
                // sending task item to entities
                entities.add(new NotifEntity(taskItem.getId(), taskItem.getName()));
                // sending task to entities
                entities.add(new NotifEntity(task.getId(), task.getName()));
                // sending task instance to entities
                Project project = projectRepository.findById(task.getProcessId()).orElse(null);
                if(project != null){
                    entities.add(new NotifEntity(project.getId(), project.getLabel()));
                }

                NotifAction action = null;
                List<Long> targetIds = new ArrayList<>();
                if(isNew){
                    targetIds.add(taskItem.getCheckerId());
                    action = NotifAction.PRJ_TASK_ITEM_TO_CHECK;
                }else{
                    if(taskItem.isChecked() != null && taskItem.isChecked().booleanValue() == true){
                        if(taskItem.getEditorId() != null)
                            targetIds.add(taskItem.getEditorId());
                        action = NotifAction.PRJ_TASK_ITEM_CHECKED;
                        List<Long> userIds = getNotifUserByTaskStatus(task).stream()
                            .filter(u -> u.getUserId() != null && 
                                !u.getUserId().equals(taskItem.getEditorId())
                                && !u.getUserId().equals(taskItem.getCheckerId()))
                            .map(u -> u.getUserId()).collect(Collectors.toList());
                        if(userIds != null && !userIds.isEmpty())
                            targetIds.addAll(userIds);
                    }
                }

                if(action != null && !targetIds.isEmpty()){
                    // sending notification
                    NotifDTO note = new NotifDTO();
                    note.setAction(action);
                    note.setEntities(entities);
                    note.setTargetUserIds(targetIds);

                    gatewayClientService.sendNotificationToGateway(note);
                    
                }

            } // end task null check if
        }
    }

    private List<ProjectTaskUser> getNotifUserByTaskStatus(ProjectTask task){
        List<ProjectTaskUser> users = new ArrayList<>();
        if(task != null){
            switch(task.getStatus()){
                case STARTED:
                    users = taskUserRepository.findByTaskIdAndRole(task.getId(), ProjectTaskUserRole.EXCEUTOR);
                    break;
                case CANCELED:
                    users = taskUserRepository.findByTaskId(task.getId());
                    break;
                case EXECUTED:
                    users = taskUserRepository.findByTaskIdAndRole(task.getId(), ProjectTaskUserRole.SUBMITOR);
                    break;
                case SUBMITTED:
                    users = taskUserRepository.findByTaskIdAndRole(task.getId(), ProjectTaskUserRole.VALIDATOR);
                    break;/* 
                case ON_PAUSE:
                    action = NotifAction.TASK_ON_PAUSE;
                    targetUserIds = users.stream()
                    .map(t -> t.getUserId()).sorted().collect(Collectors.toList());
                    break; */
                case COMPLETED:
                    users = taskUserRepository.findByTaskIdAndRole(task.getId(), ProjectTaskUserRole.SUBMITOR);
                    break;
                case VALID:
                    users = taskUserRepository.findByTaskIdAndRole(task.getId(), ProjectTaskUserRole.SUBMITOR);
                    break;
                default:
                  // notings
            } // swith end
        }
        return users;
    }

    private void sendTaskItemCheckersNotification(ProjectTask task, NotifAction triggerAction, List<NotifEntity> entities){
        if(task != null){
            List<ProjectTaskItem> taskItems = new ArrayList<>();
            taskItems = taskItemRepository
                .findByTaskIdAndCheckedIsNot(task.getId(), Boolean.TRUE);

            if(task.getTaskModelId() != null && taskItems.isEmpty() && triggerAction == NotifAction.PRJ_TASK_STARTED){
                taskItems = taskItemRepository
                    .findByTaskIdAndCheckedIsNot(task.getTaskModelId(), Boolean.TRUE);
            }
    
            List<Long> targetIds = taskItems.stream()
                .filter(item -> item.getCheckerId() != null)
                .map(item -> item.getCheckerId().longValue())
                .distinct()
                .map(id -> Long.valueOf(id))
                .collect(Collectors.toList());

            if(targetIds != null && !targetIds.isEmpty()){
                // sending notification
                NotifDTO note = new NotifDTO();
                note.setAction(NotifAction.PRJ_TASK_TO_CHECK);
                note.setEntities(entities);
                note.setTargetUserIds(targetIds);
                gatewayClientService.sendNotificationToGateway(note);
            }
        }
    }

}
