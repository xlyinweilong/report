package com.yin.report.etl.source.lijing.dao;

import com.yin.report.etl.common.ObjectUtils;
import com.yin.report.etl.dw.entity.DimSize;
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
public class SizeDao {

    @Autowired
    @Qualifier("dynamicJdbcTemplate")
    private JdbcTemplate dynamicJdbcTemplate;

    /**
     * 从丽晶查询全部的渠道
     *
     * @return
     */
    public List<DimSize> findAll() {
        String sql = "select [Size] as size_name,Size_Class,FieldName as size_code  from DictSize";
        return dynamicJdbcTemplate.query(sql, (resultSet, i) -> {
            DimSize dim = new DimSize();
            dim.setSizeCode(ObjectUtils.getString(resultSet.getString("size_code")));
            dim.setSizeName(ObjectUtils.getString(resultSet.getString("size_name")));
            dim.setSizeClass(ObjectUtils.getString(resultSet.getString("size_class")));
            return dim;
        });
    }

}
