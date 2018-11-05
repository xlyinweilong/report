package com.yin.report.system.task.dao;

import com.yin.report.system.task.entity.Task;
import com.yin.report.system.task.entity.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 任务日志DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class TaskLogDao {

    @Autowired
    @Qualifier("systemJdbcTemplate")
    private JdbcTemplate systemJdbcTemplate;

    /**
     * 查询任务日志
     *
     * @return
     */
    public List<TaskLog> findTop10() {
        return systemJdbcTemplate.query("SELECT id,task_end_time,task_id,task_start_log,task_start_time,task_status FROM t_task_log", (resultSet, i) -> {
            TaskLog taskLog = new TaskLog();
            taskLog.setId(resultSet.getLong("id"));
            taskLog.setTaskEndTime(resultSet.getDate("task_end_time"));
            taskLog.setTaskId(resultSet.getLong("task_id"));
            taskLog.setTaskLog(resultSet.getString("task_start_log"));
            taskLog.setTaskStartTime(resultSet.getDate("task_start_time"));
            taskLog.setTaskStatus(resultSet.getString("task_status"));
            return taskLog;
        });
    }


    /**
     * 插入任务日志
     *
     * @param taskLog
     * @return
     */
    public int insert(TaskLog taskLog) {
        return systemJdbcTemplate.update("INSERT INTO t_task_log(task_end_time,task_id,task_log,task_start_time,task_status) VALUES(?,?,?,?,?)",
                new Object[]{taskLog.getTaskEndTime(), taskLog.getTaskId(), taskLog.getTaskLog(), taskLog.getTaskStartTime(), taskLog.getTaskStatus()});
    }

    /**
     * 更新任务日志
     *
     * @param taskLog
     * @return
     */
    public int update(TaskLog taskLog) {
        return systemJdbcTemplate.update("UPDATE t_task_log SET task_end_time=?,task_id=?,task_log=?,task_start_time=?,task_status=? WHERE id = ?",
                new Object[]{taskLog.getTaskEndTime(), taskLog.getTaskId(), taskLog.getTaskLog(), taskLog.getTaskStartTime(), taskLog.getTaskStatus(), taskLog.getId()});
    }
}
