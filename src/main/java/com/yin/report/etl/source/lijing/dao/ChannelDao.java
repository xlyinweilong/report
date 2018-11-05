package com.yin.report.etl.source.lijing.dao;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 销售DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class ChannelDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 从丽晶查询全部的渠道
     *
     * @return
     */
    public List<DimChannel> findAll() {
        String sql = "select Customer_id as channel_code,Customer_na as channel_name,City as channel_city,Address as channel_address  from Customer";
        return dynamicJdbcTemplate.query(sql, (resultSet, i) -> {
            DimChannel dim = new DimChannel();
            dim.setChannelCode(ObjectUtils.getString(resultSet.getString("channel_code")));
            dim.setChannelName(ObjectUtils.getString(resultSet.getString("channel_name")));
            dim.setChannelCity(ObjectUtils.getString(resultSet.getString("channel_city")));
            dim.setChannelAddress(ObjectUtils.getString(resultSet.getString("channel_address")));
            return dim;
        });
    }

}
