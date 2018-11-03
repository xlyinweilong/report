package com.yin.report.common.datasource.mapper;

import com.yin.report.common.datasource.entity.DatasourceConfig;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据源映射
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
public class DatesourceConfigRowMapper implements RowMapper<DatasourceConfig> {

    @Override
    public DatasourceConfig mapRow(ResultSet resultSet, int rows) throws SQLException {
        DatasourceConfig datasourceConfig = new DatasourceConfig();
        datasourceConfig.setDbDriverClassName(resultSet.getString("db_driver_class_name"));
        datasourceConfig.setDbKey(resultSet.getString("db_key"));
        datasourceConfig.setDbPassword(resultSet.getString("db_password"));
        datasourceConfig.setDbType(resultSet.getInt("db_type"));
        datasourceConfig.setDbUrl(resultSet.getString("db_url"));
        datasourceConfig.setDbUsername(resultSet.getString("db_username"));
        return datasourceConfig;
    }


}
