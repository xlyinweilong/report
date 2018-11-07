package com.yin.report.etl.dw.common;

import com.yin.report.common.datasource.config.DBIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yin.weilong
 * @date 2018.11.07
 */

@Component
public class CommonDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    public Integer findCount(String wdCode, String tableName, Date startDate) {
        DBIdentifier.setProjectCode(wdCode);
        if (startDate == null) {
            return dynamicJdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
        } else {
            return dynamicJdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName + "  WHERE create_time > ? ", new Object[]{startDate}, Integer.class);
        }
    }
}
