package com.ymt.springbootdemo;

import com.ymt.springbootdemo.task.quartz.MyJob;
import com.ymt.springbootdemo.task.quartz.MyJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by @author yangmingtian on 2019/11/4
 */
public class TestMain {
    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("ymt", "12345")
                .usingJobData("yang", "234")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withMisfireHandlingInstructionNowWithExistingCount()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
//        scheduler.setJobFactory();
        ListenerManager listenerManager = scheduler.getListenerManager();
        listenerManager.addJobListener(new MyJobListener());
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }
}
