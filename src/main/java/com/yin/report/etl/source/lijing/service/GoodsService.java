package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.DimGoodsDao;
import com.yin.report.etl.dw.entity.DimGoods;
import com.yin.report.etl.source.lijing.dao.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 销售服务
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private DimGoodsDao gimGoodsDao;

    /**
     * 抽取销售并保存维度和事实
     */
    public void etlGoods(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        List<DimGoods> erpList = goodsDao.findAll();
        DBIdentifier.setProjectCode(dwKey);
        List<DimGoods> dwList = gimGoodsDao.findAll();
        for (DimGoods dim : dwList) {
            int index = erpList.indexOf(dim);
            if (index > -1) {
                DimGoods erp = erpList.get(index);
                dim.setSupplyName(erp.getSupplyName());
                dim.setSupplyCode(erp.getSupplyCode());
                dim.setSizeClass(erp.getSizeClass());
                dim.setGoodsBrand(erp.getGoodsBrand());
                dim.setGoodsYear(erp.getGoodsYear());
                dim.setGoodsStyle(erp.getGoodsStyle());
                dim.setGoodsSeries(erp.getGoodsSeries());
                dim.setGoodsSeason(erp.getGoodsSeason());
                dim.setGoodsPattern(erp.getGoodsPattern());
                dim.setGoodsName(erp.getGoodsName());
                dim.setGoodsImageUrl(erp.getGoodsImageUrl());
                dim.setGoodsCategory2(erp.getGoodsCategory2());
                dim.setGoodsCategory(erp.getGoodsCategory());
                dim.setMarketTime(erp.getMarketTime());
            }
        }
        gimGoodsDao.updateBatch(dwList);
    }


}
