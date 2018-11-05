package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.entity.FactSale;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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


    public List<String> findAllBillCode() {
        return dynamicJdbcTemplate.queryForList("SELECT DISTINCT(bill_code) FROM fact_sale ORDER BY sale_sk DESC limit 10000", String.class);
    }


    /**
     * 插入货品
     *
     * @return
     */
    public void insertBatch(List<FactSale> factSaleList) {
        List<Object[]> batch = new ArrayList<>();
        factSaleList.forEach(factSale -> {
            Object[] values = new Object[]{
                    factSale.getGoodsSk(), factSale.getColorSk(), factSale.getSizeSk(),
                    factSale.getChannelSk(), factSale.getDateSk(), factSale.getClerkSk() == null ? 0 : factSale.getClerkSk(),
                    factSale.getVipSk() == null ? 0 : factSale.getVipSk(), factSale.getSaleQuantityFact(), factSale.getCostAmountFact(),
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        String separate = "\t";
        factSaleList.forEach(factSale -> {
            StringBuilder sb = new StringBuilder();
            sb.append(factSale.getGoodsSk()).append(separate);
            sb.append(factSale.getColorSk()).append(separate);
            sb.append(factSale.getSizeSk()).append(separate);
            sb.append(factSale.getChannelSk()).append(separate);
            sb.append(factSale.getDateSk()).append(separate);
            sb.append(factSale.getClerkSk() != null ? factSale.getClerkSk() : 0);
            sb.append(separate);
            sb.append(factSale.getVipSk() != null ? factSale.getVipSk() : 0);
            sb.append(separate);
            sb.append(factSale.getSaleQuantityFact()).append(separate);
            sb.append(factSale.getCostAmountFact()).append(separate);
            sb.append(factSale.getSaleTagPriceFact()).append(separate);
            sb.append(factSale.getSalePriceFact()).append(separate);
            sb.append(factSale.getBillCode()).append(separate);
            sb.append(dateFormat.format(factSale.getBillDate()));
            list.add(sb.toString());
        });
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String fileUrl = jarFile.getParentFile().toString() + "/loadFactSales" + System.currentTimeMillis();
        File file = new File(fileUrl);
        FileUtils.writeLines(file, list);
        //加载文件
        dynamicJdbcTemplate.execute("LOAD DATA INFILE '" + fileUrl.replaceAll("\\\\", "/") +
                "' INTO TABLE fact_sale" +
                " (goods_sk, color_sk, size_sk, channel_sk, date_sk,clerk_sk,vip_sk,sale_quantity_fact,cost_amount_fact,sale_tag_price_fact,sale_price_fact,bill_code,bill_date);");
        //删除文件
        FileUtils.deleteQuietly(file);
    }
}
