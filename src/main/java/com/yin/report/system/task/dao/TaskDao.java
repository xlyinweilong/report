package com.yin.report.system.task.dao;

import com.yin.report.system.task.common.TaskConstant;
import com.yin.report.system.task.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 任务DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class TaskDao {

    @Autowired
    @Qualifier("systemJdbcTemplate")
    private JdbcTemplate systemJdbcTemplate;

    /**
     * 查询全部渠道
     *
     * @return
     */
    public List<Task> findAll() {
        return systemJdbcTemplate.query("SELECT id,task_name,task_finish_date,task_last_success_date,task_time,task_erp_db,task_dw_db,task_remarks,task_time FROM t_task WHERE task_finish_date IS NULL OR task_finish_date < DATE(NOW())", (resultSet, i) -> {
            Task task = new Task();
            task.setId(resultSet.getLong("id"));
            task.setTaskDwDb(resultSet.getString("task_dw_db"));
            task.setTaskErpDb(resultSet.getString("task_erp_db"));
            task.setTaskName(resultSet.getString("task_name"));
            task.setTaskRemarks(resultSet.getString("task_remarks"));
            task.setTaskFinishDate(resultSet.getString("task_finish_date") == null ? null : LocalDate.parse(resultSet.getString("task_finish_date")));
            task.setTaskTime(LocalTime.parse(resultSet.getString("task_time")));
            task.setTaskLastSuccessDate(resultSet.getDate("task_last_success_date"));
            return task;
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
     * @param task
     * @return
     */
    public int update(Task task) {
        return systemJdbcTemplate.update("UPDATE t_task SET task_name = ?,task_finish_date = ? ,task_last_success_date = ? ,task_time = ?,task_erp_db = ? ,task_dw_db = ?,task_remarks = ? WHERE id = ?",
                new Object[]{task.getTaskName(), task.getTaskFinishDate() == null ? null : task.getTaskFinishDate().format(TaskConstant.DATE_FORMATTER), task.getTaskLastSuccessDate(), task.getTaskTime() == null ? null : task.getTaskTime().format(TaskConstant.TIME_FORMATTER), task.getTaskErpDb(), task.getTaskDwDb(), task.getTaskRemarks(), task.getId()});
    }
}
