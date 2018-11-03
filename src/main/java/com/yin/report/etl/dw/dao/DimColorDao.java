package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.Constant;
import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimClerk;
import com.yin.report.etl.dw.entity.DimColor;
import com.yin.report.etl.dw.entity.DimGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * 颜色DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimColorDao implements DaoInterface {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询全部颜色
     *
     * @return
     */
    public List<DimColor> findAll() {
        return dynamicJdbcTemplate.query("SELECT color_sk,color_code FROM dim_color", (resultSet, i) -> {
            DimColor dim = new DimColor();
            dim.setColorSk(resultSet.getLong("color_sk"));
            dim.setColorCode(resultSet.getString("color_code"));
            return dim;
        });
    }

    @Override
    public Long insert(String[] code) {
        return this.insert(code[0]);
    }

    /**
     * 插入颜色
     *
     * @return
     */
    public Long insert(String colorCode) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_color(color_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, colorCode);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
