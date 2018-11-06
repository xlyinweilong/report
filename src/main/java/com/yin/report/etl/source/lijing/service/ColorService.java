package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.DimColorDao;
import com.yin.report.etl.dw.entity.DimColor;
import com.yin.report.etl.source.lijing.dao.ColorDao;
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
public class ColorService {



    @Autowired
    private ColorDao colorDao;
    @Autowired
    private DimColorDao dimColorDao;

    /**
     * 抽取销售并保存维度和事实
     */
    public void etlColor(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        List<DimColor> erpList = colorDao.findAll();
        DBIdentifier.setProjectCode(dwKey);
        List<DimColor> dwList = dimColorDao.findAll();
        for (DimColor dim : dwList) {
            int index = erpList.indexOf(dim);
            if (index > -1) {
                DimColor erp = erpList.get(index);
                dim.setColorCode(erp.getColorCode());
                dim.setColorName(erp.getColorName());
            }
        }
        dimColorDao.updateBatch(dwList);
    }

}
