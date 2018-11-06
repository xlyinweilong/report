package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.DimChannelDao;
import com.yin.report.etl.dw.entity.DimChannel;
import com.yin.report.etl.source.lijing.dao.ChannelDao;
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
    private ChannelDao channelDao;
    @Autowired
    private DimChannelDao dimChannelDao;

    /**
     * 抽取销售并保存维度和事实
     */
    public void etlChannel(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        List<DimChannel> erpChannelList = channelDao.findAll();
        DBIdentifier.setProjectCode(dwKey);
        List<DimChannel> dwChannelList = dimChannelDao.findAll();
        for (DimChannel dimChannel : dwChannelList) {
            int index = erpChannelList.indexOf(dimChannel);
            if (index > -1) {
                DimChannel erpChannel = erpChannelList.get(index);
                dimChannel.setChannelAddress(erpChannel.getChannelAddress());
                dimChannel.setChannelName(erpChannel.getChannelName());
                dimChannel.setChannelCity(erpChannel.getChannelCity());
            }
        }
        dimChannelDao.updateBatch(dwChannelList);
    }

}
