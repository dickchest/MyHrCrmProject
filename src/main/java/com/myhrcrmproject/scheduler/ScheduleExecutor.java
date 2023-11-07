package com.myhrcrmproject.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleExecutor {

    private Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);

    @Scheduled(fixedDelayString = "PT20M")
    public void fixedDelayTask(){
        logger.info("Fixed delay task");
    }
}
