package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.common.SqlCommon;
import com.yin.report.etl.dw.dao.*;
import com.yin.report.etl.dw.entity.DimDate;
import com.yin.report.etl.dw.entity.FactChannelBill;
import com.yin.report.etl.dw.service.DimChannelService;
import com.yin.report.etl.dw.service.DimColorService;
import com.yin.report.etl.dw.service.DimGoodsService;
import com.yin.report.etl.dw.service.DimSizeService;
import com.yin.report.etl.source.lijing.common.LijinServiceCommon;
import com.yin.report.etl.source.lijing.dao.ChannelBillDao;
import com.yin.report.etl.source.lijing.dao.CheckDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 销售服务
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Service
public class ChannelBillService {


    @Autowired
    private ChannelBillDao channelBillDao;
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
    private DimDateDao dimDateDao;
    @Autowired
    private DimColorDao dimColorDao;
    @Autowired
    private FactChannelBillDao factChannelBillDao;
    @Autowired
    private DimSizeDao dimSizeDao;
    @Autowired
    private DimGoodsDao dimGoodsDao;

    /**
     * 抽取渠道单据并保存维度和事实
     */
    public void etlChannelBill(String erpKey, String dwKey, Date lastSuccessDate) throws IOException, Exception {
        DBIdentifier.setProjectCode(erpKey);
        int maxSizeCount = checkDao.findMaxSizeCount();
        String sizeSql = SqlCommon.createSizeSql(maxSizeCount);
        Integer totalCount = channelBillDao.findChannelBillCount(lastSuccessDate);
        Integer maxRs = 100000;

        //查询全部 渠道 颜色 货号 尺码
        DBIdentifier.setProjectCode(dwKey);
        //时间
        Map<Long, Long> dimDateMap = new HashMap<>();
        //渠道
        Map<String, Long> dimChannelMap = dimChannelService.findCodeMap();
        //货品
        Map<String, Long> dimGoodsMap = dimGoodsService.findCodeMap();
        //颜色
        Map<String, Long> dimColorMap = dimColorService.findCodeMap();
        //尺码
        Map<String, Long> dimSizeMap = dimSizeService.findCodeMap();
        //单号
        List<String> billList = factChannelBillDao.findAllBillCode();
        for (int i = 0; i <= totalCount / maxRs; i++) {
            List<FactChannelBill> list = new ArrayList<>();
            DBIdentifier.setProjectCode(erpKey);
            List<Map<String, Object>> subList = channelBillDao.findChannelBillList(sizeSql, i * maxRs + 1, maxRs, lastSuccessDate);
            DBIdentifier.setProjectCode(dwKey);
            for (Map<String, Object> sub : subList) {
                if (billList.contains(ObjectUtils.getString(sub.get("bill_code")))) {
                    continue;
                }
                //如果数据有空的就跳过不计算
                if (sub.get("channel_code") == null || sub.get("goods_color_code") == null || sub.get("goods_code") == null || sub.get("size_class") == null || sub.get("bill_code") == null) {
                    continue;
                }
                if (StringUtils.isBlank(sub.get("channel_code").toString()) || StringUtils.isBlank(sub.get("goods_color_code").toString()) || StringUtils.isBlank(sub.get("goods_code").toString()) || StringUtils.isBlank(sub.get("size_class").toString()) || StringUtils.isBlank(sub.get("bill_code").toString())) {
                    continue;
                }
                //尺码
                for (int s = 1; s <= maxSizeCount; s++) {
                    String sizeCode = "S" + s;
                    if (ObjectUtils.getInteger(sub.get(sizeCode)) == 0) {
                        continue;
                    }
                    FactChannelBill fcb = new FactChannelBill();
                    //维度
                    //时间
                    fcb.setBillDateFact(ObjectUtils.getDateNoHMS((Date) sub.get("bill_date")));
                    if (dimDateMap.containsKey(fcb.getBillDateFact().getTime())) {
                        fcb.setDateSk(dimDateMap.get(fcb.getBillDateFact().getTime()));
                    } else {
                        DimDate dimDate = dimDateDao.findByDate(fcb.getBillDateFact());
                        if (dimDate != null) {
                            dimDateMap.put(dimDate.getDateDate().getTime(), dimDate.getDateSk());
                        } else {
                            try {
                                Long id = dimDateDao.insert(fcb.getBillDateFact());
                                dimDateMap.put(fcb.getBillDateFact().getTime(), id);
                                fcb.setDateSk(id);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        fcb.setDateSk(dimDateMap.get(fcb.getBillDateFact().getTime()));
                    }
                    //渠道
                    fcb.setChannelSk(LijinServiceCommon.getSk(ObjectUtils.getString(sub.get("channel_code")), dimChannelMap, dimChannelDao));
                    //颜色
                    fcb.setColorSk(LijinServiceCommon.getSk(ObjectUtils.getString(sub.get("goods_color_code")), dimColorMap, dimColorDao));
                    //货号
                    fcb.setGoodsSk(LijinServiceCommon.getSk(ObjectUtils.getString(sub.get("goods_code")), dimGoodsMap, dimGoodsDao));
                    //尺码
                    fcb.setSizeSk(LijinServiceCommon.getSk(sizeCode, ObjectUtils.getString(sub.get("size_class")), dimSizeMap, dimSizeDao));
                    //事实数据 销售数量 成本金额 销售吊盘价 销售单价
                    fcb.setPriceFact(ObjectUtils.getBigDecimal(sub.get("price")));
                    fcb.setBillCodeFact(ObjectUtils.getString(sub.get("bill_code")));
                    fcb.setBillQuantityFact(ObjectUtils.getInteger(sub.get(sizeCode)));
                    fcb.setBillTypeFact(ObjectUtils.getInteger(sub.get("bill_type")));
                    fcb.setTagPriceFact(ObjectUtils.getBigDecimal(sub.get("tag_price")));
                    list.add(fcb);
                }

            }
            if (!list.isEmpty()) {
                if (lastSuccessDate == null) {
                    //写一个TXT文件到本地
                    factChannelBillDao.write2Txt(list);
                } else {
                    //直接插入到数据库
                    factChannelBillDao.insertBatch(list);
                }
            }
        }
    }

}
