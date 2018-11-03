package com.yin.report.etl.dw.dao;

import com.yin.report.etl.dw.common.DaoInterface;
import com.yin.report.etl.dw.entity.DimColor;
import com.yin.report.etl.dw.entity.DimSize;
import com.yin.report.etl.dw.entity.DimVip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * 尺码DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class DimVipDao implements DaoInterface {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 查询全部货品
     *
     * @return
     */
    public List<DimVip> findAll() {
        return dynamicJdbcTemplate.query("SELECT vip_sk,vip_code FROM dim_vip",(resultSet, i) -> {
            DimVip dim = new DimVip();
            dim.setVipSk(resultSet.getLong("vip_sk"));
            dim.setVipCode(resultSet.getString("vip_code"));
            return dim;
        });
    }

    @Override
    public Long insert(String[] code) {
        return this.insert(code[0]);
    }

    /**
     * 插入会员
     *
     * @return
     */
    public Long insert(String vipCode) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dynamicJdbcTemplate.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dim_vip(vip_code) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vipCode);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
