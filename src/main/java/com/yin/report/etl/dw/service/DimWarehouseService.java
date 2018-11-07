package com.yin.report.etl.dw.service;

import com.yin.report.etl.dw.dao.DimWarehouseDao;
import com.yin.report.etl.dw.entity.DimWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仓库维度service
 *
 * @author yin.weilong
 * @date 2018.11.02
 */

@Service
public class DimWarehouseService {

    @Autowired
    private DimWarehouseDao dimWarehouseDao;

    /**
     * 获取渠道编号和ID的映射
     *
     * @return
     */
    public Map<String, Long> findCodeMap() {
        List<DimWarehouse> list = dimWarehouseDao.findAll();
        Map<String, Long> map = new HashMap<>(list.size());
        list.forEach(dim -> {
            map.put(dim.getWarehouseCode(), dim.getWarehouseSk());
        });
        return map;
    }
}
