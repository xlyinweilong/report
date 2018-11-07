package com.yin.report.system.task.service;

import com.yin.report.system.task.entity.TaskLogDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

/**
 * 任务详情描述
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Service
public class TaskLogDetailService {

    @Autowired
    @Qualifier("systemJdbcTemplate")
    private JdbcTemplate systemJdbcTemplate;

    /**
     * 创建一个详情
     *
     * @param taskId
     * @param taskLogId
     * @param logDetail
     * @return
     */
    public TaskLogDetail createDetail(Long taskId, Long taskLogId, String logDetail) {
        TaskLogDetail detail = new TaskLogDetail();
        detail.setTaskLogId(taskLogId);
        detail.setTaskId(taskId);
        detail.setLogDetail(logDetail);
        detail.setStartTime(new Date());
        detail.setLogStatus("开始执行");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        systemJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO t_task_log_detail(task_id,task_log_id,log_detail,start_time,log_status) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, taskId);
            ps.setLong(2, taskLogId);
            ps.setString(3, logDetail);
            ps.setTimestamp(4, new java.sql.Timestamp(detail.getStartTime().getTime()));
            ps.setString(5, detail.getLogStatus());
            return ps;
        }, keyHolder);
        detail.setId(keyHolder.getKey().longValue());
        return detail;
    }

    /**
     * 更新完成这个
     *
     * @param taskLogDetail
     * @return
     */
    public TaskLogDetail updateTaskLogDetail(TaskLogDetail taskLogDetail) {
        taskLogDetail.setEndTime(new Date());
        taskLogDetail.setLogStatus("完成执行");
        systemJdbcTemplate.update("UPDATE t_task_log_detail SET log_status = ?,end_time = ?,log_count=? WHERE id = ?", new Object[]{taskLogDetail.getLogStatus(), taskLogDetail.getEndTime(), taskLogDetail.getLogCount(), taskLogDetail.getId()});
        return taskLogDetail;
    }
}
