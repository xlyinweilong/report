package com.yin.report.system.task.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * 任务
 *
 * @author yin.weilong
 * @date 2018.11.03
 */

@Getter
@Setter
public class Task {

    private Long id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务执行日期
     */
    private LocalDate taskFinishDate;

    /**
     * 任务执行时间
     */
    private LocalTime taskTime;

    /**
     * erp数据库的key
     */
    private String taskErpDb;

    /**
     * dw数据库的key
     */
    private String taskDwDb;

    /**
     * 任务描述
     */
    private String taskRemarks;

    /**
     * 任务最后执行成功时间
     */
    private Date taskLastSuccessDate;

}
