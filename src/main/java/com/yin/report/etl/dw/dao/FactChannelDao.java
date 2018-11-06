package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.entity.FactChannelBill;
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
public class FactChannelDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;


    public List<String> findAllBillCode() {
        return dynamicJdbcTemplate.queryForList("SELECT DISTINCT(bill_code_fact) FROM fact_channel_bill ORDER BY channel_bill_sk DESC limit 10000", String.class);
    }


    /**
     * 插入货品
     *
     * @return
     */
    public void insertBatch(List<FactChannelBill> factChannelBillList) {
        List<Object[]> batch = new ArrayList<>();
        factChannelBillList.forEach(factChannelBill -> {
            Object[] values = new Object[]{
                    factChannelBill.getGoodsSk(), factChannelBill.getColorSk(), factChannelBill.getSizeSk(),
                    factChannelBill.getChannelSk(),factChannelBill.getDateSk(),factChannelBill.getBillCodeFact(),
                    factChannelBill.getBillTypeFact(),factChannelBill.getBillQuantityFact(),factChannelBill.getPriceFact(),
                    factChannelBill.getBillDateFact()
            };
            batch.add(values);
        });
        dynamicJdbcTemplate.batchUpdate("INSERT INTO fact_channel_bill(goods_sk,color_sk,size_sk,channel_sk,date_sk,bill_code_fact,bill_type_fact,bill_quantity_fact,tag_price_fact,bill_date_fact) VALUES(?,?,?,?,?,?,?,?,?,?", batch);
    }


    /**
     * 写入一个纯文本文件
     *
     * @param factChannelBillList
     * @throws IOException
     */
    public void write2Txt(List<FactChannelBill> factChannelBillList) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        String separate = "\t";
        factChannelBillList.forEach(factChannelBill -> {
            StringBuilder sb = new StringBuilder();
            sb.append(factChannelBill.getGoodsSk()).append(separate);
            sb.append(factChannelBill.getColorSk()).append(separate);
            sb.append(factChannelBill.getSizeSk()).append(separate);
            sb.append(factChannelBill.getChannelSk()).append(separate);
            sb.append(factChannelBill.getDateSk()).append(separate);

            sb.append(factChannelBill.getBillCodeFact()).append(separate);
            sb.append(factChannelBill.getBillTypeFact()).append(separate);
            sb.append(factChannelBill.getBillQuantityFact()).append(separate);
            sb.append(factChannelBill.getPriceFact()).append(separate);
            sb.append(dateFormat.format(factChannelBill.getBillDateFact()));
            list.add(sb.toString());
        });
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String fileUrl = jarFile.getParentFile().toString() + "/loadFactChannelBill" + System.currentTimeMillis();
        File file = new File(fileUrl);
        FileUtils.writeLines(file, list);
        //加载文件
        dynamicJdbcTemplate.execute("LOAD DATA INFILE '" + fileUrl.replaceAll("\\\\", "/") +
                "' INTO TABLE fact_channel_bill" +
                " (goods_sk,color_sk,size_sk,channel_sk,date_sk,bill_code_fact,bill_type_fact,bill_quantity_fact,tag_price_fact,bill_date_fact);");
        //删除文件
        FileUtils.deleteQuietly(file);
    }
}
