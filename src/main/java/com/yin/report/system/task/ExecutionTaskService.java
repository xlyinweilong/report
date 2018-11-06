package com.yin.report.system.task;

import com.yin.report.etl.source.lijing.service.*;
import com.yin.report.system.task.dao.TaskDao;
import com.yin.report.system.task.dao.TaskLogDao;
import com.yin.report.system.task.entity.Task;
import com.yin.report.system.task.entity.TaskLog;
import com.yin.report.system.task.enums.TaskLogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * 执行任务
 *
 * @author yin.weilong
 * @date 2018.11.03
 */
@Service
public class ExecutionTaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskLogDao taskLogDao;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private VipService vipService;

    private static final Logger log = LoggerFactory.getLogger(ExecutionTaskService.class);

    /**
     * 开启一个线程执行任务
     */
    @Async
    public void executionTask(Task task) {
        log.info("开始执行任务 {}", task.getId());
        Date now = new Date();
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(task.getId());
        taskLog.setTaskStartTime(now);
        try {
            //销售
            checkService.etlCheck(task.getTaskErpDb(), task.getTaskDwDb(), task.getTaskLastSuccessDate());
            //更关货品
            goodsService.etlGoods(task.getTaskErpDb(), task.getTaskDwDb());
            //更新
            channelService.etlChannel(task.getTaskErpDb(), task.getTaskDwDb());
            //更新员工
            clerkService.etlClerk(task.getTaskErpDb(), task.getTaskDwDb());
            //更新颜色
            colorService.etlColor(task.getTaskErpDb(), task.getTaskDwDb());
            //更新尺码
            sizeService.etlSize(task.getTaskErpDb(), task.getTaskDwDb());
            //更新VIP
            vipService.etlVip(task.getTaskErpDb(), task.getTaskDwDb());

            //执行完成后更新
            task.setTaskFinishDate(LocalDate.now());
            task.setTaskLastSuccessDate(now);
            taskDao.update(task);
            taskLog.setTaskStatus(TaskLogStatus.SUCCESS.name());
            taskLog.setTaskLog(TaskLogStatus.SUCCESS.getMean());
            taskLog.setTaskEndTime(new Date());
            taskLogDao.insert(taskLog);
        } catch (Throwable e) {
            e.printStackTrace();
            //发生了一个错误
            taskLog.setTaskStatus(TaskLogStatus.UNKNOWN_ERROR.name());
            taskLog.setTaskLog(TaskLogStatus.UNKNOWN_ERROR.getMean());
            taskLog.setTaskEndTime(new Date());
            taskLogDao.insert(taskLog);
        }
    }
}
