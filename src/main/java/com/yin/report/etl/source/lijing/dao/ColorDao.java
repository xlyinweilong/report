package com.yin.report.etl.source.lijing.dao;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 颜色DAO
 *
 * @author yin.weilong
 * @date 2018.11.02
 */
@Component
public class ColorDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 从丽晶查询全部的颜色
     *
     * @return
     */
    public List<DimColor> findAll() {
        String sql = "select ColorID as color_name,Color as color_name from DictGoodsColor";
        return dynamicJdbcTemplate.query(sql, (resultSet, i) -> {
            DimColor dim = new DimColor();
            dim.setColorCode(ObjectUtils.getString(resultSet.getString("color_code")));
            dim.setColorName(ObjectUtils.getString(resultSet.getString("color_name")));
            return dim;
        });
    }

}
