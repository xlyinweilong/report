package com.yin.report.etl.source.lijing.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 渠道单据DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class ChannelBillDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;


    /**
     * 查询渠道单据总数
     *
     * @return
     */
    public Integer findChannelBillCount(Date lastSuccessDate) {
        String sql = "select sum(co) from (select \n" +
                "count(*) as co from ShopTinDetail as d\n" +
                "left join ShopTinGoods g on g.ShopTinGoodsID = d.ShopTinGoodsID\n" +
                "left join [ShopTin] c on c.ShopTinID = g.ShopTinID\n" +
                "\n" +
                "where c.Posted = 1 \n" +
                (lastSuccessDate == null ? "" : "and (c.Input_Date > ? or c.Modi_Date > ? or c.PostedDate > ?)\n") +
                "union all\n" +
                "select count(*) as co from DealereturnDetail as d\n" +
                "left join DealereturnGoods g on g.DealereturnGoodsID = d.DealereturnGoodsID\n" +
                "left join [Dealereturn] c on c.DealereturnID = g.DealereturnID\n" +
                "where c.Posted = 1 \n" +
                (lastSuccessDate == null ? "" : "and (c.Input_Date > ? or c.Modi_Date > ? or c.PostedDate > ?)\n") +
                "union all\n" +
                "select \n" +
                "count(*) as co from ShopToutDetail as d\n" +
                "left join ShopToutGoods g on g.ShopToutGoodsID = d.ShopToutGoodsID\n" +
                "left join [ShopTout] c on c.ShopToutID = g.ShopToutID\n" +
                "\n" +
                "where c.Posted = 1 " + (lastSuccessDate == null ? "" : "and (c.Input_Date > ? or c.Modi_Date > ? or c.PostedDate > ?) ")+
                ") t";
        if (lastSuccessDate == null) {
            return dynamicJdbcTemplate.queryForObject(sql, Integer.class);
        } else {
            return dynamicJdbcTemplate.queryForObject(sql, new Object[]{lastSuccessDate, lastSuccessDate, lastSuccessDate}, Integer.class);
        }

    }

    /**
     * 查询渠道单据详情
     *
     * @param sizeStr
     * @param firstIndex:起始索引
     * @param pageSize:每页显示的数量
     * @return
     */
    public List<Map<String, Object>> findChannelBillList(String sizeStr, Integer firstIndex, Integer pageSize, Date lastSuccessDate) {
        String sql = "select * from ( \n" +
                "select ROW_NUMBER() OVER(Order by t.bill_date ) AS RowId,t.* \n" +
                " from (select \n" +
                "c.type as bill_type,\n" +
                "c.TinShop as channel_code,\n" +
                "case when g.UnitPrice is not null then g.UnitPrice else 0 end as tag_price,\n" +
                "case when g.BalancePrice is not null then g.BalancePrice else 0 end as price,\n" +
                "c.ShopTinID as bill_code,\n" +
                "c.Tin_Date as bill_date,\n" +
                "g.Goods_No as goods_code,\n" +
                "d.ColorID as goods_color_code,\n" +
                "goods.size_class as size_class,\n" + sizeStr +
                " from ShopTinDetail as d\n" +
                "left join ShopTinGoods g on g.ShopTinGoodsID = d.ShopTinGoodsID\n" +
                "left join [ShopTin] c on c.ShopTinID = g.ShopTinID\n" +
                "left join goods on goods.Goods_No = g.Goods_No\n" +
                "where c.Posted = 1 \n" +
                (lastSuccessDate == null ? "" : "and (c.Input_Date > ? or c.Modi_Date > ? or c.PostedDate > ?)\n") +
                "union all\n" +
                "select \n" +
                "case when type = 1 then -1 else -3 end as bill_type,\n" +
                "c.Customer_id as channel_code,\n" +
                "case when g.UnitPrice is not null then g.UnitPrice else 0 end as tag_price,\n" +
                "case when g.BalancePrice is not null then g.BalancePrice else 0 end as price,\n" +
                "c.DealereturnID as bill_code,\n" +
                "c.Return_Date as bill_date,\n" +
                "g.Goods_No as goods_code,\n" +
                "d.ColorID as goods_color_code,\n" +
                "goods.size_class as size_class,\n" + sizeStr +
                " from DealereturnDetail as d\n" +
                "left join DealereturnGoods g on g.DealereturnGoodsID = d.DealereturnGoodsID\n" +
                "left join [Dealereturn] c on c.DealereturnID = g.DealereturnID\n" +
                "left join goods on goods.Goods_No = g.Goods_No\n" +
                "where c.Posted = 1 \n" +
                (lastSuccessDate == null ? "" : "and (c.Input_Date > ? or c.Modi_Date > ? or c.PostedDate > ?)\n") +
                "\n" +
                "union all\n" +
                "select \n" +
                "-2 as bill_type,\n" +
                "c.ToutShop as channel_code,\n" +
                "case when g.UnitPrice is not null then g.UnitPrice else 0 end as tag_price,\n" +
                "case when g.BalancePrice is not null then g.BalancePrice else 0 end as price,\n" +
                "c.ShopToutID as bill_code,\n" +
                "c.Tout_Date as bill_date,\n" +
                "g.Goods_No as goods_code,\n" +
                "d.ColorID as goods_color_code,\n" +
                "goods.size_class as size_class,\n" + sizeStr +
                " from ShopToutDetail as d\n" +
                "left join ShopToutGoods g on g.ShopToutGoodsID = d.ShopToutGoodsID\n" +
                "left join [ShopTout] c on c.ShopToutID = g.ShopToutID\n" +
                "left join goods on goods.Goods_No = g.Goods_No\n" +
                "where c.Posted = 1 \n" +
                (lastSuccessDate == null ? "" : "and (c.Input_Date > ? or c.Modi_Date > ? or c.PostedDate > ?)\n") +
                ") t ) as b\n" +
                "      where RowId between ? and ? ";
        if (lastSuccessDate == null) {
            return dynamicJdbcTemplate.queryForList(sql, new Object[]{firstIndex, firstIndex + pageSize - 1});
        } else {
            return dynamicJdbcTemplate.queryForList(sql, new Object[]{lastSuccessDate, lastSuccessDate, lastSuccessDate, firstIndex, firstIndex + pageSize - 1});
        }
    }
}
