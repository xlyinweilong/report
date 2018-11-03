package com.yin.report.common.datasource.config;

/**
 * 数据库标识管理类。用于区分数据源连接的不同数据库。
 *
 * @author yin.weilong
 * @date 2018.11.01
 */
public class DBIdentifier {

    /**
     * 用不同的工程编码来区分数据库
     */
    private static ThreadLocal<String> projectCode = new ThreadLocal<>();

    public static String getProjectCode() {
        return projectCode.get();
    }

    public static void setProjectCode(String code) {
        projectCode.set(code);
    }
}
