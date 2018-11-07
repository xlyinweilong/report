package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.entity.FactChannelStock;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道单据DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class FactChannelStockDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;


    public void truncateTable() {
        dynamicJdbcTemplate.execute("TRUNCATE  fact_channel_stock");
    }


    /**
     * 写入一个纯文本文件
     *
     * @param factChannelStockList
     * @throws IOException
     */
    public void write2Txt(List<FactChannelStock> factChannelStockList) throws IOException {
        List<String> list = new ArrayList<>();
        String separate = "\t";
        factChannelStockList.forEach(factChannelStock -> {
            StringBuilder sb = new StringBuilder();
            sb.append(factChannelStock.getGoodsSk()).append(separate);
            sb.append(factChannelStock.getColorSk()).append(separate);
            sb.append(factChannelStock.getSizeSk()).append(separate);
            sb.append(factChannelStock.getChannelSk()).append(separate);
            sb.append(factChannelStock.getStockQuantityFact());
            list.add(sb.toString());
        });
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String fileUrl = jarFile.getParentFile().toString() + "/loadFactChannelStock" + System.currentTimeMillis();
        File file = new File(fileUrl);
        FileUtils.writeLines(file, list);
        //加载文件
        dynamicJdbcTemplate.execute("LOAD DATA INFILE '" + fileUrl.replaceAll("\\\\", "/") +
                "' INTO TABLE fact_channel_stock" +
                " (goods_sk,color_sk,size_sk,channel_sk,stock_quantity_fact);");
        //删除文件
        FileUtils.deleteQuietly(file);
    }
}
