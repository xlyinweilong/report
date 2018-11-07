package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.DimWarehouseDao;
import com.yin.report.etl.dw.entity.DimWarehouse;
import com.yin.report.etl.source.lijing.dao.WarehouseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 仓库服务
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Service
public class WarehouseService {



    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private DimWarehouseDao dimWarehouseDao;

    /**
     * 抽取销售并保存维度和事实
     */
    public void etlWarehouse(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        List<DimWarehouse> erpWarehouseList = warehouseDao.findAll();
        DBIdentifier.setProjectCode(dwKey);
        List<DimWarehouse> dwWarehouseList = dimWarehouseDao.findAll();
        for (DimWarehouse dimWarehouse : dwWarehouseList) {
            int index = erpWarehouseList.indexOf(dimWarehouse);
            if (index > -1) {
                DimWarehouse erpWarehouse = erpWarehouseList.get(index);
                dimWarehouse.setWarehouseName(erpWarehouse.getWarehouseName());
            }
        }
        dimWarehouseDao.updateBatch(dwWarehouseList);
    }

}
