package com.yin.report.system.task.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务执行日志
 *
 * @author yin.weilong
 * @date 2018.11.03
 */

@Getter
@Setter
public class TaskLog {

    private Long id;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务开始时间
     */
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    private Date taskEndTime;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 任务日志
     */
    private String taskLog;
}
