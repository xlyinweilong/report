package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.DimVipDao;
import com.yin.report.etl.dw.entity.DimVip;
import com.yin.report.etl.source.lijing.dao.VipDao;
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
public class VipService {


    @Autowired
    private VipDao vipDao;
    @Autowired
    private DimVipDao dimVipDao;

    /**
     * 抽取销售并保存维度和事实
     */
    public void etlVip(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        List<DimVip> erpList = vipDao.findAll();
        DBIdentifier.setProjectCode(dwKey);
        List<DimVip> dwList = dimVipDao.findAll();
        for (DimVip dim : dwList) {
            int index = erpList.indexOf(dim);
            if (index > -1) {
                DimVip erp = erpList.get(index);
                dim.setVipGrade(erp.getVipGrade());
                dim.setVipDiscount(erp.getVipDiscount());
                dim.setVipSex(erp.getVipSex());
                dim.setVipTel(erp.getVipTel());
                dim.setVipStartDate(erp.getVipStartDate());
                dim.setVipName(erp.getVipName());
                dim.setVipCode(erp.getVipCode());
            }
        }
        dimVipDao.updateBatch(dwList);
    }

}
