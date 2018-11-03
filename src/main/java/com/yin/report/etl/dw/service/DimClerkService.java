package com.yin.report.etl.dw.service;

import com.yin.report.etl.dw.dao.DimChannelDao;
import com.yin.report.etl.dw.dao.DimClerkDao;
import com.yin.report.etl.dw.entity.DimChannel;
import com.yin.report.etl.dw.entity.DimClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工维度service
 *
 * @author yin.weilong
 * @date 2018.11.02
 */

@Service
public class DimClerkService {

    @Autowired
    private DimClerkDao dimClerkDao;

    /**
     * 获取员工编号和ID的映射
     *
     * @return
     */
    public Map<String, Long> findCodeMap() {
        List<DimClerk> list = dimClerkDao.findAll();
        Map<String, Long> map = new HashMap<>(list.size());
        list.forEach(dim -> {
            map.put(dim.getClerkCode(), dim.getClerkSk());
        });
        return map;
    }
}
