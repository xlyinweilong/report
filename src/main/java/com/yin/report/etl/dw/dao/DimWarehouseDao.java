package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimWarehouseDao implements DaoInterface {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询全部渠道
     *
     * @return
     */
    public List<DimWarehouse> findAll() {
        return dynamicJdbcTemplate.query("SELECT warehouse_sk,warehouse_code,warehouse_name FROM dim_warehouse", (resultSet, i) -> {
            DimWarehouse dim = new DimWarehouse();
            dim.setWarehouseSk(resultSet.getLong("warehouse_sk"));
            dim.setWarehouseCode(resultSet.getString("warehouse_code"));
            dim.setWarehouseName(resultSet.getString("warehouse_name"));
            return dim;
        });
    }

    @Override
    public Long insert(String[] code) {
        return this.insert(code[0]);
    }

    /**
     * 插入渠道
     *
     * @return
     */
    public Long insert(String warehouseCode) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_warehouse(warehouse_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, warehouseCode);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * 批量更新
     *
     * @param list
     */
    public void updateBatch(List<DimWarehouse> list) {
        List<Object[]> batch = new ArrayList<>();
        list.forEach(dim -> {
            Object[] values = new Object[]{
                    dim.getWarehouseName(), dim.getWarehouseSk()
            };
            batch.add(values);
        });
        dynamicJdbcTemplate.batchUpdate("UPDATE dim_warehouse SET warehouse_name = ? WHERE warehouse_sk =  ?", batch);
    }
}
