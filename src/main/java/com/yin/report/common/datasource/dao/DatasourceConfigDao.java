package com.yin.report.common.datasource.dao;

import com.yin.report.common.datasource.entity.DatasourceConfig;
import com.yin.report.common.datasource.mapper.DatesourceConfigRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据源配置DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DatasourceConfigDao {

    @Autowired
    @Qualifier("systemJdbcTemplate")
    private JdbcTemplate systemJdbcTemplate;

    public List<DatasourceConfig> findAll() {
        return systemJdbcTemplate.query("SELECT db_key,db_type,db_url,db_username,db_password,db_driver_class_name FROM d_datasource", new DatesourceConfigRowMapper());
    }
}
