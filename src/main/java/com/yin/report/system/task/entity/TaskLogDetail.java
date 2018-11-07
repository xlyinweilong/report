package com.yin.report.system.task.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务执行日志详情
 *
 * @author yin.weilong
 * @date 2018.11.03
 */

@Getter
@Setter
public class TaskLogDetail {

    private Long id;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务日志ID
     */
    private Long taskLogId;

    /**
     * 任务开始时间
     */
    private String logDetail;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 当前状态
     */
    private String logStatus;


    /**
     * 本次收录数量
     */
    private Integer logCount;

    /**
     * 仓库内总数
     */
    private Integer dwCount;

    /**
     * erp内数量
     */
    private Integer erpCount;

}
