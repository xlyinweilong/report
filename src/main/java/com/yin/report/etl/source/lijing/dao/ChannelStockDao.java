package com.yin.report.etl.source.lijing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 渠道单据DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class ChannelStockDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;


    /**
     * 查询渠道单据总数
     *
     * @return
     */
    public Integer findChannelBillCount() {
        String sql = "select count(*) " +
                "from CStockDetail as d\n" +
                "left join [CStock] c on c.StockCode = d.StockCode\n" +
                "left join goods on goods.Goods_No = c.Goods_No\n" +
                "left join Customer on Customer.Customer_id = c.Customer_ID\n" +
                "where c.Customer_ID is not null";
        return dynamicJdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * 查询渠道单据详情
     *
     * @param sizeStr
     * @param firstIndex:起始索引
     * @param pageSize:每页显示的数量
     * @return
     */
    public List<Map<String, Object>> findChannelStockList(String sizeStr, Integer firstIndex, Integer pageSize) {
        String sql = "select * from (" +
                "select ROW_NUMBER() OVER(Order by d.StockCode ) AS RowId," +
                "c.StockCode,\n" +
                "c.Customer_ID as channel_code,\n" +
                "c.Goods_No as goods_code,\n" +
                "d.ColorID as goods_color_code,\n" +
                "goods.size_class as size_class,\n" +
                sizeStr +
                " from CStockDetail as d\n" +
                "left join [CStock] c on c.StockCode = d.StockCode\n" +
                "left join goods on goods.Goods_No = c.Goods_No\n" +
                "left join Customer on Customer.Customer_id = c.Customer_ID\n" +
                "where c.Customer_ID is not null ) as  b where RowId between ? and ? ";
        return dynamicJdbcTemplate.queryForList(sql, new Object[]{firstIndex, firstIndex + pageSize - 1});
    }
}
