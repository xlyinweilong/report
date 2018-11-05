package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimChannel;
import com.yin.report.etl.dw.mapper.DimChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimChannelDao implements DaoInterface {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询全部渠道
     *
     * @return
     */
    public List<DimChannel> findAll() {
        return dynamicJdbcTemplate.query("SELECT channel_sk,channel_code,channel_name,channel_city,channel_address FROM dim_channel", new DimChannelMapper());
    }

    @Override
    public Long insert(String[] code) {
        return this.insert(code[0]);
    }

    /**
     * 插入渠道
     *
     * @return
     */
    public Long insert(String channelCode) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_channel(channel_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, channelCode);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * 批量更新
     *
     * @param list
     */
    public void updateBatch(List<DimChannel> list) {
        List<Object[]> batch = new ArrayList<>();
        list.forEach(dim -> {
            Object[] values = new Object[]{
                    dim.getChannelCode(), dim.getChannelCity(), dim.getChannelAddress(), dim.getChannelSk()
            };
            batch.add(values);
        });
        dynamicJdbcTemplate.batchUpdate("UPDATE dim_channel SET channel_name = ? ,channel_city = ? ,channel_address=  ? WHERE channel_sk =  ?", batch);
    }
}
