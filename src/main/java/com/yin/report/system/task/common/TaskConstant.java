package com.yin.report.system.task.common;

import java.time.format.DateTimeFormatter;

/**
 * 任务常量
 *
 * @author yin.weilong
 * @date 2018.11.04
 */
public class TaskConstant {

    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
}
