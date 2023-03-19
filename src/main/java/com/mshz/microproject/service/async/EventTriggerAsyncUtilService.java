package com.mshz.microproject.service.async;

import com.mshz.microproject.service.ProjectEventTriggerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventTriggerAsyncUtilService {
    private final Logger log = LoggerFactory.getLogger(EventTriggerAsyncUtilService.class);

    private final ProjectEventTriggerService triggerService;

    public EventTriggerAsyncUtilService(ProjectEventTriggerService triggerService){
        this.triggerService = triggerService;
    }
    
    /*
    @Async
    @Scheduled(fixedDelay = 60000, initialDelay = 30000)
    public void autoCreatePlannedInstances(){
        triggerService.autoCreateSheduledInstances();
    } */
}
