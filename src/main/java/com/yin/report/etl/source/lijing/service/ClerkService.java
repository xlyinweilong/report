package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.DimClerkDao;
import com.yin.report.etl.dw.entity.DimClerk;
import com.yin.report.etl.source.lijing.dao.ClerkDao;
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
public class ClerkService {



    @Autowired
    private ClerkDao clerkDao;
    @Autowired
    private DimClerkDao dimClerkDao;

    /**
     * 抽取销售并保存维度和事实
     */
    public void etlClerk(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        List<DimClerk> erpList = clerkDao.findAll();
        DBIdentifier.setProjectCode(dwKey);
        List<DimClerk> dwList = dimClerkDao.findAll();
        for (DimClerk dim : dwList) {
            int index = erpList.indexOf(dim);
            if (index > -1) {
                DimClerk erp = erpList.get(index);
                dim.setChannelCode(erp.getChannelCode());
                dim.setClerkSex(erp.getClerkSex());
                dim.setClerkBirthDate(erp.getClerkBirthDate());
                dim.setClerkName(erp.getClerkName());
                dim.setClerkCode(erp.getClerkCode());
            }
        }
        dimClerkDao.updateBatch(dwList);
    }

}
