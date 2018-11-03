package com.yin.report.etl.dw.service;

import com.yin.report.etl.dw.common.Constant;
import com.yin.report.etl.dw.dao.DimChannelDao;
import com.yin.report.etl.dw.dao.DimColorDao;
import com.yin.report.etl.dw.entity.DimChannel;
import com.yin.report.etl.dw.entity.DimColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 颜色维度service
 *
 * @author yin.weilong
 * @date 2018.11.02
 */

@Service
public class DimColorService {

    @Autowired
    private DimColorDao dimColorDao;


    /**
     * 获取渠道编号和ID的映射
     *
     * @return
     */
    public Map<String, Long> findCodeMap() {
        List<DimColor> list = dimColorDao.findAll();
        Map<String, Long> map = new HashMap<>(list.size());
        list.forEach(dim -> {
            map.put(dim.getColorCode(), dim.getColorSk());
        });
        return map;
    }
}
