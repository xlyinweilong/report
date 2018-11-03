package com.yin.report.common.datasource.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 数据源配置
 *
 * @author yin.weilong
 * @date 2018.11.01
 */
@Getter
@Setter
@NoArgsConstructor
public class DatasourceConfig {

    private Long id;
    /**
     * 数据库key
     */
    private String dbKey;

    /**
     * 数据库类型，0是源，1是仓库
     */
    private Integer dbType;

    /**
     * 数据库地址
     */
    private String dbUrl;

    /**
     * 数据库用户
     */
    private String dbUsername;

    /**
     * 数据库密码
     */
    private String dbPassword;

    /**
     * 数据库驱动
     */
    private String dbDriverClassName;

}
