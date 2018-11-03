package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.entity.FactSale;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 尺码DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class FactSaleDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;


    /**
     * 插入货品
     *
     * @return
     */
    public void insertBacth(List<FactSale> factSaleList) {
        List<Object[]> batch = new ArrayList<>();
        factSaleList.forEach(factSale -> {
            Object[] values = new Object[]{
                    factSale.getGoodsSk(), factSale.getColorSk(), factSale.getSizeSk(),
                    factSale.getChannelSk(), factSale.getDateSk(), factSale.getClerkSk(),
                    factSale.getVipSk(), factSale.getSaleQuantityFact(), factSale.getCostAmountFact(),
                    factSale.getSaleTagPriceFact(), factSale.getSalePriceFact(), factSale.getBillCode(), factSale.getBillDate()
            };
            batch.add(values);
        });
        dynamicJdbcTemplate.batchUpdate("INSERT INTO fact_sale(goods_sk,color_sk,size_sk,channel_sk,date_sk,clerk_sk,vip_sk,sale_quantity_fact,cost_amount_fact,sale_tag_price_fact,sale_price_fact,bill_code,bill_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", batch);
    }


    /**
     * 写入一个纯文本文件
     *
     * @param factSaleList
     * @throws IOException
     */
    public void write2Txt(List<FactSale> factSaleList) throws IOException {
        List<String> list = new ArrayList<>();
        factSaleList.forEach(factSale -> {
            StringBuilder sb = new StringBuilder();

            list.add(sb.toString());
        });
        FileUtils.writeLines(new File(""), list);
    }
}
