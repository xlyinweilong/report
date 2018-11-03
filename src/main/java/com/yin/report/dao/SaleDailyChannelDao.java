package com.yin.report.dao;

//import com.yin.report.entity.SaleDailyChannelEntity;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日报
 *
 * @author yin.weilong
 * @date 2018.10.19
 */
//@Repository
public interface SaleDailyChannelDao {
//        extends CrudRepository<SaleDailyChannelEntity, Long> {


//    List<SaleDailyChannelEntity> findByBillDateAndChannelCode(Date billDate, String channelCode);
//
//
//    @Query("SELECT SUM(quantity) as quantity,SUM(amount) as amount,SUM(orderQuantity) as orderQuantity,SUM(tagAmount) as tagAmount FROM SaleDailyChannelEntity t WHERE t.channelName = :channelName  AND t.billDate BETWEEN :startDate AND :endDate")
////    @Query("SELECT SUM(t.quantity) as quantity FROM SaleDailyChannelEntity t WHERE t.channelName = :channelName  AND t.billDate BETWEEN :startDate AND :endDate")
//    Map sumQuantity(@Param("channelName") String channelName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
//
//
//    @Query(value = "SELECT SUM(quantity) as quantity,SUM(amount) as amount,SUM(order_quantity) as orderQuantity,SUM(tag_amount) as tagAmount FROM sale_daily_channel_2014 t WHERE t.channel_name = :channelName  AND t.bill_date BETWEEN :startDate AND :endDate",nativeQuery = true)
////    @Query("SELECT SUM(t.quantity) as quantity FROM SaleDailyChannelEntity t WHERE t.channelName = :channelName  AND t.billDate BETWEEN :startDate AND :endDate")
//    Map sumQuantityNative(@Param("channelName") String channelName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
