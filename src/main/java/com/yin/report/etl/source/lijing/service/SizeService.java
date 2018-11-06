package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.DimSizeDao;
import com.yin.report.etl.dw.entity.DimSize;
import com.yin.report.etl.source.lijing.dao.SizeDao;
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
public class SizeService {



    @Autowired
    private SizeDao sizeDao;
    @Autowired
    private DimSizeDao dimSizeDao;

    /**
     * 抽取销售并保存维度和事实
     */
    public void etlSize(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        List<DimSize> erpList = sizeDao.findAll();
        DBIdentifier.setProjectCode(dwKey);
        List<DimSize> dwList = dimSizeDao.findAll();
        for (DimSize dim : dwList) {
            int index = erpList.indexOf(dim);
            if (index > -1) {
                DimSize erp = erpList.get(index);
                dim.setSizeClass(erp.getSizeClass());
                dim.setSizeName(erp.getSizeName());
                dim.setSizeCode(erp.getSizeCode());
            }
        }
        dimSizeDao.updateBatch(dwList);
    }

}
