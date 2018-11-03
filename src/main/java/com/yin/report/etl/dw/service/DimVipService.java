package com.yin.report.etl.dw.service;

import com.yin.report.etl.dw.dao.DimChannelDao;
import com.yin.report.etl.dw.dao.DimVipDao;
import com.yin.report.etl.dw.entity.DimChannel;
import com.yin.report.etl.dw.entity.DimVip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员维度service
 *
 * @author yin.weilong
 * @date 2018.11.02
 */

@Service
public class DimVipService {

    @Autowired
    private DimVipDao dimVipDao;

    /**
     * 获取渠道编号和ID的映射
     *
     * @return
     */
    public Map<String, Long> findCodeMap() {
        List<DimVip> list = dimVipDao.findAll();
        Map<String, Long> map = new HashMap<>(list.size());
        list.forEach(dimChannel -> {
            map.put(dimChannel.getVipCode(), dimChannel.getVipSk());
        });
        return map;
    }
}
