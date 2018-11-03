package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
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
        return dynamicJdbcTemplate.query("SELECT channel_sk,channel_code,channel_name,channel_city FROM dim_channel", (resultSet, i) -> {
            DimChannel dim = new DimChannel();
            dim.setChannelSk(resultSet.getLong("channel_sk"));
            dim.setChannelCity(resultSet.getString("channel_code"));
            dim.setChannelCode(resultSet.getString("channel_name"));
            dim.setChannelName(resultSet.getString("channel_city"));
            return dim;
        });
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
}
