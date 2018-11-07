package com.yin.report.etl.source.lijing.dao;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 售货员
 * @author yin.weilong
 * @date 2018.11.05
 */
@Component
public class ClerkDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 从丽晶查询全部的售货员
     *
     * @return
     */
    public List<DimClerk> findAll() {
        String sql = "select BuisnessManID as clerk_code,Name as clerk_name,BirthDate as clerk_birth_date,Sex as clerk_sex,CustomerID as channel_code from BuisnessMan";
        return dynamicJdbcTemplate.query(sql, (resultSet, i) -> {
            DimClerk dim = new DimClerk();
            dim.setClerkCode(ObjectUtils.getString(resultSet.getString("clerk_code")));
            dim.setClerkName(ObjectUtils.getString(resultSet.getString("clerk_name")));
            dim.setClerkBirthDate(ObjectUtils.getString(resultSet.getString("clerk_birth_date")));
            dim.setClerkSex(ObjectUtils.getString(resultSet.getString("clerk_sex")));
            dim.setChannelCode(ObjectUtils.getString(resultSet.getString("channel_code")));
            return dim;
        });
    }
}
