package com.yin.report.system.task.enums;

/**
 * 任务日志状态
 */
public enum TaskLogStatus {
    UNKNOWN_ERROR, SUCCESS, DOING;

    public String getMean() {
        switch (this) {
            case SUCCESS:
                return "成功";
            case UNKNOWN_ERROR:
                return "未知错误";
            case DOING:
                return "执行中";
            default:
                throw new RuntimeException("TaskLogStatus缺少枚举状态描述");
        }
    }
}
