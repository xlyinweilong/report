package com.yin.report.etl.dw.mapper;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimChannel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 渠道mapper
 *
 * @author yin.weilong
 * @date 2018.11.04
 */
public class DimChannelMapper implements RowMapper<DimChannel> {

    @Override
    public DimChannel mapRow(ResultSet resultSet, int i) throws SQLException {
        DimChannel dim = new DimChannel();
        dim.setChannelSk(resultSet.getLong("channel_sk"));
        dim.setChannelCode(ObjectUtils.getString(resultSet.getString("channel_code")));
        dim.setChannelName(ObjectUtils.getString(resultSet.getString("channel_name")));
        dim.setChannelCity(ObjectUtils.getString(resultSet.getString("channel_city")));
        dim.setChannelAddress(ObjectUtils.getString(resultSet.getString("channel_address")));
        return dim;
    }

}
