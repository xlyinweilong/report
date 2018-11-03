package com.yin.report.service;

import com.yin.report.dao.BaseChannelDao;
import com.yin.report.dao.SaleDailyChannelDao;
import com.yin.report.entity.BaseChannelEntity;
import com.yin.report.entity.SaleDailyChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 日报
 *
 * @author yin.weilong
 * @date 2018.10.19
 */
@Service
public class GoodsClusterlService {

//    @Autowired
//    private SaleDailyChannelDao saleDailyChannelDao;
//    @Autowired
//    private BaseChannelDao baseChannelDao;
//
//    /**
//     * 初始创建聚类200条数据
//     */
//    public void createInit200() {
//        Calendar calendar = Calendar.getInstance();
//        Iterable<BaseChannelEntity> channelList = baseChannelDao.findAll();
//        List<SaleDailyChannelEntity> list = new ArrayList();
////        if (saleDailyChannelDao.count() == 0) {
//        for (int i = 150; i > 0; i--) {
//            calendar.setTime(new Date());
//            calendar.add(Calendar.DAY_OF_YEAR, -i);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
//            for (BaseChannelEntity baseChannelEntity : channelList) {
//                SaleDailyChannelEntity saleDailyChannelEntity = new SaleDailyChannelEntity();
//                saleDailyChannelEntity.setChannelCode(baseChannelEntity.getChannelCode());
//                saleDailyChannelEntity.setChannelName(baseChannelEntity.getChannelName());
//                saleDailyChannelEntity.setBillDate(calendar.getTime());
//                if (saleDailyChannelDao.findByBillDateAndChannelCode(saleDailyChannelEntity.getBillDate(), saleDailyChannelEntity.getChannelCode()).size() == 0) {
//                    System.out.println("00000");
//                    list.add(saleDailyChannelEntity);
//                } else {
//                    System.out.println("111111");
//                }
//
//            }
////            }
////            saleDailyChannelDao.saveAll(list);
//        }
//    }
//
//    public void updateSaleDailyChannel() {
//        Calendar calendar = Calendar.getInstance();
//        Calendar calendarEnd = Calendar.getInstance();
//        List<SaleDailyChannelEntity> list = new ArrayList();
////        list.add("id,quantity7,quantity14,quantity_month.quantity_last_year_7,amount_7,amount_14,amount_month,amount_last_year_7,order_quantity_7,order_quantity_14,order_quantity_month,order_quantity_last_year_7,tag_amount_7,tag_amount_14,tag_amount_month,tag_amount_last_year_7");
//        int i = 0;
//        for (SaleDailyChannelEntity saleDailyChannelEntity : saleDailyChannelDao.findAll()) {
//            calendar.setTime(saleDailyChannelEntity.getBillDate());
//            calendar.add(Calendar.DAY_OF_YEAR, -6);
//            Map map7 = saleDailyChannelDao.sumQuantity(saleDailyChannelEntity.getChannelName(), calendar.getTime(), saleDailyChannelEntity.getBillDate());
//            saleDailyChannelEntity.setQuantity7((Long) (map7.get("quantity") == null ? 0L : map7.get("quantity")));
//            saleDailyChannelEntity.setAmount7((BigDecimal) (map7.get("amount") == null ? BigDecimal.ZERO : map7.get("amount")));
//            saleDailyChannelEntity.setOrderQuantity7((Long) (map7.get("orderQuantity") == null ? 0L : map7.get("orderQuantity")));
//            saleDailyChannelEntity.setTagAmount7((BigDecimal) (map7.get("tagAmount") == null ? BigDecimal.ZERO : map7.get("tagAmount")));
//
//            calendar.setTime(saleDailyChannelEntity.getBillDate());
//            calendar.add(Calendar.DAY_OF_YEAR, -13);
//            calendarEnd.setTime(saleDailyChannelEntity.getBillDate());
//            calendarEnd.add(Calendar.DAY_OF_YEAR, -7);
//            Map map14 = saleDailyChannelDao.sumQuantity(saleDailyChannelEntity.getChannelName(), calendar.getTime(), calendarEnd.getTime());
//            saleDailyChannelEntity.setQuantity14((Long) (map14.get("quantity") == null ? 0L : map14.get("quantity")));
//            saleDailyChannelEntity.setAmount14((BigDecimal) (map14.get("amount") == null ? BigDecimal.ZERO : map14.get("amount")));
//            saleDailyChannelEntity.setOrderQuantity14((Long) (map14.get("orderQuantity") == null ? 0L : map14.get("orderQuantity")));
//            saleDailyChannelEntity.setTagAmount14((BigDecimal) (map14.get("tagAmount") == null ? BigDecimal.ZERO : map14.get("tagAmount")));
//
//            calendar.setTime(saleDailyChannelEntity.getBillDate());
//            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            Map mapMonth = saleDailyChannelDao.sumQuantity(saleDailyChannelEntity.getChannelName(), calendar.getTime(), saleDailyChannelEntity.getBillDate());
//            saleDailyChannelEntity.setQuantityMonth((Long) (mapMonth.get("quantity") == null ? 0L : mapMonth.get("quantity")));
//            saleDailyChannelEntity.setAmountMonth((BigDecimal) (mapMonth.get("amount") == null ? BigDecimal.ZERO : mapMonth.get("amount")));
//            saleDailyChannelEntity.setOrderQuantityMonth((Long) (mapMonth.get("orderQuantity") == null ? 0L : mapMonth.get("orderQuantity")));
//            saleDailyChannelEntity.setTagAmountMonth((BigDecimal) (mapMonth.get("tagAmount") == null ? BigDecimal.ZERO : mapMonth.get("tagAmount")));
//
//            calendar.setTime(saleDailyChannelEntity.getBillDate());
//            calendar.add(Calendar.YEAR, -1);
//            calendar.add(Calendar.DAY_OF_YEAR, -6);
//            calendarEnd.setTime(saleDailyChannelEntity.getBillDate());
//            calendarEnd.add(Calendar.YEAR, -1);
//            Map mapLastYear = saleDailyChannelDao.sumQuantityNative(saleDailyChannelEntity.getChannelName(), calendar.getTime(), calendarEnd.getTime());
//            saleDailyChannelEntity.setQuantityLastYear7((Long) (mapLastYear.get("quantity") == null ? 0L : Long.parseLong(mapLastYear.get("quantity").toString())));
//            saleDailyChannelEntity.setAmountLastYear7((BigDecimal) (mapLastYear.get("amount") == null ? BigDecimal.ZERO : mapLastYear.get("amount")));
//            saleDailyChannelEntity.setOrderQuantityLastYear7((Long) (mapLastYear.get("orderQuantity") == null ? 0L : Long.parseLong(mapLastYear.get("orderQuantity").toString())));
//            saleDailyChannelEntity.setTagAmountLastYear7((BigDecimal) (mapLastYear.get("tagAmount") == null ? BigDecimal.ZERO : mapLastYear.get("tagAmount")));
//            list.add(saleDailyChannelEntity);
////            saleDailyChannelDao.save(saleDailyChannelEntity);
//            System.out.println(i++);
////            list.add("" + saleDailyChannelEntity.getId() + "," + saleDailyChannelEntity.getQuantity7() + "," + saleDailyChannelEntity.getQuantity14() + "," + saleDailyChannelEntity.getQuantityMonth() + "," + saleDailyChannelEntity.getQuantityLastYear7() + ","
////                    + saleDailyChannelEntity.getAmount7() + "," + saleDailyChannelEntity.getAmount14() + "," + saleDailyChannelEntity.getAmountMonth() + "," + saleDailyChannelEntity.getAmountLastYear7() + "," + saleDailyChannelEntity.getOrderQuantity7() + "," + saleDailyChannelEntity.getOrderQuantity14() + "," + saleDailyChannelEntity.getOrderQuantityMonth() + "," + saleDailyChannelEntity.getOrderQuantityLastYear7() + ","
////                    + saleDailyChannelEntity.getTagAmount7() + "," + saleDailyChannelEntity.getTagAmount14() + "," + saleDailyChannelEntity.getTagAmountMonth() + "," + saleDailyChannelEntity.getTagAmountLastYear7());
//        }
////        try {
////            FileUtils.writeLines(new File("d:/sale.txt"), list);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        saleDailyChannelDao.saveAll(list);
//    }
}
