package com.yin.report.etl.dw.service;

import com.yin.report.etl.dw.dao.DimGoodsDao;
import com.yin.report.etl.dw.dao.DimVipDao;
import com.yin.report.etl.dw.entity.DimGoods;
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
public class DimGoodsService {

    @Autowired
    private DimGoodsDao dimGoodsDao;

    /**
     * 获取渠道编号和ID的映射
     *
     * @return
     */
    public Map<String, Long> findCodeMap() {
        List<DimGoods> list = dimGoodsDao.findAll();
        Map<String, Long> map = new HashMap<>(list.size());
        list.forEach(dim -> {
            map.put(dim.getGoodsCode(), dim.getGoodsSk());
        });
        return map;
    }
}
