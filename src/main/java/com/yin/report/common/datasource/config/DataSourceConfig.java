package com.yin.report.common.datasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author yin.weilong
 * @date 2018.11.01
 */
@Configuration
public class DataSourceConfig {


    /**
     * 程序数据库
     *
     * @return
     */
    @Bean(name = "systemDataSource")
    @Qualifier("systemDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.system")
    @Primary
    public DataSource systemDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据库
     *
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @Qualifier("dynamicDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic")
    public DataSource dynamicDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.type(DynamicDataSource.class);
        return builder.build();
    }

}
