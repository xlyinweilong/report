package com.yin.report.etl.dw.service;

import com.yin.report.etl.dw.dao.DimChannelDao;
import com.yin.report.etl.dw.entity.DimChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道维度service
 *
 * @author yin.weilong
 * @date 2018.11.02
 */

@Service
public class DimChannelService {

    @Autowired
    private DimChannelDao dimChannelDao;

    /**
     * 获取渠道编号和ID的映射
     *
     * @return
     */
    public Map<String, Long> findCodeMap() {
        List<DimChannel> list = dimChannelDao.findAll();
        Map<String, Long> map = new HashMap<>(list.size());
        list.forEach(dimChannel -> {
            map.put(dimChannel.getChannelCode(), dimChannel.getChannelSk());
        });
        return map;
    }
}
