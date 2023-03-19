package com.mshz.microproject.aop;

import com.mshz.microproject.domain.ProjectTask;
import com.mshz.microproject.service.async.AsyncUtilsService;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProjectTaskAspect {
    private final Logger logger = LoggerFactory.getLogger(ProjectTaskAspect.class);
    
    private final AsyncUtilsService asyncUtilsService;

    public ProjectTaskAspect(AsyncUtilsService asyncUtilsService){
        this.asyncUtilsService = asyncUtilsService;
    }

    @AfterReturning(pointcut="execution(* org.springframework.data.repository.CrudRepository.save(..))",returning="returnObject")
    public void projectTaskSaveJoinPoint(Object returnObject){
        if(returnObject != null && returnObject instanceof ProjectTask){
            logger.info("auto calcule project percent advenced start aspect running");
           /*  try {
                ProjectTask projectTask = (ProjectTask) returnObject;
                if(projectTask != null && projectTask.getProcessId() != null)
                    asyncUtilsService.calculeAndSaveProjectAdvencedPercent(projectTask.getProcessId());
            } catch (Exception e) {
                logger.debug("error {} ", e.getMessage());
            } */
        }
    }
}
