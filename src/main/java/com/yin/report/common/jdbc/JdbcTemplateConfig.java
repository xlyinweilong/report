package com.yin.report.common.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author yin.weilong
 * @date 2018.11.02
 */
@Configuration
public class JdbcTemplateConfig {

    @Bean(name = "systemJdbcTemplate")
    public JdbcTemplate systemJdbcTemplate(
            @Qualifier("systemDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "dynamicJdbcTemplate")
    public JdbcTemplate dynamicJdbcTemplate(
            @Qualifier("dynamicDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
