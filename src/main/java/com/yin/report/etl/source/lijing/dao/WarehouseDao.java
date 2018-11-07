package com.yin.report.etl.source.lijing.dao;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 仓库DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class WarehouseDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 从丽晶查询全部的渠道
     *
     * @return
     */
    public List<DimWarehouse> findAll() {
        String sql = "select warehouse_no as warehouse_code,warehouse_na as warehouse_name  from warehouse";
        return dynamicJdbcTemplate.query(sql, (resultSet, i) -> {
            DimWarehouse dim = new DimWarehouse();
            dim.setWarehouseCode(ObjectUtils.getString(resultSet.getString("warehouse_code")));
            dim.setWarehouseName(ObjectUtils.getString(resultSet.getString("warehouse_name")));
            return dim;
        });
    }

}
