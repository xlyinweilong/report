package com.yin.report.etl.source.lijing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 仓库库存DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class WarehouseStockDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;


    /**
     * 查询渠道单据总数
     *
     * @return
     */
    public Integer findWarehouseStockCount() {
        String sql = "select count(*)" +
                "from StockDetail as d\n" +
                "left join [Stock] c on c.StockCode = d.StockCode\n" +
                "left join goods on goods.Goods_No = c.Goods_No\n" +
                "left join Warehouse on Warehouse.Warehouse_No = c.Warehouse_No\n" +
                "where c.Warehouse_No is not null";
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
    public List<Map<String, Object>> findWarehouseStockList(String sizeStr, Integer firstIndex, Integer pageSize) {
        String sql = "select * from (" +
                "select ROW_NUMBER() OVER(Order by d.StockCode ) AS RowId," +
                "c.StockCode,\n" +
                "c.Warehouse_No as Warehouse_code,\n" +
                "c.Goods_No as goods_code,\n" +
                "d.ColorID as goods_color_code,goods.size_class as size_class,\n" +
                sizeStr +
                " from StockDetail as d\n" +
                "left join [Stock] c on c.StockCode = d.StockCode\n" +
                "left join goods on goods.Goods_No = c.Goods_No\n" +
                "left join Warehouse on Warehouse.Warehouse_No = c.Warehouse_No\n" +
                "where c.Warehouse_No is not null ) as  b where RowId between ? and ? ";
        return dynamicJdbcTemplate.queryForList(sql, new Object[]{firstIndex, firstIndex + pageSize - 1});
    }
}
