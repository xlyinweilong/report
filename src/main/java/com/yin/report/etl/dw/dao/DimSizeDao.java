package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimSize;
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
 * 尺码DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimSizeDao implements DaoInterface {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询全部货品
     *
     * @return
     */
    public List<DimSize> findAll() {
        return dynamicJdbcTemplate.query("SELECT size_sk,size_code,size_class FROM dim_size", (resultSet, i) -> {
            DimSize dim = new DimSize();
            dim.setSizeSk(resultSet.getLong("size_sk"));
            dim.setSizeCode(resultSet.getString("size_code"));
            dim.setSizeClass(resultSet.getString("size_class"));
            return dim;
        });
    }


    /**
     * 插入尺码
     *
     * @param code
     * @return
     */
    @Override
    public Long insert(String[] code) {
        return this.insert(code[0], code[1]);
    }


    /**
     * 插入尺码
     *
     * @return
     */
    public Long insert(String sizeCode, String sizeClass) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_size(size_code,size_class) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, sizeCode);
            ps.setString(2, sizeClass);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }


    /**
     * 批量更新
     *
     * @param list
     */
    public void updateBatch(List<DimSize> list) {
        List<Object[]> batch = new ArrayList<>();
        list.forEach(dim -> {
            Object[] values = new Object[]{
                    dim.getSizeCode(), dim.getSizeName(), dim.getSizeClass(),dim.getSizeSk()
            };
            batch.add(values);
        });
        dynamicJdbcTemplate.batchUpdate("UPDATE dim_size SET size_code = ? ,size_name = ?,size_class=? WHERE size_sk =  ?", batch);
    }
}
