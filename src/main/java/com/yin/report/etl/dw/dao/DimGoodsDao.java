package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
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
import java.util.ArrayList;
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

    /**
     * 批量更新
     *
     * @param list
     */
    public void updateBatch(List<DimGoods> list) {
        List<Object[]> batch = new ArrayList<>();
        list.forEach(dim -> {
            Object[] values = new Object[]{
                    dim.getGoodsName(), dim.getGoodsImageUrl(), dim.getSizeClass(), dim.getGoodsBrand(),dim.getGoodsCategory(),dim.getGoodsCategory2(),
                    dim.getGoodsSeries(),dim.getGoodsStyle(),dim.getGoodsPattern(),dim.getGoodsYear(),dim.getGoodsSeason(),dim.getSupplyCode(),dim.getSupplyName(),dim.getMarketTime(),dim.getGoodsSk()
            };
            batch.add(values);
        });
        dynamicJdbcTemplate.batchUpdate("UPDATE dim_goods SET goods_name = ? ,goods_image_url = ? ,size_class=  ?,goods_brand=?,goods_category=?,goods_category_2=?,goods_series=?,goods_style=?,goods_pattern=?,goods_year=?,goods_season=?,supply_code=?,supply_name=?,market_time=?  WHERE goods_sk =  ?", batch);
    }


}
