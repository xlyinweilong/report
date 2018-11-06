package com.yin.report.etl.source.lijing.dao;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimVip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * VIPDAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class VipDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 从丽晶查询全部的VIP
     *
     * @return
     */
    public List<DimVip> findAll() {
        String sql = "select \n" +
                "v.vip as vip_code,\n" +
                "v.name as vip_name,\n" +
                "v.BegainDate as vip_start_date,\n" +
                "v.Sex as vip_sex,\n" +
                "v.Tel as vip_tel,\n" +
                "v.Discount as vip_discount,\n" +
                "v.VipGrade as vip_grade,\n" +
                "g.VipGradeNAME as vip_grade_name\n" +
                "from CustomerVIP v\n" +
                "LEFT JOIN VIPGrade g ON g.VipGrade = v.VIPGrade\n";
        return dynamicJdbcTemplate.query(sql, (resultSet, i) -> {
            DimVip dim = new DimVip();
            dim.setVipCode(ObjectUtils.getString(resultSet.getString("vip_code")));
            dim.setVipName(ObjectUtils.getString(resultSet.getString("vip_name")));
            dim.setVipStartDate(resultSet.getDate("vip_start_date"));
            dim.setVipSex(ObjectUtils.getString(resultSet.getString("vip_sex")));
            dim.setVipTel(ObjectUtils.getString(resultSet.getString("vip_tel")));
            dim.setVipSex(ObjectUtils.getString(resultSet.getString("vip_sex")));
            dim.setVipDiscount(ObjectUtils.getBigDecimal(resultSet.getBigDecimal("vip_discount")));
            dim.setVipGrade(ObjectUtils.getString(resultSet.getString("vip_grade")));
            return dim;
        });
    }

}
