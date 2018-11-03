package com.yin.report.etl.source.lijing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 销售DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class CheckDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询最大尺码
     *
     * @return
     */
    public Integer findMaxSizeCount() {
        return dynamicJdbcTemplate.queryForObject(" select count(*)-4 from sysobjects a join syscolumns b  on a.id=b.id where a.name='CheckDetail'", Integer.class);
    }


    /**
     * 查询销售单据总数
     *
     * @return
     */
    public Integer findSaleCount() {
        return dynamicJdbcTemplate.queryForObject("select count(*)" +
                " from CheckDetail as d\n" +
                "left join CheckGoods g on g.CheckGoodsID = d.CheckGoodsID\n" +
                "left join [check] c on c.checkid = g.checkid\n" +
                "left join goods on goods.Goods_No = g.Goods_No\n" +
                "left join Customer on Customer.Customer_id = c.Customer_ID\n" +
                "where c.Posted = 1", Integer.class);
    }

    /**
     * 查询销售单据详情
     *
     * @param sizeStr
     * @param firstIndex:起始索引
     * @param pageSize:每页显示的数量
     * @return
     */
    public List<Map<String, Object>> findSaleList(String sizeStr, Integer firstIndex, Integer pageSize) {
        return dynamicJdbcTemplate.queryForList("select * from ( \n" +
                "select ROW_NUMBER() OVER(Order by c.CheckDate ) AS RowId,\n" +
                "c.Customer_ID as channel_code,\n" +
                "g.UnitPrice as sale_tag_price,\n" +
                "g.BalancePrice as sale_price,\n" +
                "g.discount as sale_discount,\n" +
                "c.CheckID as bill_code,\n" +
                "c.CheckDate as bill_date,\n" +
                "BuisnessManID as clerk_code,\n" +
                "VIP_Card as vip_code,\n" +
                "g.Goods_No as goods_code,\n" +
                "d.ColorID as goods_color_code,\n" +
                "goods.Goods_name as goods_name,\n" +
                "goods.Picture as goods_image_url,\n" +
                "goods.Brand as goods_brand,\n" +
                "goods.Category as goods_category,\n" +
                "goods.Range as goods_series,\n" +
                "goods.Style as goods_style,\n" +
                "goods.Pattern as goods_pattern,\n" +
                "goods.Year as goods_year,\n" +
                "goods.Season as goods_season,goods.size_class as size_class,\n" +
                "Customer.Customer_na as channel_name,\n" +
                "S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,S11\n" +
                " from CheckDetail as d\n" +
                "left join CheckGoods g on g.CheckGoodsID = d.CheckGoodsID\n" +
                "left join [check] c on c.checkid = g.checkid\n" +
                "left join goods on goods.Goods_No = g.Goods_No\n" +
                "left join Customer on Customer.Customer_id = c.Customer_ID\n" +
                "where c.Posted = 1\n" +
                ") as b\n" +
                "      where RowId between ? and ? ", new Object[]{firstIndex, firstIndex + pageSize - 1});
    }
}
