package com.yin.report.system.task.dao;

import com.yin.report.system.task.entity.TaskLogDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 任务日志详情DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class TaskLogDetailDao {

    @Autowired
    @Qualifier("systemJdbcTemplate")
    private JdbcTemplate systemJdbcTemplate;

    /**
     * 查询任务日志详情
     *
     * @return
     */
    public List<TaskLogDetail> findByTaskLogId(Long taskLogId) {
        return systemJdbcTemplate.query("SELECT id,task_dw_db,task_id,task_log_id FROM t_task_log_detail WHERE task_log_id = ?", new Object[]{taskLogId},(resultSet, i) -> {
            TaskLogDetail taskLogDetail = new TaskLogDetail();
            taskLogDetail.setId(resultSet.getLong("id"));
            taskLogDetail.setLogDetail(resultSet.getString("task_dw_db"));
            taskLogDetail.setTaskId(resultSet.getLong("task_id"));
            taskLogDetail.setTaskLogId(resultSet.getLong("task_log_id"));
            return taskLogDetail;
        });
    }


    /**
     * 插入任务
     *
     * @return
     */
    public int insert(String channelCode) {
        return systemJdbcTemplate.update("INSERT INTO dim_channel(channel_code) VALUES(?)");
    }

    /**
     * 更新任务
     *
     * @param channelCode
     * @return
     */
    public int update(String channelCode) {
        return systemJdbcTemplate.update("INSERT INTO dim_channel(channel_code) VALUES(?)");
    }
}
