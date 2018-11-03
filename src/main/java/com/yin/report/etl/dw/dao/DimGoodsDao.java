package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimChannel;
import com.yin.report.etl.dw.entity.DimClerk;
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
 * 货品DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimGoodsDao implements DaoInterface {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询全部货品
     *
     * @return
     */
    public List<DimGoods> findAll() {
        return dynamicJdbcTemplate.query("SELECT goods_sk,goods_code FROM dim_goods", (resultSet, i) -> {
            DimGoods dim = new DimGoods();
            dim.setGoodsSk(resultSet.getLong("goods_sk"));
            dim.setGoodsCode(resultSet.getString("goods_code"));
            return dim;
        });
    }

    @Override
    public Long insert(String[] code) {
        return this.insert(code[0]);
    }

    /**
     * 插入货品
     *
     * @return
     */
    public Long insert(String goodsCode) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_goods(goods_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, goodsCode);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
