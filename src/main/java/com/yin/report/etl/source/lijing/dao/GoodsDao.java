package com.yin.report.etl.source.lijing.dao;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 销售DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class GoodsDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;


    public List<DimGoods> findAll() {
        String sql = "select Goods_no as goods_code,Goods_name as goods_name,Picture as goods_image_url,Brand as goods_brand,\n" +
                "Category as goods_category,Range as goods_series,Style as goods_style,Pattern as goods_pattern,size_class,NewOld as market_time,\n" +
                "Year as goods_year,Supply_No as supply_code,Season as goods_season,reserve1 as goods_category_2\n" +
                " from Goods";
        return dynamicJdbcTemplate.query(sql, (resultSet, i) -> {
            DimGoods dim = new DimGoods();
            dim.setGoodsCode(ObjectUtils.getString(resultSet.getString("goods_code")));
            dim.setGoodsBrand(ObjectUtils.getString(resultSet.getString("goods_brand")));
            dim.setGoodsCategory(ObjectUtils.getString(resultSet.getString("goods_category")));
            dim.setGoodsCategory2(ObjectUtils.getString(resultSet.getString("goods_category_2")));
            dim.setGoodsImageUrl(ObjectUtils.getString(resultSet.getString("goods_image_url")));
            dim.setGoodsName(ObjectUtils.getString(resultSet.getString("goods_name")));
            dim.setGoodsPattern(ObjectUtils.getString(resultSet.getString("goods_pattern")));
            dim.setGoodsSeason(ObjectUtils.getString(resultSet.getString("goods_season")));
            dim.setGoodsSeries(ObjectUtils.getString(resultSet.getString("goods_series")));
            dim.setGoodsStyle(ObjectUtils.getString(resultSet.getString("goods_style")));
            dim.setGoodsYear(ObjectUtils.getString(resultSet.getString("goods_year")));
            dim.setSizeClass(ObjectUtils.getString(resultSet.getString("size_class")));
            dim.setSupplyCode(ObjectUtils.getString(resultSet.getString("supply_code")));
            dim.setMarketTime(ObjectUtils.getString(resultSet.getString("market_time")));
            return dim;
        });
    }

}
