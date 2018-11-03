package com.yin.report.etl.source.lijing.service;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.common.Constant;
import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.dao.*;
import com.yin.report.etl.dw.entity.DimDate;
import com.yin.report.etl.dw.entity.FactSale;
import com.yin.report.etl.dw.service.*;
import com.yin.report.etl.source.lijing.dao.CheckDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 销售服务
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Service
public class CheckService {


    @Autowired
    private CheckDao checkDao;
    @Autowired
    private DimChannelService dimChannelService;
    @Autowired
    private DimClerkService dimClerkService;
    @Autowired
    private DimVipService dimVipService;
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
    private DimClerkDao dimCherkDao;
    @Autowired
    private DimColorDao dimColorDao;
    @Autowired
    private DimVipDao dimVipDao;
    @Autowired
    private DimSizeDao dimSizeDao;
    @Autowired
    private DimGoodsDao dimGoodsDao;
    @Autowired
    private FactSaleDao factSaleDao;

    /**
     * 生成尺码SQL
     *
     * @param count
     * @return
     */
    public String createSizeSql(int count) {
        StringBuffer sb = new StringBuffer();
        String code = "S";
        for (int i = 1; i < count; i++) {
            sb.append(code).append(i).append(",");
        }
        sb.append(code).append(count);
        return sb.toString();
    }

    /**
     * 抽取销售并保存维度和事实
     */
    public void findCheckDetailList() throws IOException {
        int maxSizeCount = checkDao.findMaxSizeCount();
        String sizeSql = this.createSizeSql(maxSizeCount);
        Integer totalCount = checkDao.findSaleCount();
        Integer maxRs = 300000;


        //查询全部 渠道 员工 颜色 货号 会员 尺码
        DBIdentifier.setProjectCode("dw_nuoqi");
        //时间
        Map<Date, Long> dimDateMap = new HashMap<>();
        //渠道
        Map<String, Long> dimChannelMap = dimChannelService.findCodeMap();
        //货品
        Map<String, Long> dimGoodsMap = dimGoodsService.findCodeMap();
        //颜色
        Map<String, Long> dimColorMap = dimColorService.findCodeMap();
        //尺码
        Map<String, Long> dimSizeMap = dimSizeService.findCodeMap();
        //员工
        Map<String, Long> dimClerkMap = dimClerkService.findCodeMap();
        //会员
        Map<String, Long> dimVipMap = dimVipService.findCodeMap();
        for (int i = 0; i <= totalCount / maxRs; i++) {
            List<FactSale> list = new ArrayList<>();
            DBIdentifier.setProjectCode("erp_nuoqi");
            List<Map<String, Object>> subList = checkDao.findSaleList(sizeSql, i * maxRs + 1, maxRs);
            DBIdentifier.setProjectCode("dw_nuoqi");
            subList.forEach(sub -> {
                //尺码
                for (int s = 1; s <= maxSizeCount; s++) {
                    String sizeCode = "S" + s;
                    if (this.getInteger(sub.get(sizeCode)) == 0) {
                        continue;
                    }
                    FactSale fs = new FactSale();
                    //维度
                    //时间
                    fs.setBillDate((Date) sub.get("bill_date"));
                    if (dimDateMap.containsKey(fs.getBillDate())) {
                        fs.setDateSk(dimDateMap.get(fs.getBillDate()));
                    } else {
                        DimDate dimDate = dimDateDao.findByDate(fs.getBillDate());
                        if (dimDate != null) {
                            dimDateMap.put(dimDate.getDateDate(), dimDate.getDateSk());
                        } else {
                            try {
                                Long id = dimDateDao.insert(fs.getBillDate());
                                dimDateMap.put(fs.getBillDate(), id);
                                fs.setDateSk(id);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //渠道
                    fs.setChannelSk(this.getSk(this.getString(sub.get("channel_code")), dimChannelMap, dimChannelDao));
                    //员工
                    fs.setClerkSk(this.getSk(this.getString(sub.get("clerk_code")), dimClerkMap, dimCherkDao));
                    //颜色
                    fs.setColorSk(this.getSk(this.getString(sub.get("goods_color_code")), dimColorMap, dimColorDao));
                    //货号
                    fs.setGoodsSk(this.getSk(this.getString(sub.get("goods_code")), dimGoodsMap, dimGoodsDao));
                    //会员
                    fs.setVipSk(this.getSk(this.getString(sub.get("vip_code")), dimVipMap, dimVipDao));
                    //尺码
                    fs.setSizeSk(this.getSk(sizeCode, this.getString(sub.get("size_class")), dimSizeMap, dimSizeDao));
                    //事实数据 销售数量 成本金额 销售吊盘价 销售单价
//                    fs.setCostAmountFact(this.getBigDecimal(sub.get("vip_code")));
                    fs.setSalePriceFact(this.getBigDecimal(sub.get("sale_price")));
                    fs.setSaleQuantityFact(this.getInteger(sub.get(sizeCode)));
                    fs.setSaleTagPriceFact(this.getBigDecimal(sub.get("sale_tag_price")));
                    fs.setBillCode(this.getString(sub.get("bill_code")));
                    list.add(fs);
                }
            });
            //写一个TXT文件到本地

            factSaleDao.insertBacth(list);
        }
//        List<Map<String, Object>> list = checkDao.findSaleList(sizeSql);
    }

    private Long getSk(String key, Map<String, Long> codeLongMap, DaoInterface daoInterface) {
        return this.getSk(key, null, codeLongMap, daoInterface);
    }

    private Long getSk(String key, String subKey, Map<String, Long> codeLongMap, DaoInterface daoInterface) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (codeLongMap.containsKey(key)) {
            return codeLongMap.get(key);
        } else {
            //插入数据
//            DBIdentifier.setProjectCode("dw_nuoqi");
            String[] keys = new String[2];
            keys[0] = key;
            keys[1] = subKey;
            Long id = daoInterface.insert(keys);
            codeLongMap.put(key, id);
            return id;
        }
    }

    private BigDecimal getBigDecimal(Object o) {
        if (o == null) {
            return BigDecimal.ZERO;
        }
        if (o instanceof BigDecimal) {
            return (BigDecimal) o;
        } else if (o instanceof Double) {
            return BigDecimal.valueOf((Double) o);
        } else if (o instanceof String) {
            return BigDecimal.valueOf(Double.parseDouble(o.toString()));
        } else {
            return BigDecimal.valueOf(Double.parseDouble(o.toString()));
        }
    }

    private Integer getInteger(Object o) {
        if (o == null) {
            return 0;
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Double) {
            return (Integer) o;
        } else if (o instanceof String) {
            return Integer.parseInt(o.toString());
        } else {
            return Integer.parseInt(o.toString());
        }
    }


    private String getString(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof String) {
            return o.toString().trim();
        } else {
            return o.toString().trim();
        }
    }
}
