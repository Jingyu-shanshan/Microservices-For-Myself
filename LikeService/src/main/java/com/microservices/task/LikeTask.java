package com.microservices.task;

import com.microservices.config.aop.LogExecutionTime;
import com.microservices.service.interfaces.ILikeService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class LikeTask extends QuartzJobBean {
    @Autowired
    ILikeService likeService;

    @Override
    @LogExecutionTime
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        likeService.persistLikedFromRedis();
        likeService.persistLikedCountFromRedis();
    }
}
