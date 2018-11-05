package com.yin.report.system.task;

import com.yin.report.system.task.dao.TaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ETL定时任务
 *
 * @author yin.weilong
 * @date 2018.11.03
 */
@Component
public class EtlScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(EtlScheduledTasks.class);

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ExecutionTaskService executionTaskService;


    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentTime() {
        log.debug("执行定时任务开始");
        //查询任务
        LocalTime time = LocalTime.now();
        taskDao.findAll().forEach(task -> {
            //判断任务是否能执行
            if (task.getTaskTime().isBefore(time)) {
                //执行任务
                executionTaskService.executionTask(task);
                task.setTaskFinishDate(LocalDate.now());
                taskDao.update(task);
            }
        });
        log.debug("执行定时任务结束");
    }

}
