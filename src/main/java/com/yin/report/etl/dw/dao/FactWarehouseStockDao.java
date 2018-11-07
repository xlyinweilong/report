package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.entity.FactWarehouseStock;
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
 * 仓库库存DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class FactWarehouseStockDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;



    public void truncateTable() {
        dynamicJdbcTemplate.execute("TRUNCATE  fact_warehouse_stock");
    }


    /**
     * 写入一个纯文本文件
     *
     * @param factWarehouseStockList
     * @throws IOException
     */
    public void write2Txt(List<FactWarehouseStock> factWarehouseStockList) throws IOException {
        List<String> list = new ArrayList<>();
        String separate = "\t";
        factWarehouseStockList.forEach(factWarehouseStock -> {
            StringBuilder sb = new StringBuilder();
            sb.append(factWarehouseStock.getGoodsSk()).append(separate);
            sb.append(factWarehouseStock.getColorSk()).append(separate);
            sb.append(factWarehouseStock.getSizeSk()).append(separate);
            sb.append(factWarehouseStock.getWarehouseSk()).append(separate);
            sb.append(factWarehouseStock.getStockQuantityFact());
            list.add(sb.toString());
        });
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String fileUrl = jarFile.getParentFile().toString() + "/loadFactWarehouseStock" + System.currentTimeMillis();
        File file = new File(fileUrl);
        FileUtils.writeLines(file, list);
        //加载文件
        dynamicJdbcTemplate.execute("LOAD DATA INFILE '" + fileUrl.replaceAll("\\\\", "/") +
                "' INTO TABLE fact_warehouse_stock" +
                " (goods_sk,color_sk,size_sk,warehouse_sk,stock_quantity_fact);");
        //删除文件
        FileUtils.deleteQuietly(file);
    }
}
