package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.common.SqlCommon;
import com.yin.report.etl.dw.dao.*;
import com.yin.report.etl.dw.entity.FactChannelStock;
import com.yin.report.etl.dw.service.DimChannelService;
import com.yin.report.etl.dw.service.DimColorService;
import com.yin.report.etl.dw.service.DimGoodsService;
import com.yin.report.etl.dw.service.DimSizeService;
import com.yin.report.etl.source.lijing.common.LijinServiceCommon;
import com.yin.report.etl.source.lijing.dao.ChannelStockDao;
import com.yin.report.etl.source.lijing.dao.CheckDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 销售服务
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Service
public class ChannelStockService {


    @Autowired
    private ChannelStockDao channelStockDao;
    @Autowired
    private CheckDao checkDao;
    @Autowired
    private DimChannelService dimChannelService;
    @Autowired
    private DimSizeService dimSizeService;
    @Autowired
    private DimColorService dimColorService;
    @Autowired
    private DimGoodsService dimGoodsService;
    @Autowired
    private DimChannelDao dimChannelDao;
    @Autowired
    private DimColorDao dimColorDao;
    @Autowired
    private FactChannelStockDao factChannelStockDao;
    @Autowired
    private DimSizeDao dimSizeDao;
    @Autowired
    private DimGoodsDao dimGoodsDao;

    /**
     * 抽取渠道库存并保存维度和事实
     */
    public void etlChannelStock(String erpKey, String dwKey) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        int maxSizeCount = checkDao.findMaxSizeCount();
        String sizeSql = SqlCommon.createSizeSql(maxSizeCount);
        Integer totalCount = channelStockDao.findChannelBillCount();
        Integer maxRs = 100000;

        //查询全部 渠道 颜色 货号 尺码
        DBIdentifier.setProjectCode(dwKey);
        //渠道
        Map<String, Long> dimChannelMap = dimChannelService.findCodeMap();
        //货品
        Map<String, Long> dimGoodsMap = dimGoodsService.findCodeMap();
        //颜色
        Map<String, Long> dimColorMap = dimColorService.findCodeMap();
        //尺码
        Map<String, Long> dimSizeMap = dimSizeService.findCodeMap();
        for (int i = 0; i <= totalCount / maxRs; i++) {
            List<FactChannelStock> list = new ArrayList<>();
            DBIdentifier.setProjectCode(erpKey);
            List<Map<String, Object>> subList = channelStockDao.findChannelStockList(sizeSql, i * maxRs + 1, maxRs);
            DBIdentifier.setProjectCode(dwKey);
            for (Map<String, Object> sub : subList) {
                //如果数据有空的就跳过不计算
                if (sub.get("channel_code") == null || sub.get("goods_color_code") == null || sub.get("goods_code") == null || sub.get("size_class") == null) {
                    continue;
                }
                if (StringUtils.isBlank(sub.get("channel_code").toString()) || StringUtils.isBlank(sub.get("goods_color_code").toString()) || StringUtils.isBlank(sub.get("goods_code").toString()) || StringUtils.isBlank(sub.get("size_class").toString())) {
                    continue;
                }
                //尺码
                for (int s = 1; s <= maxSizeCount; s++) {
                    String sizeCode = "S" + s;
                    if (ObjectUtils.getInteger(sub.get(sizeCode)) == 0) {
                        continue;
                    }
                    FactChannelStock fcs = new FactChannelStock();
                    //维度
                    //渠道
                    fcs.setChannelSk(LijinServiceCommon.getSk(ObjectUtils.getString(sub.get("channel_code")), dimChannelMap, dimChannelDao));
                    //颜色
                    fcs.setColorSk(LijinServiceCommon.getSk(ObjectUtils.getString(sub.get("goods_color_code")), dimColorMap, dimColorDao));
                    //货号
                    fcs.setGoodsSk(LijinServiceCommon.getSk(ObjectUtils.getString(sub.get("goods_code")), dimGoodsMap, dimGoodsDao));
                    //尺码
                    fcs.setSizeSk(LijinServiceCommon.getSk(sizeCode, ObjectUtils.getString(sub.get("size_class")), dimSizeMap, dimSizeDao));
                    //事实数据 销售数量 成本金额 销售吊盘价 销售单价
                    fcs.setStockQuantityFact(ObjectUtils.getInteger(sub.get(sizeCode)));
                    list.add(fcs);
                }

            }
            if (!list.isEmpty()) {
                factChannelStockDao.write2Txt(list);
            }
        }
    }

}
