package com.mshz.microproject.service.async;

import java.util.List;

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.domain.enumeration.ProjectStartableTaskCond;
import com.mshz.microproject.domain.enumeration.ProjectTaskFileType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AsyncUtilsService {

    private final Logger log = LoggerFactory.getLogger(AsyncUtilsService.class);

    private final UtilService utilService;

    public AsyncUtilsService(UtilService utilService) {
        this.utilService = utilService;
    }

    /**
     * util method to start all startup tasks for task
     * @param task : task asssociated startup tasks
     */
    @Async
    public void startStratupTasks(ProjectTask task){
        try{
            utilService.startStatupTask(task);
        }catch(Exception e){
            e.printStackTrace();
            log.info("starting tasks error");
        }
    }

    /**
     * copy task files 
     */
    @Async
    public void copyTaskSFiles(List<ProjectTask> tasks, ProjectTaskFileType taskFileType){
        utilService.copyTaskSFiles(tasks, taskFileType);
    }

    @Async
    public void copyTasksTaskItems(List<ProjectTask> tasks){
        utilService.copyTaskitemsFormTasks(tasks);
    }

    /**
     * 
     * @param tasks
     */
    public void copyTasksUsers(List<ProjectTask> tasks) {
        utilService.copyTasksUsers(tasks);
    }
    
    @Async
    public void startStartableTasks(Long triggerTaskId, ProjectStartableTaskCond startCond){
        utilService.startStartableTasks(triggerTaskId, startCond);
    }

    @Async
    @Scheduled(fixedDelay = 30000, initialDelay = 10000) // 10 seconds and initialize on 10 seconds
    public void autoPlayOrPauseTasks(){
        utilService.autoPlayTasks();
        utilService.autoPauseTasks();
        utilService.autoPlaySheduledTaks();
    }
 
}
