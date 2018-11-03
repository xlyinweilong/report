package com.yin.report.etl.dw.service;

import com.yin.report.etl.dw.common.Constant;
import com.yin.report.etl.dw.dao.DimChannelDao;
import com.yin.report.etl.dw.dao.DimSizeDao;
import com.yin.report.etl.dw.entity.DimChannel;
import com.yin.report.etl.dw.entity.DimSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 尺码维度service
 *
 * @author yin.weilong
 * @date 2018.11.02
 */

@Service
public class DimSizeService {

    @Autowired
    private DimSizeDao dimSizeDao;

    /**
     * 获取渠道编号和ID的映射
     *
     * @return
     */
    public Map<String, Long> findCodeMap() {
        List<DimSize> list = dimSizeDao.findAll();
        Map<String, Long> map = new HashMap<>(list.size());
        list.forEach(dim -> {
            map.put(dim.getSizeCode() + Constant.SEPARATE + dim.getSizeClass(), dim.getSizeSk());
        });
        return map;
    }
}
